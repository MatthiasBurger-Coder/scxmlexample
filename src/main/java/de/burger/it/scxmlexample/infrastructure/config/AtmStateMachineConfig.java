package de.burger.it.scxmlexample.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.model.StateMachineModel;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;

@Configuration
@EnableStateMachine
public class AtmStateMachineConfig {

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {
        return new YamlStateMachineModelFactory(new ClassPathResource("application.yml"));
    }

    @Bean
    public StateMachine<String, String> atmStateMachine(StateMachineModelFactory<String, String> yamlModelFactory) throws Exception {
        // YAML in ein StateMachineModel laden
        StateMachineModel<String, String> model = yamlModelFactory.build();

        // Builder initialisieren
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        builder.configureStates().withStates().states(model.getStatesData().getStates()).initial(model.getConfigurationData().getInitialState());

        builder.configureTransitions().withExternal()
                .and().transitions(model.getTransitionsData().getTransitions());

        StateMachine<String, String> machine = builder.build();
        machine.getStateMachineAccessor().doWithAllRegions(access ->
                access.resetStateMachine(new DefaultStateMachineContext<>(model.getConfigurationData().getInitialState(), null, null, null))
        );
        machine.start();

        return machine;
    }
}

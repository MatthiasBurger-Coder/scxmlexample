package de.burger.it.scxmlexample.infrastructure.config;

import org.apache.commons.scxml2.model.SCXML;
import org.apache.commons.scxml2.model.State;
import org.apache.commons.scxml2.model.Transition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

@Configuration
public class AtmStateMachineConfig {

    private static final String SCXML_RESOURCE = "statemachine.scxml";

    @Bean
    public StateMachine<String, String> atmStateMachine() {
        ScxmlParser parser = new ScxmlParser();
        SCXML scxml = parser.parseScxml(SCXML_RESOURCE);

        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(scxml.getInitial())
                .states(scxml.getChildren().keySet());

        var transitions = builder.configureTransitions();
        transitions.withExternal();

        for (Object child : scxml.getChildren().values()) {
            if (child instanceof State state) {
                for (Transition transition : state.getTransitionsList()) {
                    transitions
                            .withExternal()
                            .source(state.getId())
                            .target(transition.getTarget())
                            .event(transition.getEvent());
                }
            }
        }

        return builder.build();
    }
}

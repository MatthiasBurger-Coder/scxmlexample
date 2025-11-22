package de.burger.it.scxmlexample.infrastructure.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class AtmStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    private static final String MODEL_RESOURCE = "atm-machine.yml";

    private final StateMachineDefinition stateMachineDefinition = loadDefinition();

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        Machine machine = stateMachineDefinition.getMachine();

        states
                .withStates()
                .initial(machine.getInitial())
                .states(machine.stateIds());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        Machine machine = stateMachineDefinition.getMachine();

        StateMachineTransitionConfigurer<String, String>.ExternalTransitionConfigurer external = transitions.withExternal();
        for (Transition transition : machine.getTransitions()) {
            external
                    .source(transition.getSource())
                    .target(transition.getTarget())
                    .event(transition.getEvent());
        }
    }
    private StateMachineDefinition loadDefinition() {
        ClassPathResource resource = new ClassPathResource(MODEL_RESOURCE);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.readValue(resource.getInputStream(), StateMachineDefinition.class);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load state machine YAML resource", e);
        }
    }

    public static final class StateMachineDefinition {
        private Machine machine;

        public Machine getMachine() {
            return machine;
        }

        public void setMachine(Machine machine) {
            this.machine = machine;
        }
    }

    public static final class Machine {
        private String id;
        private String initial;
        private java.util.List<State> states = java.util.List.of();
        private java.util.List<Transition> transitions = java.util.List.of();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public java.util.List<State> getStates() {
            return states;
        }

        public void setStates(java.util.List<State> states) {
            this.states = states;
        }

        public java.util.List<Transition> getTransitions() {
            return transitions;
        }

        public void setTransitions(java.util.List<Transition> transitions) {
            this.transitions = transitions;
        }

        java.util.Set<String> stateIds() {
            java.util.Set<String> ids = new java.util.LinkedHashSet<>();
            ids.add(initial);
            for (State state : states) {
                ids.add(state.getId());
            }
            return java.util.Collections.unmodifiableSet(ids);
        }
    }

    public static final class State {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static final class Transition {
        private String source;
        private String target;
        private String event;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }
    }
}

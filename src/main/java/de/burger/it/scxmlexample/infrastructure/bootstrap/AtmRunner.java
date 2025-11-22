package de.burger.it.scxmlexample.infrastructure.bootstrap;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component
public class AtmRunner implements CommandLineRunner {

    private final StateMachine<String, String> stateMachine;

    public AtmRunner(StateMachine<String, String> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void run(@NotNull String... args) throws Exception {
        System.out.println("== ATM STATE MACHINE START ==");
        System.out.println("Initial state: " + stateMachine.getState().getId());

        stateMachine.start();

        stateMachine.sendEvent("atm.connected");
        stateMachine.sendEvent("atm.loadSuccess");
        stateMachine.sendEvent("atm.connLost");
        stateMachine.sendEvent("atm.connRestored");
        stateMachine.sendEvent("atm.shutdown");

        System.out.println("Final state: " + stateMachine.getState().getId());
        System.out.println("== ATM STATE MACHINE END ==");
    }
}


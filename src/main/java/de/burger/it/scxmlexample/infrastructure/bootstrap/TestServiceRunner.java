
package de.burger.it.scxmlexample.infrastructure.bootstrap;


import de.burger.it.scxmlexample.application.loggertest.LoggerTestService;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestServiceRunner implements CommandLineRunner {

    private final LoggerTestService testService;

    public TestServiceRunner(LoggerTestService testService) {
        this.testService = testService;
    }

    @Override
    public void run(String @NotNull ... args) {
        String result = testService.runTest("Matthias");
        // Optional: just to see something in console besides AOP logs
        System.out.println("TestService result = " + result);
    }
}


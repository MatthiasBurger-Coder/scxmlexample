package de.burger.it.scxmlexample.application.loggertest;

import de.burger.it.scxmlexample.infrastructure.logging.Loggable;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Service
@Loggable(Level.DEBUG)
public class LoggerTestService {

    public String runTest(String name) {
        // simple demo logic
        return "Hello " + name + ", test service executed.";
    }
}

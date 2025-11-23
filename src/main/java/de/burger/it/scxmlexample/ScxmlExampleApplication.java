package de.burger.it.scxmlexample;

import de.burger.it.scxmlexample.infrastructure.logging.Loggable;
import org.slf4j.event.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Loggable(Level.DEBUG)
public class ScxmlExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScxmlExampleApplication.class, args);

    }

}

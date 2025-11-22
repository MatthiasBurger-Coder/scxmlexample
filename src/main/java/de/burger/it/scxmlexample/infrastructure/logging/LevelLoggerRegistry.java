package de.burger.it.scxmlexample.infrastructure.logging;


import de.burger.it.scxmlexample.infrastructure.logging.strategy.*;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class LevelLoggerRegistry {

    private final Map<Level, LevelLogger> registry = new EnumMap<>(Level.class);

    public LevelLoggerRegistry() {
        registry.put(Level.TRACE, new TraceLevelLogger());
        registry.put(Level.DEBUG, new DebugLevelLogger());
        registry.put(Level.INFO,  new InfoLevelLogger());
        registry.put(Level.WARN,  new WarnLevelLogger());
        registry.put(Level.ERROR, new ErrorLevelLogger());
    }

    public LevelLogger get(Level level) {
        LevelLogger logger = registry.get(level);
        if (logger == null) {
            // Fallback to DEBUG strategy if somehow not configured
            return registry.get(Level.DEBUG);
        }
        return logger;
    }
}


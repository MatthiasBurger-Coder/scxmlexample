package de.burger.it.scxmlexample.infrastructure.logging;

import org.slf4j.Logger;

public interface LevelLogger {

    void logEntry(Logger logger, String methodName, Object[] args);

    void logExit(Logger logger, String methodName, long durationMs, Object result);

    void logException(Logger logger, String methodName, long durationMs, Throwable ex);
}


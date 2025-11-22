package de.burger.it.scxmlexample.infrastructure.logging.strategy;

import de.burger.it.scxmlexample.infrastructure.logging.LevelLogger;
import org.slf4j.Logger;

public class ErrorLevelLogger implements LevelLogger {

    @Override
    public void logEntry(Logger logger, String methodName, Object[] args) {
        // No entry logging on error level
    }

    @Override
    public void logExit(Logger logger, String methodName, long durationMs, Object result) {
        // No normal exit logging on error level
    }

    @Override
    public void logException(Logger logger, String methodName, long durationMs, Throwable ex) {
        if (logger.isErrorEnabled()) {
            logger.error("✖ Exception in {} ({} ms)", methodName, durationMs, ex);
        }
    }
}

package de.burger.it.scxmlexample.infrastructure.logging.strategy;


import de.burger.it.scxmlexample.infrastructure.logging.LevelLogger;
import org.slf4j.Logger;

public class WarnLevelLogger implements LevelLogger {

    @Override
    public void logEntry(Logger logger, String methodName, Object[] args) {
        if (logger.isWarnEnabled()) {
            logger.warn("→ Enter {}", methodName);
        }
    }

    @Override
    public void logExit(Logger logger, String methodName, long durationMs, Object result) {
        if (logger.isWarnEnabled()) {
            logger.warn("← Exit {} ({} ms)", methodName, durationMs);
        }
    }

    @Override
    public void logException(Logger logger, String methodName, long durationMs, Throwable ex) {
        if (logger.isErrorEnabled()) {
            logger.error("✖ Exception in {} ({} ms)", methodName, durationMs, ex);
        }
    }
}


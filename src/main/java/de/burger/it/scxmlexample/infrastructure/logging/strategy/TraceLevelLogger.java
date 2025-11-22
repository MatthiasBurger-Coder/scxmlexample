package de.burger.it.scxmlexample.infrastructure.logging.strategy;

import de.burger.it.scxmlexample.infrastructure.logging.LevelLogger;
import org.slf4j.Logger;

import java.util.Arrays;

public class TraceLevelLogger implements LevelLogger {

    @Override
    public void logEntry(Logger logger, String methodName, Object[] args) {
        if (logger.isTraceEnabled()) {
            logger.trace("→ Enter {}", methodName);
            logger.trace("  args={}", Arrays.toString(args));
        }
    }

    @Override
    public void logExit(Logger logger, String methodName, long durationMs, Object result) {
        if (logger.isTraceEnabled()) {
            logger.trace("← Exit {} ({} ms)", methodName, durationMs);
            logger.trace("  result={}", result);
        }
    }

    @Override
    public void logException(Logger logger, String methodName, long durationMs, Throwable ex) {
        if (logger.isTraceEnabled()) {
            logger.trace("✖ Exception in {} ({} ms)", methodName, durationMs, ex);
        }
    }
}


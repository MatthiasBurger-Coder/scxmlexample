package de.burger.it.scxmlexample.infrastructure.logging.strategy;

import de.burger.it.scxmlexample.infrastructure.logging.LevelLogger;
import org.slf4j.Logger;

import java.util.Arrays;

public class DebugLevelLogger implements LevelLogger {

    @Override
    public void logEntry(Logger logger, String methodName, Object[] args) {
        if (logger.isDebugEnabled()) {
            logger.debug("→ Enter {}", methodName);
            logger.debug("  args={}", Arrays.toString(args));
        }
    }

    @Override
    public void logExit(Logger logger, String methodName, long durationMs, Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug("← Exit {} ({} ms)", methodName, durationMs);
            logger.debug("  result={}", result);
        }
    }

    @Override
    public void logException(Logger logger, String methodName, long durationMs, Throwable ex) {
        if (logger.isDebugEnabled()) {
            logger.debug("✖ Exception in {} ({} ms)", methodName, durationMs, ex);
        }
    }
}


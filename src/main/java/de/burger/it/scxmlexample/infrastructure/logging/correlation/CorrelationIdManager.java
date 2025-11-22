package de.burger.it.scxmlexample.infrastructure.logging.correlation;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * Manages the lifecycle of a correlation id for the current thread.
 * The id is stored in the MDC under the key "correlationId".
 */
public final class CorrelationIdManager {

    public static final String MDC_KEY = "correlationId";

    /**
     * Indicates whether the current method invocation created the correlation id.
     * Only the owner is allowed to clear it.
     */
    private static final ThreadLocal<Boolean> OWNER = new ThreadLocal<>();

    private CorrelationIdManager() {
    }

    /**
     * Initialize the correlation id if none is present yet.
     * Marks the current invocation as owner if a new id was created.
     */
    public static void initCorrelationId() {
        String current = MDC.get(MDC_KEY);
        if (current == null) {
            String id = UUID.randomUUID().toString();
            MDC.put(MDC_KEY, id);
            OWNER.set(true);
        } else {
            OWNER.set(false);
        }
    }

    /**
     * Clears the correlation id from MDC only if the current invocation
     * created it. This ensures nested @Loggable calls do not accidentally
     * remove an id that was created by an outer call.
     */
    public static void clearIfOwned() {
        Boolean owned = OWNER.get();
        if (Boolean.TRUE.equals(owned)) {
            MDC.remove(MDC_KEY);
        }
        OWNER.remove();
    }

    /**
     * Returns the current correlation id or null if none is set.
     */
    public static String getCorrelationId() {
        return MDC.get(MDC_KEY);
    }
}


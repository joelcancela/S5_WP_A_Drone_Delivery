package fr.unice.polytech.si3.dda.exception;

/**
 * Signals when a drone the used payload of a drone exceed the max Payload.
 * Thrown by <code>Drone</code>.
 *
 * @author Alexandre Hiltcher
 */
public class OverLoadException extends Exception {

    private static final long serialVersionUID = -3290639640695784205L;

    public OverLoadException() {
    }

    public OverLoadException(String message) {
        super(message);
    }

    public OverLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverLoadException(Throwable cause) {
        super(cause);
    }

}

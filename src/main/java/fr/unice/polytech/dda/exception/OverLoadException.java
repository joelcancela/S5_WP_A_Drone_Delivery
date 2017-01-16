package fr.unice.polytech.dda.exception;

/**
 * Created by alexh on 16/01/2017.
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

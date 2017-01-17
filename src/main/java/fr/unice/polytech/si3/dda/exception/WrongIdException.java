package fr.unice.polytech.si3.dda.exception;

/**
 * Signals that an id does not exists.
 * Thrown by <code> Mapping</code>
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class WrongIdException extends Exception {
    public WrongIdException() {
    }

    public WrongIdException(String message) {
        super(message);
    }

    public WrongIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongIdException(Throwable cause) {
        super(cause);
    }
}

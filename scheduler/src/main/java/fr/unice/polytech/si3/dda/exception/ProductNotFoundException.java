package fr.unice.polytech.si3.dda.exception;

/**
 * Signals that an item is not present when trying to unload it.
 * Thrown by <code>Drone</code>.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 4080126928650616184L;

    public ProductNotFoundException(String s) {
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductNotFoundException() {
    }
}

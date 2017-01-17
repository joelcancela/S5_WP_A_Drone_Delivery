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

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = -5801935147380982298L;

	/**
	 * Normal constructor of WrongIdException
	 * @param message Exception message
	 */
    public WrongIdException(String message) {
        super(message);
    }

}

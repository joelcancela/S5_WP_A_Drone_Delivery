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
public class GlobalException extends Exception {

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = -6359662948436845688L;

	/**
	 * Normal constructor of GlobalException
	 * @param s Exception message
	 */
    public GlobalException(String s) {
    	super(s);
    }
    
    /**
     * Default constructor of GlobalException
     */
    public GlobalException() {
    	super();
    }
}

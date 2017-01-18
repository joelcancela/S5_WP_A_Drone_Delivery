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
public class ProductNotFoundException extends StrategyException {

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = 4080126928650616184L;

	/**
	 * Normal constructor of ProductNotFoundException
	 * @param s Exception message
	 */
    public ProductNotFoundException(String s) {
    	super(s);
    }

    /**
     * Default constructor of ProductNotFoundException
     */
    public ProductNotFoundException() {
    	super();
    }
}

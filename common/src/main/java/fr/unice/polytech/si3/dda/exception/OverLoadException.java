package fr.unice.polytech.si3.dda.exception;

/**
 * Signals when a drone the used payload of a drone exceed the max Payload.
 * Thrown by <code>Drone</code>.
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class OverLoadException extends GlobalException {

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = -3290639640695784205L;

	/**
	 * Default constructor of OverLoadException
	 *
	 * @param msg message of this exception
	 */
	public OverLoadException(String msg) {
		super(msg);
	}

}

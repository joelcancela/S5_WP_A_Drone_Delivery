package fr.unice.polytech.si3.dda.exception;

/**
 * Exception class for malformed scheduler.out as input
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MalformedScheduleException extends GlobalException {

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = -7050963456160981441L;

	/**
	 * Normal constructor of MalformedScheduleException
	 *
	 * @param message Exception message
	 */
	public MalformedScheduleException(String message) {
		super(message);
	}
}

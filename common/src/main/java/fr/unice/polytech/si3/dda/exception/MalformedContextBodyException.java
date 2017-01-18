package fr.unice.polytech.si3.dda.exception;


import fr.unice.polytech.si3.dda.common.Context;

/**
 * Class exception for malformed context body
 *
 * @author Jeremy JUNAC
 * @author Alexandre HILTCHER
 * @author Pierre RAINERO
 * @author Joël CANCELA VAZ
 */
public class MalformedContextBodyException extends GlobalException {

	/**
	 * Generated serialId
	 */
	private static final long serialVersionUID = -4095912707064000726L;

	private final Context ctx;

	/**
	 * Normal constructor of MalformedContextBodyException
	 *
	 * @param ctx Context used
	 */
	public MalformedContextBodyException(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Gets the ctx.
	 *
	 * @return the ctx
	 */
	public Context getCtx() {
		return ctx;
	}

}

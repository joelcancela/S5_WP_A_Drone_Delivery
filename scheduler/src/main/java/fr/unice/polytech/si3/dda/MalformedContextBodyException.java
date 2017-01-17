package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.scheduler.Context;

public class MalformedContextBodyException extends Exception {

	private static final long serialVersionUID = -4095912707064000726L;
	
	private Context ctx;
	
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

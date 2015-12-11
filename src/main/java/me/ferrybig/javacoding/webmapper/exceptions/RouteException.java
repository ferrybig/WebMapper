/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ferrybig.javacoding.webmapper.exceptions;

/**
 *
 * @author Fernando
 */
public class RouteException extends ServerException {

    /**
     * Creates a new instance of <code>RouteException</code> without detail message.
     */
    public RouteException() {
        super();
    }


    /**
     * Constructs an instance of <code>RouteException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RouteException(String msg) {
        super(msg);
    }

    /**
	 * Constructs an instance of <code>RouteException</code> with the specified
	 * detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause the cause
	 */
	public RouteException(String message, Throwable cause) {
		super(message);
        this.initCause(cause);
	}

	/**
	 * Constructs an instance of <code>RouteException</code> with the specified
	 * cause.
	 *
	 * @param cause the cause
	 */
	public RouteException(Throwable cause) {
		this(cause.toString());
        this.initCause(cause);
	}
}

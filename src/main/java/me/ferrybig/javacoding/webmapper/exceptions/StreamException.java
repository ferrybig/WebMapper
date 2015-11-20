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
public class StreamException extends RuntimeException {

    /**
     * Creates a new instance of <code>StreamException</code> without detail message.
     */
    public StreamException() {
        super();
    }


    /**
     * Constructs an instance of <code>StreamException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public StreamException(String msg) {
        super(msg);
    }

    /**
	 * Constructs an instance of <code>StreamException</code> with the specified
	 * detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause the cause
	 */
	public StreamException(String message, Throwable cause) {
		super(message);
        this.initCause(cause);
	}

	/**
	 * Constructs an instance of <code>StreamException</code> with the specified
	 * cause.
	 *
	 * @param cause the cause
	 */
	public StreamException(Throwable cause) {
		this(cause.toString());
        this.initCause(cause);
	}
	
	public <T extends Throwable> void rethrowIfCauseInstanceOf(Class<T> type) throws T {
		if(this.getCause() != null) {
			if(type.isAssignableFrom(this.getCause().getClass())) {
				throw type.cast(this.getCause());
			}
		}
	}
}

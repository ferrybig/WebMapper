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
public class DatabaseException extends ServerException {

	/**
	 * Creates a new instance of <code>DatabaseException</code> without detail
	 * message.
	 */
	public DatabaseException() {
	}

	/**
	 * Constructs an instance of <code>DatabaseException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message.
	 */
	public DatabaseException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructs an instance of <code>DatabaseException</code> with the specified
	 * detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause the cause
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an instance of <code>DatabaseException</code> with the specified
	 * cause.
	 *
	 * @param cause the cause
	 */
	public DatabaseException(Throwable cause) {
		super(cause);
	}
}

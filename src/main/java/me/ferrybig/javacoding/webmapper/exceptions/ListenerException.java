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
public class ListenerException extends ServerException {

	/**
	 * Creates a new instance of <code>ListenerException</code> without detail
	 * message.
	 */
	public ListenerException() {
	}

	/**
	 * Constructs an instance of <code>ListenerException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message.
	 */
	public ListenerException(String msg) {
		super(msg);
	}
}

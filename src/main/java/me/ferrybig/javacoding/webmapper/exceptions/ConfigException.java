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
public class ConfigException extends ServerException {

	/**
	 * Creates a new instance of <code>ConfigException</code> without detail
	 * message.
	 */
	public ConfigException() {
	}

	/**
	 * Constructs an instance of <code>ConfigException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message.
	 */
	public ConfigException(String msg) {
		super(msg);
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.empty;

import me.ferrybig.javacoding.webmapper.Server;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Fernando
 */
public class EmptyServer{

	private EmptyServer() {
	}
	
	public final static Server INSTANCE = (Server) Proxy.newProxyInstance(EmptyServer.class.getClassLoader(), 
			new Class<?>[]{Server.class}, (Object proxy, Method method, Object[] args) -> {
				throw new UnsupportedOperationException("Not supported yet.");
	});
	
}

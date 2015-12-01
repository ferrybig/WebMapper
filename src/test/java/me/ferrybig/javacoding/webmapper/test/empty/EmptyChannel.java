/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.empty;

import io.netty.channel.Channel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Fernando
 */
public class EmptyChannel {
	public static final Channel INSTANCE;
	
	static {
		INSTANCE = (Channel) Proxy.newProxyInstance(EmptyChannel.class.getClassLoader(), 
				new Class[]{Channel.class}, (Object proxy, Method method, Object[] args) -> {
					throw new UnsupportedOperationException("Dummy channel is dumb");
		});
	}
}

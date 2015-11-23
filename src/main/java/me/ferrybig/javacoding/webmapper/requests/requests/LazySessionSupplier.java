/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.util.LazyObjectCreator;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author Fernando
 */
public class LazySessionSupplier implements SessionSupplier {

	private final LazyObjectCreator<Session> session;

	public LazySessionSupplier(Supplier<Session> sessionFactory) {
		this.session = new LazyObjectCreator<>(Objects.requireNonNull(sessionFactory, "sessionFactory == null"));
	}
	
	@Override
	public Session getSession() {
		return session.get();
	}

	@Override
	public boolean hasTouchedSession() {
		return session.has();
	}
	
}

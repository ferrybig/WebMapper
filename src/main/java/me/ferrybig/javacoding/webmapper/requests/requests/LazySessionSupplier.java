/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.session.Session;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author Fernando
 */
public class LazySessionSupplier implements SessionSupplier {

	private Session session;
	private final Supplier<Session> sessionFactory;

	public LazySessionSupplier(Supplier<Session> sessionFactory) {
		this.sessionFactory = Objects.requireNonNull(sessionFactory, "sessionFactory == null");
	}
	
	@Override
	public Session getSession() {
		if(session == null) {
			session = sessionFactory.get();
		}
		return session;
	}

	@Override
	public boolean hasTouchedSession() {
		return session != null;
	}
	
}

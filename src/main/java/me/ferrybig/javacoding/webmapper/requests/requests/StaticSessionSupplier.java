/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.session.Session;
import java.util.Objects;

/**
 *
 * @author Fernando
 */
public class StaticSessionSupplier implements SessionSupplier {
	private final Session session;

	public StaticSessionSupplier(Session session) {
		this.session = Objects.requireNonNull(session, "session == null");
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public boolean hasTouchedSession() {
		return true;
	}
	
}

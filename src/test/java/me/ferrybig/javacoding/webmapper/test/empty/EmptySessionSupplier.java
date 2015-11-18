/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.empty;

import me.ferrybig.javacoding.webmapper.requests.requests.SessionSupplier;
import me.ferrybig.javacoding.webmapper.session.Session;

/**
 *
 * @author Fernando
 */
public class EmptySessionSupplier implements SessionSupplier {

	public EmptySessionSupplier() {
	}

	@Override
	public Session getSession() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean hasTouchedSession() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}

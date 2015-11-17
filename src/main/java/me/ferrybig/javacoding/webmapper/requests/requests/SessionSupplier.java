/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.requests;

import me.ferrybig.javacoding.webmapper.session.Session;

/**
 *
 * @author Fernando
 */
public interface SessionSupplier {
	public default Session session() {
		return getSession();
	}
	
	public Session getSession();
	
	public boolean hasTouchedSession();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.util.Optional;

/**
 *
 * @author Fernando
 */
public interface Session {
	public Optional<AuthToken> getAuthToken();
	
	public String getKey();

	public static interface AuthToken {

	}
}

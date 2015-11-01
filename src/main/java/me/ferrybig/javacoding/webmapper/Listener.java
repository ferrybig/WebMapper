/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.util.Objects;

/**
 *
 * @author Fernando
 */
public class Listener {

	private final boolean ssl;
	private final int port;
	private final String host;

	public Listener(String host, int port, boolean ssl) {
		this.ssl = ssl;
		this.port = port;
		this.host = host;
	}

	@Override
	public String toString() {
		return "Listener{" + "ssl=" + ssl + ", port=" + port + ", host=" + host + '}';
	}

	public boolean isSsl() {
		return ssl;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + (this.ssl ? 1 : 0);
		hash = 71 * hash + this.port;
		hash = 71 * hash + Objects.hashCode(this.host);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Listener other = (Listener) obj;
		if (this.ssl != other.ssl) {
			return false;
		}
		if (this.port != other.port) {
			return false;
		}
		if (!Objects.equals(this.host, other.host)) {
			return false;
		}
		return true;
	}
	
	
	
}

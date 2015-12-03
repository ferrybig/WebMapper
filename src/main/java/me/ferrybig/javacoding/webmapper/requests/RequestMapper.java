/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;

/**
 *
 * @author Fernando
 */
@FunctionalInterface
public interface RequestMapper {
	public EndpointResult<?> handleHttpRequest(WebServerRequest req);
}

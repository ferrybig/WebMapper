/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.routes;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 */
public class NotFoundRoute implements RequestMapper {

	public static final NotFoundRoute JSON = new NotFoundRoute(new JSONObject(), EndpointResult.ContentType.JSON);
	
	public static final NotFoundRoute TEXT = new NotFoundRoute("No permissions", EndpointResult.ContentType.TEXT);
	
	private final EndpointResult<?> returnData;
	
	public <T> NotFoundRoute(T data, EndpointResult.ContentType<T> type) {
		returnData = new EndpointResult<>(EndpointResult.Result.UNKNOWN_ENDPOINT, data, type);
	}
	
	@Override
	public EndpointResult handleHttpRequest(WebServerRequest req) {
		return returnData;
	}
	
}


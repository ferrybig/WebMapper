/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.routes;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.VersionInfo;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 */
public class DebugRoute implements RequestMapper {

	@Override
	public EndpointResult handleHttpRequest(WebServerRequest req) {
		JSONObject result = new JSONObject();
		result.put("version", VersionInfo.getFullVersion());
		result.put("session-key", req.getSession().getKey());
		return new EndpointResult(EndpointResult.Result.OK, result, EndpointResult.ContentType.JSON);
	}
	
}

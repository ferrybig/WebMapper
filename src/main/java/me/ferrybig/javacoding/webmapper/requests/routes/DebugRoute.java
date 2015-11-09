/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.routes;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.VersionInfo;
import me.ferrybig.javacoding.webmapper.requests.DefaultRequestWrapper;
import me.ferrybig.javacoding.webmapper.session.Session;
import io.netty.channel.ChannelHandlerContext;
import java.util.Optional;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 */
public class DebugRoute extends DefaultRequestWrapper<Object> {

	public DebugRoute() {
		super(Object.class);
	}
	
	@Override
	protected EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional<? super Object> userData) {
		JSONObject result = new JSONObject();
		result.put("version", VersionInfo.getFullVersion());
		result.put("session-key", session.getKey());
		return new EndpointResult(EndpointResult.Result.OK, result, EndpointResult.ContentType.JSON);
	}
	
}

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
		return new EndpointResult(EndpointResult.Result.OK, VersionInfo.getFullVersion(), EndpointResult.ContentType.TEXT);
	}
	
}

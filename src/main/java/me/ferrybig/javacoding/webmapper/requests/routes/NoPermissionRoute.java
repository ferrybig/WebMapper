/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests.routes;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.EndpointResult.ContentType;
import me.ferrybig.javacoding.webmapper.EndpointResult.Result;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.session.Session;
import io.netty.channel.ChannelHandlerContext;
import java.util.Optional;
import org.json.JSONObject;

/**
 *
 * @author Fernando
 */
public class NoPermissionRoute implements RequestMapper {

	public static final NoPermissionRoute JSON = new NoPermissionRoute(new JSONObject(), ContentType.JSON);
	
	public static final NoPermissionRoute TEXT = new NoPermissionRoute("No permissions", ContentType.TEXT);
	
	private final EndpointResult<?> returnData;
	
	public <T> NoPermissionRoute(T data, ContentType<T> type) {
		returnData = new EndpointResult<>(Result.NO_PERMISSIONS, data, type);
	}
	
	@Override
	public EndpointResult handleHttpRequest(ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) {
		return returnData;
	}
	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import java.util.Optional;

/**
 *
 * @author Fernando
 */
public interface RequestMapper {
	public EndpointResult handleHttpRequest(ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData);
}

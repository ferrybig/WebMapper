/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import static me.ferrybig.javacoding.webmapper.util.URLEncoder.encode;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;

/**
 *
 * @author Fernando
 */
public class LoggingRoute implements RequestMapper {

	private final RequestMapper upstream;
	private final Level level;
	private static final Logger LOGGER = Logger.getLogger(LoggingRoute.class);

	public LoggingRoute(RequestMapper upstream, Level level) {
		this.upstream = upstream;
		this.level = level;
	}

	@Override
	public EndpointResult<?> handleHttpRequest(WebServerRequest req) {
		EndpointResult<?> res = upstream.handleHttpRequest(req);
		SocketAddress remote = req.getChannel().remoteAddress();
		String remoteNiceStr;
		if (remote instanceof InetSocketAddress) {
			InetSocketAddress inetSocketAddress = (InetSocketAddress) remote;
			remoteNiceStr = "[" + inetSocketAddress.getAddress().getHostAddress() + "]:" + inetSocketAddress.getPort();
		} else {
			remoteNiceStr = remote.toString();
		}
		String type = (req.hasData() ? "DATA" : "GET");
		LOGGER.log(level, "{0} {1} {2} {3}", new Object[]{remoteNiceStr, type, encode(req.endpoint()), res.getResult()});
		return res;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.session.Session;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * This class handles routing between RequestMappers and named endpoints.
 * @author Fernando
 */
public class SimpleRequestRouter implements RequestMapper {

	private final Map<String, RequestMapper> routes;
	private final RequestMapper defaultRoute;

	public SimpleRequestRouter(RequestMapper defaultRoute) {
		this.defaultRoute = Objects.requireNonNull(defaultRoute, "defaultRoute == null");
		this.routes = new TreeMap<>();
	}

	public void addRoute(String endpoint, RequestMapper route) {
		this.addRoute(endpoint, route, true);
	}

	public void addRoute(String endpoint, RequestMapper route, boolean useSubString) {
		Objects.requireNonNull(route, "route == null");
		if (useSubString) {
			final RequestMapper route2 = Objects.requireNonNull(route, "route == null");
			final int endpointLength = endpoint.length();
			route = (ChannelHandlerContext ctx, String endpoint1, Session session, Optional<?> userData) -> route2.handleHttpRequest(ctx, endpoint1.substring(endpointLength), session, userData);
		}
		this.routes.put(endpoint, route);
	}

	public Optional<RequestMapper> getRouteExact(String route) {
		return Optional.ofNullable(this.routes.get(Objects.requireNonNull(route, "route == null")));
	}

	public Optional<RequestMapper> getRoute(String route) {
		Objects.requireNonNull(route, "route == null");
		RequestMapper r = null;
		int lastLength = 0;
		for (Map.Entry<String, RequestMapper> localRoute : this.routes.entrySet()) {
			int localRouteLength = localRoute.getKey().length();
			if (route.startsWith(localRoute.getKey()) && lastLength < localRouteLength) {
				lastLength = localRouteLength;
				r = localRoute.getValue();
				if (localRouteLength == route.length()) {
					return Optional.of(localRoute.getValue());
				}
			}
		}
		return Optional.ofNullable(r);
	}

	public boolean removeRoute(String route) {
		return this.routes.remove(Objects.requireNonNull(route, "route == null")) != null;
	}

	private RequestMapper resolvRoute(String route) {
		return this.getRoute(route).orElse(defaultRoute);
	}

	@Override
	public EndpointResult handleHttpRequest(ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) {
		return resolvRoute(endpoint).handleHttpRequest(ctx, endpoint, session, userData);
	}
}

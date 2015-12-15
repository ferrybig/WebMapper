/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.exceptions.RouteException;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
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
		this.addRoute(endpoint, route, useSubString, false);
	}

	@SuppressWarnings("UnusedAssignment")
	public void addRoute(String endpoint, RequestMapper route, boolean useSubString, boolean exactMatch) {
		Objects.requireNonNull(route, "route == null");
		if (useSubString) {
			final RequestMapper route2 = route;
			final int endpointLength = endpoint.length();
			route = (req) -> route2.handleHttpRequest(req.endpoint(req.endpoint().substring(endpointLength)));
		}
		if (exactMatch) {
			final RequestMapper route2 = route;
			route = (request) -> {
				if (endpoint.equals(request.getEndpoint())) {
					return route2.handleHttpRequest(request);
				} else {
					return defaultRoute.handleHttpRequest(request);
				}
			};
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
	public EndpointResult handleHttpRequest(WebServerRequest req) throws RouteException {
		return resolvRoute(req.endpoint()).handleHttpRequest(req);
	}
}

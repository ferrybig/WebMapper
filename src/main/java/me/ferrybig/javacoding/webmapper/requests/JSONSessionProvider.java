/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import static me.ferrybig.javacoding.webmapper.EndpointResult.ContentType.JSON;
import me.ferrybig.javacoding.webmapper.requests.RequestMapper;
import me.ferrybig.javacoding.webmapper.requests.requests.LazySessionSupplier;
import me.ferrybig.javacoding.webmapper.requests.requests.StaticSessionSupplier;
import me.ferrybig.javacoding.webmapper.requests.requests.WebServerRequest;
import me.ferrybig.javacoding.webmapper.session.Session;
import me.ferrybig.javacoding.webmapper.session.SessionManager;
import java.util.Optional;
import org.json.JSONObject;

/**
 * Provides support for json session hashes in the request body
 *
 * @author Fernando
 */
public class JSONSessionProvider implements RequestMapper {

	private final RequestMapper upstream;
	private final SessionManager sessions;

	public JSONSessionProvider(RequestMapper upstream, SessionManager sessions) {
		this.upstream = upstream;
		this.sessions = sessions;
	}

	@Override
	public EndpointResult<?> handleHttpRequest(WebServerRequest req) {
		Optional<JSONObject> data = req.getDataAs(JSONObject.class);
		LazySessionSupplier s = new LazySessionSupplier(()->
				sessions.findOrCreateSession(Optional.ofNullable(
						data.orElseGet(JSONObject::new).optString("session", null))));
		req.setSessionSupplier(s);
		EndpointResult<?> upper = upstream.handleHttpRequest(req);
		if (upper.getContentType() == JSON) {
			JSONObject obj = (JSONObject) upper.getData();
			obj.put("session", s.getSession().getKey());
			return new EndpointResult<>(upper.getResult(), obj, JSON);
		}
		return upper;
	}

}

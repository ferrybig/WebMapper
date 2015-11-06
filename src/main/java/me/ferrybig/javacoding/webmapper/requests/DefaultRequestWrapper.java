/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.requests;

import me.ferrybig.javacoding.webmapper.EndpointResult;
import me.ferrybig.javacoding.webmapper.Session;
import static me.ferrybig.javacoding.webmapper.requests.DefaultRequestWrapper.DataType.EMPTY;
import static me.ferrybig.javacoding.webmapper.requests.DefaultRequestWrapper.DataType.OPTIONAL;
import static me.ferrybig.javacoding.webmapper.requests.DefaultRequestWrapper.DataType.REQUIRED;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Fernando
 * @param <T>
 */
public abstract class DefaultRequestWrapper<T> implements RequestMapper{

	private DataType allowUserData = OPTIONAL;
	protected final Class<T> dataType;
	
	public DefaultRequestWrapper(Class<T> dataType) {
		this.dataType = Objects.requireNonNull(dataType, "dataType == null");
	}

	public DataType getAllowUserData() {
		return allowUserData;
	}

	protected void setAllowUserData(DataType allowUserData) {
		this.allowUserData = allowUserData;
	}

	public Class<T> getDataType() {
		return dataType;
	}
	
	@Override
	public EndpointResult handleHttpRequest(ChannelHandlerContext ctx, String endpoint, Session session, Optional<?> userData) {
		if(allowUserData == EMPTY && userData.isPresent()) {
			return new EndpointResult(EndpointResult.Result.SYNTAX_ERROR, "Bad Syntax, bogus userdata", EndpointResult.ContentType.TEXT);
		}
		if(allowUserData == REQUIRED && !userData.isPresent()) {
			return new EndpointResult(EndpointResult.Result.SYNTAX_ERROR, "Bad Syntax, missing userdata", EndpointResult.ContentType.TEXT);
		}
		if(userData.isPresent() && !dataType.isAssignableFrom(userData.get().getClass())) {
			return new EndpointResult(EndpointResult.Result.SYNTAX_ERROR, "Bad Syntax, userdata wrong format", EndpointResult.ContentType.TEXT);
		}
		return handle(ctx, endpoint, session, userData.map(dataType::cast));
	}
	
	protected abstract EndpointResult handle(ChannelHandlerContext ctx, String endpoint, Session session, Optional<? super T> userData);

	public static enum DataType {
		REQUIRED, OPTIONAL, EMPTY,
	}
	
	
}

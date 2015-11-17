/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.empty;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;

/**
 *
 * @author Fernando
 */
public class EmptyChannelHandlerContext implements ChannelHandlerContext {

	@Override
	public Channel channel() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public EventExecutor executor() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String name() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandler handler() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isRemoved() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelRegistered() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelUnregistered() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelActive() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelInactive() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireUserEventTriggered(Object event) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelRead(Object msg) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelReadComplete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext fireChannelWritabilityChanged() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture disconnect() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture close() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture deregister() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture close(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture deregister(ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext read() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture write(Object msg) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture write(Object msg, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelHandlerContext flush() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture writeAndFlush(Object msg) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelPipeline pipeline() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ByteBufAllocator alloc() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelPromise newPromise() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable cause) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ChannelPromise voidPromise() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> key) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public <T> boolean hasAttr(AttributeKey<T> key) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

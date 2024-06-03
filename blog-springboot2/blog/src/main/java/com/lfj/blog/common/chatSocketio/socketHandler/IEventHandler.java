package com.lfj.blog.common.chatSocketio.socketHandler;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:47
 * 定义事件处理接口和默认实现
 * 连接的处理使用前端传入Authorization,可以传入token,做校验
 */

import com.corundumstudio.socketio.SocketIOClient;
import com.lfj.blog.common.chatSocketio.cache.ClientCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public interface IEventHandler {

	void onConnect(SocketIOClient client);

	void onDisConnect(SocketIOClient client);

	default void connect(@NotNull SocketIOClient client) {


		//因为定义的用户的参数为Authorization，你也可以定义其他名称 客户端请求 http://localhost:9974?Authorization=12345

		//下面两种是加了命名空间的，请求对应命名空间的方法（就类似进了不同的房间玩游戏）
		//客户端请求 http://localhost:9974/Chat?Authorization=12345
		//客户端请求 http://localhost:9974/Test?Authorization=12345
		String token = client.getHandshakeData().getSingleUrlParam("Authorization");
		//同一个页面sessionid一样的
		UUID sessionId = client.getSessionId();

		if (ObjectUtils.isEmpty(token)) {
			System.err.println("客户端" + sessionId + "建立websocket连接失败，Authorization不能为null");
			client.disconnect();
			return;
		} else {
			System.out.println("客户端" + sessionId + "建立websocket连接成功");
			//保存用户的信息在缓存里面  //将token和clientId对应 方便推送时候使用
			ClientCache.saveClient(token, sessionId, client);
		}
	}

	/**
	 * 关闭连接
	 *
	 * @param client 客户端的SocketIO
	 */
	default void disconnect(@NotNull SocketIOClient client) {

		//定义用户的参数为Authorization
		String token = client.getHandshakeData().getSingleUrlParam("Authorization");

		//sessionId,页面唯一标识
		UUID sessionId = client.getSessionId();

		ClientCache.deleteUserCacheByUserId(token);
		//使用 ClientCache.deleteSessionClientByUserId(token, sessionId);只会删除用户某个页面会话的缓存，不会删除该用户不同会话的缓存，
		// 比如：用户同时打开了谷歌和QQ浏览器，当你关闭谷歌时候，只会删除该用户谷歌的缓存会话
		
		System.out.println("客户端" + sessionId + "断开websocket连接成功");
	}
}

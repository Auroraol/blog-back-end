package com.lfj.blog.common.chatSocketio.socketHandler.impl;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:49
 * 几种消息接收和发送模式
 */

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.lfj.blog.common.chatSocketio.cache.ClientCache;
import com.lfj.blog.common.chatSocketio.socketHandler.IEventHandler;
import com.lfj.blog.common.security.details.CustomUserDetails;
import com.lfj.blog.common.security.token.AuthenticationToken;
import com.lfj.blog.common.security.token.RedisTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnClass(SocketIOServer.class)
@Slf4j
public class ChatMessageEventHandler implements IEventHandler {
	private static int testPushCount = 0;
	private final SocketIOServer socketIOServer;
	@Autowired
	RedisTokenStore redisTokenStore;

	private String namespace = "/Chat";

	public ChatMessageEventHandler(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}


	@Override
	@OnConnect
	public void onConnect(SocketIOClient client) {
		connect(client);
		//向用户广播当前在线人
		emitOnlineList(client);
	}

	@Override
	@OnDisconnect
	public void onDisConnect(SocketIOClient client) {
		disconnect(client);
		//向用户广播当前在线人
		emitOnlineList(client);
	}
	/*
        '事件广播'
        send:
        	loginUsers - 返回当前在线的人的信息
        	roomsChange - 房间发生变化

        on:
        	getUsers - 前端获取当前在线列表
        	uploadLocalLog - 有人发送消息，要推送给所有用户，并更新本地记录，数据库也会更新
        	roomsChange - 房间发生变化
    */

	/**
	 * 前端获取当前在线列表
	 *
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = "getUsers")
	public void onGetUsersEvent(SocketIOClient client, AckRequest ackRequest) {
		if (ackRequest.isAckRequested()) {
			List<String[]> userDetailsList = getUserDetailsList();
			//返回给客户端
			ackRequest.sendAckData(userDetailsList);
		}


	}

	/**
	 * 有人发送消息，要推送给所有用户，并更新本地记录，数据库也会更新
	 *
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = "uploadLocalLog")
	public void onUploadLocalLogEvent(@NotNull SocketIOClient client, AckRequest ackRequest, String data1, Object data2) {
		//广播
		socketIOServer.getNamespace(namespace).getBroadcastOperations()
				.sendEvent("uploadLocalLogBroadcast", data1, data2);
	}

	/**
	 * 房间发生变化广播给所以用户
	 *
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = "roomsChange")
	public void onRoomsChangeEvent(@NotNull SocketIOClient client, AckRequest ackRequest, String data) {
		//广播
		socketIOServer.getNamespace(namespace).getBroadcastOperations()
				.sendEvent("changeRoomsBroadcast", "roomsChange");
	}

	//向用户广播当前在线人
	public void emitOnlineList(SocketIOClient client) {
		List<String[]> userDetailsList = getUserDetailsList();
		//广播
		socketIOServer.getNamespace(namespace).getBroadcastOperations()
				.sendEvent("loginUsers", userDetailsList);

	}

	@NotNull
	private List<String[]> getUserDetailsList() {
		// 创建一个 List 用于保存结果
		List<String[]> userDetailsList = new ArrayList<>();

		ConcurrentHashMap<String, HashMap<UUID, SocketIOClient>> allUserClients = ClientCache.getAllUserClients();

		allUserClients.forEach((key, value) -> {
			//  SpringSecurity //请求没有直接携带token, 不能用任务上下文
			AuthenticationToken authToken = redisTokenStore.readByAccessToken(key);
			//得到当前用户信息
			CustomUserDetails principal = authToken.getPrincipal();

			// 创建一个数组保存用户信息
			String[] userDetails = new String[2];
			userDetails[0] = principal.getNickname();
			userDetails[1] = principal.getAvatar();

			// 将用户信息添加到 List 中
			userDetailsList.add(userDetails);
		});
		return userDetailsList;
	}

}


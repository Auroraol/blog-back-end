package com.lfj.blog.common.chatSocketio.cache;

/**
 * @Author: LFJ
 * @Date: 2024-06-01 16:15
 * 存储用户的缓存信息
 */

import com.corundumstudio.socketio.SocketIOClient;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCache {

	//用于存储用户的socket缓存信息
	private static ConcurrentHashMap<String, HashMap<UUID, SocketIOClient>> concurrentHashMap
			= new ConcurrentHashMap<>();

	//根据用户id和session删除用户某个session信息
	public static void deleteSessionClientByUserId(String userId, UUID sessionId) {
		concurrentHashMap.get(userId).remove(sessionId);
	}

	//保存用户信息
	public static void saveClient(String userId, UUID sessionId, SocketIOClient socketIOClient) {
		HashMap<UUID, SocketIOClient> sessionIdClientCache = concurrentHashMap.get(userId);
		if (sessionIdClientCache == null) {
			sessionIdClientCache = new HashMap<>();
		}
		sessionIdClientCache.put(sessionId, socketIOClient);
		concurrentHashMap.put(userId, sessionIdClientCache);
	}

	//获取用户信息
	public static HashMap<UUID, SocketIOClient> getUserClient(String userId) {
		return concurrentHashMap.get(userId);
	}

	//删除用户缓存信息
	public static void deleteUserCacheByUserId(String userId) {
		concurrentHashMap.remove(userId);
	}

	// 获取所有用户信息
	public static ConcurrentHashMap<String, HashMap<UUID, SocketIOClient>> getAllUserClients() {
		return concurrentHashMap;
	}


}



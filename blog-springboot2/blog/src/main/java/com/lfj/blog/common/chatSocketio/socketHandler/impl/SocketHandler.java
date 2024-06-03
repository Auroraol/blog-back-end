//package com.lfj.blog.common.chatSocketio.socketHandler.impl;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.corundumstudio.socketio.annotation.OnEvent;
//import com.lfj.blog.common.chatSocketio.vo.MessageDto;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * SocketHandler
// *
// * @author wliduo[i@dolyw.com]
// * @date 2019/4/17 13:42
// */
//@Component
//@ConditionalOnClass(SocketIOServer.class)
//public class SocketHandler {
//
//	/**
//	 * socketIOServer
//	 */
//	@Autowired
//	private final SocketIOServer socketIOServer;
//	/**
//	 * logger
//	 */
//	private Logger logger = LoggerFactory.getLogger(SocketHandler.class);
//	/**
//	 * ConcurrentHashMap保存当前SocketServer用户ID对应关系
//	 */
//	private Map<String, UUID> clientMap = new ConcurrentHashMap<>(16);
//
//	public SocketHandler(SocketIOServer socketIOServer) {
//		this.socketIOServer = socketIOServer;
//	}
//
//	public Map<String, UUID> getClientMap() {
//		return clientMap;
//	}
//
//	public void setClientMap(Map<String, UUID> clientMap) {
//		this.clientMap = clientMap;
//	}
//
//	/**
//	 * 当客户端发起连接时调用
//	 *
//	 * @param socketIOClient
//	 * @return void
//	 * @author wliduo[i@dolyw.com]
//	 * @date 2019/4/17 13:55
//	 */
//	@OnConnect
//	public void onConnect(SocketIOClient socketIOClient) {
//		String userName = socketIOClient.getHandshakeData().getSingleUrlParam("Authorization");
//		if (StringUtils.isNotBlank(userName)) {
//			logger.info("用户{}开启长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
//					userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
//			// 保存
//			clientMap.put(userName, socketIOClient.getSessionId());
//			// 发送上线通知
////			this.sendMsg(null, null,
////					new MessageDto(userName, null, MsgTypeEnum.ONLINE.getValue(), null));
//
//			socketIOClient.sendEvent("message", "conn ok");
//		}
//	}
//
//	/**
//	 * 客户端断开连接时调用，刷新客户端信息
//	 *
//	 * @param socketIOClient
//	 * @return void
//	 * @author wliduo[i@dolyw.com]
//	 * @date 2019/4/17 13:56
//	 */
//	@OnDisconnect
//	public void onDisConnect(SocketIOClient socketIOClient) {
//		String userName = socketIOClient.getHandshakeData().getSingleUrlParam("Authorization");
//		if (StringUtils.isNotBlank(userName)) {
//			logger.info("用户{}断开长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
//					userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
//			// 移除
//			clientMap.remove(userName);
//			// 发送下线通知
////			this.sendMsg(null, null,
////					new MessageDto(userName, null, MsgTypeEnum.OFFLINE.getValue(), null));
//		}
//	}
//
//	/**
//	 * sendMsg发送消息事件
//	 *
//	 * @param socketIOClient
//	 * @param ackRequest
//	 * @param messageDto
//	 * @return void
//	 * @author wliduo[i@dolyw.com]
//	 * @date 2019/8/1 11:41
//	 */
//
//import com.corundumstudio.socketio.annotation.OnEvent;
//
//@OnEvent("sendMsg")
//	public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, MessageDto messageDto) {
//		if (messageDto != null) {
//			// 全部发送
//			clientMap.forEach((key, value) -> {
//				if (value != null) {
//					socketIOServer.getClient(value).sendEvent("receiveMsg", messageDto);
//				}
//			});
//		}
//	}
//
//
//}

package com.lfj.blog.common.chatSocketio.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:43
 */
//
@Configuration
public class SocketIoConfig {

	@Autowired
	SocketIoProperties socketIOProperties;

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config =
				new com.corundumstudio.socketio.Configuration();
		config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
		// 配置端口
		config.setPort(socketIOProperties.getPort());
		// 开启Socket端口复用
		com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();
		socketConfig.setReuseAddress(true);
		config.setSocketConfig(socketConfig);
		// 连接数大小
		config.setWorkerThreads(socketIOProperties.getWorkCount());
		// 允许客户请求
		config.setAllowCustomRequests(socketIOProperties.isAllowCustomRequests());
		// 协议升级超时时间(毫秒)，默认10秒，HTTP握手升级为ws协议超时时间
		config.setUpgradeTimeout(socketIOProperties.getUpgradeTimeout());
		// Ping消息超时时间(毫秒)，默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
		config.setPingTimeout(socketIOProperties.getPingTimeout());
		// Ping消息间隔(毫秒)，默认25秒。客户端向服务器发送一条心跳消息间隔
		config.setPingInterval(socketIOProperties.getPingInterval());
		//允许最大帧长度,防止他人利用大数据来攻击服务器
		config.setMaxFramePayloadLength(socketIOProperties.getMaxFramePayloadLength());
		//允许下最大内容
		config.setMaxHttpContentLength(socketIOProperties.getMaxHttpContentLength());
		final SocketIOServer server = new SocketIOServer(config);
		//添加命名空间
		Optional.ofNullable(socketIOProperties.getNamespaces()).ifPresent(nss ->
				Arrays.stream(nss).forEach(server::addNamespace));
		return server;

	}

	/**
	 * 注入OnConnect，OnDisconnect，OnEvent注解。 不写的话Spring无法扫描OnConnect，OnDisconnect等注解
	 */
	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
		return new SpringAnnotationScanner(socketIOServer);
	}
}
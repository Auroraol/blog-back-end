package com.lfj.blog.common.chatSocketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:28
 */
@Component
@ConfigurationProperties(prefix = "socketio")
@Data
public class SocketIoProperties {
	private String host;
	private int port;
	private String[] namespaces;
	private int maxFramePayloadLength;
	private int maxHttpContentLength;
	private int bossCount;
	private int workCount;
	private boolean allowCustomRequests;
	private int upgradeTimeout;
	private int pingTimeout;
	private int pingInterval;
}

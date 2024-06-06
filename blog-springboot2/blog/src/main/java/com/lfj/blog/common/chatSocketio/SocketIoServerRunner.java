package com.lfj.blog.common.chatSocketio;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.lfj.blog.common.chatSocketio.config.SocketIoProperties;
import com.lfj.blog.utils.SpringContextUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: LFJ
 * @Date: 2024-05-30 21:48
 * 定义启动类
 * 这里为每个命名空间注册一个事件处理Handler, 规则为bean的name为命名空间名称+MessageHandler。
 */
@Component
@Order(1)
@Log4j2
public class SocketIoServerRunner implements CommandLineRunner {
	@Autowired
	SocketIoProperties socketIOProperties;

	@Autowired(required = false)
	private SocketIOServer socketIOServer;

	@Override
	public void run(String... args) throws Exception {
		if (socketIOServer != null) {
			Optional.ofNullable(socketIOProperties.getNamespaces()).ifPresent(nss ->

					Arrays.stream(nss).forEach(ns -> {
						//获取命名空间
						SocketIONamespace socketIONamespace = socketIOServer.getNamespace(ns);
						//获取期待的类名
						String className = ns.substring(1) + "MessageEventHandler";
						try {
							// 包名.类名
							Object bean = SpringContextUtil.
									getBean(Class.forName("com.lfj.blog.common.chatSocketio.socketHandler.impl." +
											className));

							Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
						} catch (Exception e) {
							log.error("Error loading event handler for namespace: " + ns, e);
						}
					}));
			socketIOServer.start();
			log.info("socketIO启动成功");
		}
	}
}
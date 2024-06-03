package com.lfj.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfj.blog.controller.model.request.AddChatRequest;
import com.lfj.blog.entity.ChatRoom;

/**
 * @author 16658
 * @description 针对表【chat_room】的数据库操作Service
 * @createDate 2024-06-02 11:40:50
 */
public interface ChatRoomService extends IService<ChatRoom> {

	void addRoom(String name);

	void addChat(AddChatRequest addChatRequest);
}

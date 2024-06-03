package com.lfj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfj.blog.common.response.enums.ResponseCodeEnum;
import com.lfj.blog.controller.model.request.AddChatRequest;
import com.lfj.blog.entity.ChatRoom;
import com.lfj.blog.exception.ApiException;
import com.lfj.blog.mapper.ChatRoomMapper;
import com.lfj.blog.service.ChatRoomService;
import com.lfj.blog.service.vo.ChatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 16658
 * @description 针对表【chat_room】的数据库操作Service实现
 * @createDate 2024-06-02 11:40:50
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomMapper, ChatRoom>
		implements ChatRoomService {

	@Autowired
	ChatRoomMapper chatRoomMapper;

	@Override
	public void addRoom(String name) {
		long count = this.count();

		ChatRoom daoChatRoom = this.selectByBName(name);
		if (daoChatRoom != null || count > 8) {
			throw new ApiException(ResponseCodeEnum.INVALID_REQUEST.getCode(), "房间已存在或者已达到8个上限");
		}
		ChatRoom room = new ChatRoom();
		//room赋值
		room.setName(name);
		room.setDelRoot(0);
//		room.setChatLogs(null);

		this.save(room);

	}

	@Override
	public void addChat(AddChatRequest addChatRequest) {


		// 获取指定聊天室的聊天记录
		ChatRoom daoChatRoom = this.selectByBName(addChatRequest.getRoomName());
		// 如果聊天室不存在，则进行处理
		if (daoChatRoom == null) {
			throw new ApiException(ResponseCodeEnum.INVALID_REQUEST.getCode(), "房间不存在");
		}

		// 获取当前聊天记录列表
		List<ChatVo> currentChatLogs = daoChatRoom.getChatLogs();
		if (currentChatLogs == null) {
			currentChatLogs = new ArrayList<>();
		}

		// 创建新的聊天记录
		ChatVo newChat = new ChatVo();
		newChat.setRoomName(addChatRequest.getRoomName());
		newChat.setAccount(addChatRequest.getAccount());
		newChat.setContent(addChatRequest.getContent());
		newChat.setDate(addChatRequest.getDate());


		// 添加新的聊天记录
		currentChatLogs.add(newChat);

		// 更新聊天室的聊天记录
		daoChatRoom.setChatLogs(currentChatLogs);
		chatRoomMapper.updateById(daoChatRoom);
	}

	/**
	 * 根据名称查询
	 *
	 * @param name
	 * @return
	 */
	private ChatRoom selectByBName(String name) {
		QueryWrapper<ChatRoom> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(ChatRoom::getName, name);
		return this.getOne(queryWrapper, false);
	}
}





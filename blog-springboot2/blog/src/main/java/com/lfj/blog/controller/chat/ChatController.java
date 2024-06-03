package com.lfj.blog.controller.chat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lfj.blog.common.response.ApiResponseResult;
import com.lfj.blog.controller.model.request.AddChatRequest;
import com.lfj.blog.entity.ChatRoom;
import com.lfj.blog.mapper.ChatRoomMapper;
import com.lfj.blog.service.ChatRoomService;
import com.lfj.blog.service.vo.ChatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2024-06-01 22:04
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatRoomMapper chatRoomMapper;


	//添加新的房间
	@PostMapping("/room/add")
	public ApiResponseResult newRoom(@RequestParam(value = "name") String name) {
		chatRoomService.addRoom(name);
		return ApiResponseResult.success();
	}

	//请求所有的房间名
	@GetMapping("/room/all")
	public ApiResponseResult<List<String>> getAllRooms() {
		List<ChatRoom> chatRooms = chatRoomService.list();
		List<String> roomNames = new ArrayList<>();
		for (ChatRoom room : chatRooms) {
			roomNames.add(room.getName());
		}
		return ApiResponseResult.success(roomNames);
	}

	//删除房间
	@PostMapping("/room/delete")
	public ApiResponseResult deleteRoom(@RequestParam String name) {

		QueryWrapper<ChatRoom> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(ChatRoom::getName, name);
		return ApiResponseResult.success(chatRoomService.remove(queryWrapper));
	}

	//添加聊天记录
	@PostMapping("/add")
	public ApiResponseResult newChat(@RequestBody AddChatRequest addChatRequest) {
		chatRoomService.addChat(addChatRequest);
		return ApiResponseResult.success();
	}


	//获取对应房间的聊天记录
	@GetMapping("/all")
	public ApiResponseResult<List<ChatVo>> getChatLog(@RequestParam String name) {
		QueryWrapper<ChatRoom> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(ChatRoom::getName, name);
		List<ChatRoom> chatRooms = chatRoomService.list(queryWrapper);
		return ApiResponseResult.success(chatRooms.get(0).getChatLogs());
	}

	//获取聊天室公告
	//修改聊天室公告


}

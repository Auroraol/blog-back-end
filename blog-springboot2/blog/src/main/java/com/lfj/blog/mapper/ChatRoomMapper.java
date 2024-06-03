package com.lfj.blog.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfj.blog.entity.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 16658
 * @description 针对表【chat_room】的数据库操作Mapper
 * @createDate 2024-06-02 11:40:50
 * @Entity com.lfj.blog.entity.ChatRoom
 */

@Mapper
public interface ChatRoomMapper extends BaseMapper<ChatRoom> {
	List<ChatRoom> searchAllByName(@Param("name") String name);
}





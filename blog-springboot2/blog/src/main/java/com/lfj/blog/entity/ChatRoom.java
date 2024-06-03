package com.lfj.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lfj.blog.config.mybatis.handler.JsonStringArrayTypeHandler;
import com.lfj.blog.service.vo.ChatVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @TableName chat_room
 */
@TableName(value = "chat_room", autoResultMap = true)
@Data
public class ChatRoom implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 *
	 */
	@TableField(value = "name")
	private String name;
	/**
	 *
	 */
	// 使用JSON类型字段，确保数据库连接配置支持JSON处理
	@TableField(value = "chat_logs", typeHandler = JsonStringArrayTypeHandler.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private List<ChatVo> chatLogs;
	/**
	 *
	 */
	@TableField(value = "del_root")
	private Integer delRoot;
}
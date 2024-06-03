package com.lfj.blog.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 添加分类json
 *
 * @Author: LFJ
 * @Date: 2024-04-6 15:02
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "添加聊天json", description = "添加聊天")
public class ChatVo {

	@NotBlank(message = "房间名称不能为空")
	@ApiModelProperty(value = "名称")
	private String roomName;

	@NotBlank(message = "账号不能为空")
	@ApiModelProperty(value = "账号")
	private String account;

	@NotBlank(message = "内容不能为空")
	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "年月,格式yyyy-mm")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime date;
}

package com.lfj.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文章点赞表
 *
 * @TableName article_like
 */
@TableName(value = "article_like")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ArticleLike对象", description = "文章点赞表")
public class ArticleLike implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@JsonIgnore
	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 文章id
	 */
	@JsonIgnore
	@ApiModelProperty(value = "文章id")
	@TableField(value = "article_id")
	private Integer articleId;
	/**
	 * 用户id
	 */

	@ApiModelProperty(value = "用户id")
	@TableField(value = "user_id")
	private Integer userId;
}
package com.lfj.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文章-标签 关联表
 *
 * @TableName article_tag
 */
@TableName(value = "article_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@RequiredArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "ArticleTag对象", description = "文章-标签 关联表")
public class ArticleTag implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@EqualsAndHashCode.Exclude
	@Schema(description = "id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 文章id
	 */
	@NonNull
	@Schema(description = "文章id")
	@TableField(value = "article_id")
	private Integer articleId;
	/**
	 * 标签id
	 */
	@NonNull
	@Schema(description = "标签id")
	@TableField(value = "tag_id")
	private Integer tagId;
}
package com.lfj.blog.controller.model.dto;

import com.lfj.blog.entity.Article;
import lombok.Data;

/**
 * 上一篇和下一篇文章DTO
 *
 **/
@Data
public class PreArtAndNextArtDTO {

	/**
	 * 上一篇
	 */
	private Article pre;

	/**
	 * 下一篇
	 */
	private Article next;
}

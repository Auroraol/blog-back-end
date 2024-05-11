package com.lfj.blog.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询DTO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePageQueryDTO {

	private Long current;

	private Long size;

	private Integer categoryId;

	private Integer tagId;

	private String yearMonth;

	private String title;

	private String sort;
}

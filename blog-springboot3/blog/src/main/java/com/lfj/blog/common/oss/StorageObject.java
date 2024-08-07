package com.lfj.blog.common.oss;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 存储对象
 **/
@Data
public class StorageObject {

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 链接
	 */
	private String url;

	/**
	 * 时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;
}

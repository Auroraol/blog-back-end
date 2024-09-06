package com.lfj.blog.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.UUID;


public class FilePathUtil {

	/**
	 * 获取windows/linux的项目根目录
	 *
	 * @return
	 */
	public static String getConTextPath() {
		String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		if ("usr".equals(fileUrl.substring(1, 4))) {
			fileUrl = (fileUrl.substring(0, fileUrl.length() - 16));//linux
		} else {
			fileUrl = (fileUrl.substring(1, fileUrl.length() - 10));//windows
		}
		return fileUrl;
	}

	/**
	 * 获取文件目录
	 *
	 * @param filePath
	 * @return 目录路径，不以'/'或操作系统的文件分隔符结尾 C:\targe\png
	 */
	public static String extractDirPath(String filePath) {
		int separatePos = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\')); // 分隔目录和文件名的位置
		return separatePos == -1 ? null : filePath.substring(0, separatePos);
	}

	/**
	 * 解析文件路径，获取文件名
	 *
	 * @param filePath C:/taoxw/20190103.txt
	 * @return
	 */
	public static String extractFileName(String filePath) {
		if (filePath != null) {
			if (filePath.lastIndexOf("/") >= 0) {
				return filePath.substring(filePath.lastIndexOf("/") + 1);
			} else if (filePath.lastIndexOf("\\") >= 0) {
				return filePath.substring(filePath.lastIndexOf("\\") + 1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * String filename = "E:\\home\\commUtils\\temp\\Person03.txt";
	 * String baseName = FilenameUtils.getBaseName(filename);
	 *     return == Person03
	 * String extension = FilenameUtils.getExtension(filename);
	 *     return == txt
	 * String fullPath = FilenameUtils.getFullPath(filename);
	 *     return == E:\home\commUtils\temp\
	 * String fullPathNoEndSeparator = FilenameUtils.getFullPathNoEndSeparator(filename); //不带分隔符的文件目录（格式化：最标准的目录路径）
	 *     return == E:\home\commUtils\temp
	 * String path = FilenameUtils.getPath(filename);
	 *     return == home\commUtils\temp\
	 * String pathNoEndSeparator = FilenameUtils.getPathNoEndSeparator(filename);
	 *     return == home\commUtils\temp
	 * String prefix = FilenameUtils.getPrefix(filename);
	 *     return == E:\
	 */

	/**
	 * 解析文件，获取文件名
	 *
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		return file.getName();
	}

	/**
	 * 追加文件名前缀
	 *
	 * @param filePath 例如 xxx.png
	 * @param prefix   例如temp_xxx.png
	 * @return
	 */
	public static String prefixFilePath(String filePath, String prefix) {
		String sourceName = extractFileName(filePath);
		return filePath.replace(sourceName, prefix + sourceName);
	}

	/**
	 * 追加文件名后缀
	 *
	 * @param filePath 例如 xxx.png
	 * @param suffix   例如xxx_yyyyMMdd.png
	 * @return
	 */
	public static String suffixFilePath(String filePath, String suffix) {
		String extension = FilenameUtils.getExtension(filePath);
		return filePath.replace("." + extension, suffix + "." + extension);
	}

	/**
	 * 随机唯一文件名字
	 * 178f76f202394a12907d08d6fd4198e2.png
	 *
	 * @param suffixName .txt .jpg .JPG .png .PNG
	 * @return
	 */
	public static String createFileName(String suffixName) {
		return UUID.randomUUID().toString().replace("-", "") + suffixName;
	}

	/**
	 * 随机唯一文件名字
	 * 178f76f202394a12907d08d6fd4198e2
	 *
	 * @return
	 */
	public static String createFileName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
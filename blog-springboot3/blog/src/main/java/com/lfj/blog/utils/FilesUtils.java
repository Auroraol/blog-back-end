package com.lfj.blog.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesUtils {
	/**
	 * MultipartFile 转 File
	 *
	 * @param multipartFile
	 * @throws Exception
	 */
	public static File multiPartFileToFile3(MultipartFile multipartFile) {
		File file = null;
		if (multipartFile.isEmpty()) {
			return null;
		}

		try {
			//本质上是在项目根路径创建文件
			file = new File(multipartFile.getOriginalFilename());

			//将MultipartFile的byte[]写入到file中
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}


	/**
	 * File 转 MultipartFile
	 *
	 * @param file
	 * @throws Exception
	 */
	public static MultipartFile getMultipartFile(File file) throws Exception {
		MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
		return multipartFile;
	}

	/**
	 * 将 MultipartFile 保存到本地
	 *
	 * @param multipartFile 上传的文件
	 * @return 操作结果消息 如: C:\blog\png\
	 * @throws IOException 如果文件保存过程中发生 I/O 错误
	 */
	public static String saveMultipartFile(MultipartFile multipartFile) throws IOException {
		// 检查文件是否为空
		if (multipartFile.isEmpty()) {
			throw new IOException("上传的文件为空");
		}

		// 获取文件名
		String filename = multipartFile.getOriginalFilename();
		if (filename == null || filename.isEmpty()) {
			throw new IOException("文件名无效");
		}
		// 后缀
		String suffix = filename.substring(filename.lastIndexOf("."));

		// 生成新的文件名
		String newName = FilePathUtil.createFileName(suffix);

		// 根据文件扩展名决定目录结构
		String dir = suffix.substring(1); // 去掉点
		String basePath = FilePathUtil.getConTextPath(); // 基础路径
		Path directoryPath = Paths.get(basePath, dir);

		// 确保目录存在
		if (Files.notExists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}

		// 构建完整的文件路径
		Path filePath = directoryPath.resolve(newName);

		// 将 MultipartFile 传输到 File
		try {
			multipartFile.transferTo(filePath.toFile());
		} catch (IOException e) {
			throw new IOException("文件保存失败: " + e.getMessage(), e);
		}

		return filePath.toString();
	}

	/**
	 * 将 MultipartFile 保存到本地
	 *
	 * @param multipartFile 上传的文件
	 * @return 操作结果消息 如: C:\blog\png\2024-08-17\
	 * @throws IOException 如果文件保存过程中发生 I/O 错误
	 */
	public static String saveMultipartFileFormat(MultipartFile multipartFile) throws IOException {
		// 检查文件是否为空
		if (multipartFile.isEmpty()) {
			throw new IOException("上传的文件为空");
		}

		// 获取文件名
		String filename = multipartFile.getOriginalFilename();
		if (filename == null || filename.isEmpty()) {
			throw new IOException("文件名无效");
		}
		// 后缀
		String suffix = filename.substring(filename.lastIndexOf("."));

		// 生成新的文件名
		String newName = FilePathUtil.createFileName(suffix);

		// 获取格式化的日期字符串
		String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		// 根据文件扩展名决定目录结构
		String dir = suffix.substring(1); // 去掉点
		String basePath = FilePathUtil.getConTextPath(); // 基础路径
		System.out.println(basePath);
		Path directoryPath = Paths.get(basePath, dir, format);

		// 确保目录存在
		if (Files.notExists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}

		// 构建完整的文件路径
		Path filePath = directoryPath.resolve(newName);

		// 将 MultipartFile 传输到 File
		try {
			multipartFile.transferTo(filePath.toFile());
		} catch (IOException e) {
			throw new IOException("文件保存失败: " + e.getMessage(), e);
		}

		return filePath.toString();
	}

	/**
	 * 删除指定路径的文件
	 *
	 * @param filePath 要删除的文件的路径
	 * @return 操作结果消息
	 * @throws IOException 如果文件删除过程中发生 I/O 错误
	 */
	public static String deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);

		// 检查文件是否存在
		if (Files.notExists(path)) {
			throw new IOException("文件不存在: " + filePath);
		}

		// 尝试删除文件
		boolean isDeleted = Files.deleteIfExists(path);
		if (isDeleted) {
			return "文件删除成功: " + filePath;
		} else {
			throw new IOException("文件删除失败: " + filePath);
		}
	}

}
package com.lfj.blog.controller;


import com.lfj.blog.utils.FilePathUtil;
import com.lfj.blog.utils.FilesUtils;
import com.lfj.blog.utils.ImageConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用户表 前端控制器
 *
 * @Author: LFJ
 * @Date: 2024-01-25 14:02
 */
@RestController
@RequestMapping("/test")
@Log4j2
public class testController {

	@PostMapping("/upload")
	public String upload(@RequestPart("file") MultipartFile file) throws Exception {

		String srcPath = FilesUtils.saveMultipartFile(file);
		String suffix = srcPath.substring(srcPath.lastIndexOf("."));
		// bmp文件保存路径
		String extractDirPath = FilePathUtil.extractDirPath(srcPath);
		String noSuffixfileName = FilePathUtil.createFileName();
		String bmpDestPath = extractDirPath + File.separator + noSuffixfileName;
		// 转成bmp格式
		ImageConvertUtils.traverseFile(srcPath, bmpDestPath, suffix, "bmp");
		// 转换成镜像
//		RemoteShellCommandExecutor()

		// 文件上传
		File tempFile = new File(bmpDestPath + ".bmp");
		MultipartFile multipartFile = FilesUtils.getMultipartFile(tempFile);

		// 删除本地保存图片
		FilesUtils.deleteFile(srcPath);
		FilesUtils.deleteFile(bmpDestPath + ".bmp");
		return "";
	}

	public class RemoteShellCommandExecutor(String logoBmpPath, String logoKernelBmpPath) {
		String user = "root"; // 替换为你的用户名
		String password = "your_password"; // 替换为你的密码
		String host = "192.168.1.155"; // 远程服务器的IP地址

		// 构建最终的命令
		String command = String.format(
				"cd /usr/local/fungxi/t28/8cun && ./mypack.sh zlmage rv1109-evb-ddr3-v13-facial-gate-t28-8cun.dtb %s %s",
				logoBmpPath, logoKernelBmpPath);

		JSch jsch = new JSch();
		Session session = null;
		ChannelExec channel = null;
        try {
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);
			channel.setErrStream(System.err);
			channel.connect();

			// 读取命令输出
			InputStream in = channel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
	}
}

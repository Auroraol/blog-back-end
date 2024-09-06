package com.lfj.blog.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Author: LFJ
 * @Date: 2024-08-17 0:32
 */

@Slf4j
public class ImageConvertUtils {
	/**
	 * 遍历文件夹文件
	 *
	 * @param srcPath    原图路径
	 * @param destPath   新图路径 比如D:\\123\\无后缀名称
	 * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
	 * @return
	 */
	public static void traverseFile(String srcPath, String destPath, String formatToChange, String formatName) {
		boolean flag;
		File file = new File(srcPath);
		if (file.isFile()) {
			modifyImageFormat(srcPath, destPath + "." + formatName, formatName);
			log.info("转换单张图片,格式为" + formatName);
		} else {//文件是一个文件夹
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.getAbsolutePath().endsWith(formatToChange)) {
						return true;
					} else {
						return false;
					}
				}
			});
			for (File file1 : files) {
				String name = file1.toString().substring(file1.toString().lastIndexOf("\\")
						, file1.toString().lastIndexOf("."));
				flag = modifyImageFormat(file1.toString(), destPath + name + "." + formatName, formatName);
				if (flag) {
					log.info(file1.toString() + "转换成功!");
				} else {
					log.info(file1.toString() + "转换失败");
				}
			}
		}
	}

	/**
	 * 修改原图的文件格式
	 *
	 * @param srcPath    原图路径
	 * @param destPath   新图路径
	 * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
	 * @return true/false
	 */
	public static boolean modifyImageFormat(String srcPath, String destPath, String formatName) {
		boolean flag = false;
		try {
			BufferedImage bufferedImg = ImageIO.read(new File(srcPath));
			flag = ImageIO.write(bufferedImg, formatName, new File(destPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return flag;
	}

	/**
	 * 获取图片格式
	 *
	 * @param file 图片文件路径
	 * @return 图片格式，例如 "jpg", "png" 等
	 */
	public static String getFormat(File file) {
		try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
			if (iis == null || iis.length() == 0) {
				return null;
			}
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
			if (imageReaders.hasNext()) {
				ImageReader reader = imageReaders.next();
				return reader.getFormatName().toLowerCase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		traverseFile("D:\\PCTMoveData\\Desktop\\1.png", "2",
				"png", "bmp");//转换某一文件夹的图片

		traverseFile("D:\\1b147b5a54e639ec362a07afdff758b.jpg",
				"D:\\新图片", "jpg", "png");//转换单张图片

	}
}

package com.hotelbooking.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompresser {
	private static final int THUMB_WIDTH = 300;
	private static final int THUMB_HEIGHT = 300;
	
	
	public static void checkThumb(String originFilePath, String thumbFilePath)
	{
		File originFile = new File(originFilePath);
		File thumbFile = new File(thumbFilePath);
		if (originFile.exists() && !thumbFile.exists())
			compressAndSaveThumbImage(originFilePath, thumbFilePath);
	}
	
	public static void compressAndSaveThumbImage(String originFilePath, String thumbFilePath) {
		try {
			File originFile = new File(originFilePath);
			Image img = ImageIO.read(originFile);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				System.out.println("***Read image file error while compressing.");
				return;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null)) / (double) THUMB_WIDTH
						+ 0.1;
				double rate2 = ((double) img.getHeight(null)) / (double) THUMB_HEIGHT
						+ 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(thumbFilePath);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

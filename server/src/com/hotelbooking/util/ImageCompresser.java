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
			// �ж�ͼƬ��ʽ�Ƿ���ȷ
			if (img.getWidth(null) == -1) {
				System.out.println("***Read image file error while compressing.");
				return;
			} else {
				int newWidth;
				int newHeight;
				// �ж��Ƿ��ǵȱ�����
				// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
				double rate1 = ((double) img.getWidth(null)) / (double) THUMB_WIDTH
						+ 0.1;
				double rate2 = ((double) img.getHeight(null)) / (double) THUMB_HEIGHT
						+ 0.1;
				// �������ű��ʴ�Ľ������ſ���
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH �������㷨 ��������ͼƬ��ƽ���ȵ� ���ȼ����ٶȸ� ���ɵ�ͼƬ�����ȽϺ� ���ٶ���
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(thumbFilePath);
				// JPEGImageEncoder������������ͼƬ���͵�ת��
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

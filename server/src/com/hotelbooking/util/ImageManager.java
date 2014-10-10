package com.hotelbooking.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class ImageManager {

	private SmartUpload smartUpload;
	
	public ImageManager(ServletConfig servletConfig, HttpServletRequest request, HttpServletResponse response) {
		smartUpload = new SmartUpload();
        try {
			smartUpload.initialize(servletConfig, request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadImage(String fileName)
	{
		try {
			
			String trueFileName = fileName.replace("/", java.io.File.separator);
			System.out.println("***Download Image: " + Const.IMAGE_ROOT_DIR + trueFileName);
			smartUpload.downloadFile(Const.IMAGE_ROOT_DIR + trueFileName);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("***Image: " + fileName + " not found!");
			e.printStackTrace();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

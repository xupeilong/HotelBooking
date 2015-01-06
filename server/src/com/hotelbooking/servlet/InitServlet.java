package com.hotelbooking.servlet;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.hotelbooking.util.Const;


public class InitServlet extends HttpServlet {
 
    public void init() throws ServletException {
        Const.FILE_ROOT_DIR = getServletContext().getRealPath("/") + "WEB-INF" + File.separator 
        		+ "data" + File.separator;
        Const.IMAGE_ROOT_DIR = Const.FILE_ROOT_DIR + "pic" + File.separator + "full" + File.separator;
        System.out.println("***FILE_ROOT_PATH = " + Const.FILE_ROOT_DIR);
        System.out.println("***IMAGE_ORIGIN_PATH = " + Const.IMAGE_ROOT_DIR);
        checkDir(Const.IMAGE_ROOT_DIR);
    }
    
    private void checkDir(String dirPath)
    {
    	File dir = new File(dirPath);
        if (!dir.exists())
        {
        	dir.mkdirs();
        	System.out.println("***Make new dir: " + dirPath);
        }
    }
    

     
}

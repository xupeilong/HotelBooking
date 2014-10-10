package com.hotelbooking.network.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.hotelbooking.R;
import com.hotelbooking.utils.Const;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


public class PictureLoader implements HttpFileHandler{
	
	private static HashMap<String, SoftReference<Bitmap>> memCathe = new HashMap<String, SoftReference<Bitmap>>();
	
	private Context context;
	private HttpFileDownloader httpFileLoader;
	
	private static String cacheFilePath;
	private File cacheFile;
	
	public PictureLoader(Context context) {
		this.context = context;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))
			cacheFilePath = context.getFilesDir() + File.separator + Const.PICTURE_DIR;
		else
			cacheFilePath = Environment.getExternalStorageDirectory() + File.separator + Const.PICTURE_DIR;
		Log.d("uii", "getFilesDir: " + context.getFilesDir().getAbsolutePath());
		Log.d("uii", "getDataDirectory: " + Environment.getDataDirectory().getAbsolutePath());
		Log.d("uii", "getExternalStorageState: " + Environment.getExternalStorageState());
	}
	
	public void startGettingPictrue(String fileName, ImageView imageView)
	{
		Bitmap bitmap = getFromMemCache(fileName);
		if (bitmap != null)
		{
			showPicture(bitmap, imageView);
			return;
		}
		
		bitmap = getFromSDCardCache(fileName);
		if (bitmap != null)
		{
			showPicture(bitmap, imageView);
			return;
		}
		
		startGettingFromNetwork(fileName, imageView);		
	}
	
	private void startGettingFromNetwork(String fileName, ImageView imageView)
	{
		genCacheFile(fileName);
		List<Parameter> parameters = new ArrayList<Parameter>();
		Parameter parameter = new Parameter("file_name", fileName);
		parameters.add(parameter);
		
		httpFileLoader = new HttpFileDownloader(Const.GetImageURL, parameters, this, imageView, cacheFile, fileName);
		httpFileLoader.start();
	}
	
	private static void saveToMemCache(String fileName, Bitmap bitmap)
	{
		memCathe.put(fileName, new SoftReference<Bitmap>(bitmap));
	}
	
	private Bitmap getFromMemCache(String fileName)
	{
		SoftReference<Bitmap> ref = memCathe.get(fileName);
		Bitmap bitmap = null;
		if (ref != null)
			bitmap = ref.get();
		return bitmap;
	}
	
	private void genCacheFile(String fileName)
	{
		
		String dirPath = cacheFilePath;
		if (fileName.contains(File.separator))
		{
			String s[] = fileName.split(File.separator);
			for (int i = 0; i < s.length - 1; i++)
				dirPath = dirPath + s[i] + File.separator;
			fileName = s[s.length - 1];
		}
		String filePath = dirPath + fileName;
		
		Log.d("dataa", dirPath);
		Log.d("dataa", filePath);
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdirs();
		cacheFile = new File(filePath);
	}
	
	private Bitmap getFromSDCardCache(String fileName)
	{
		String path = Environment.getExternalStorageDirectory() + File.separator + Const.PICTURE_DIR + fileName;
		File file = new File(path);
		if ((file == null) || (!file.exists()))
			return null;
		else
		{	
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			saveToMemCache(fileName, bitmap);		
			return bitmap;
		}
	}
	
	private void showPicture(Bitmap picture, ImageView imageView)
	{
		if (picture == null)
		{
			Toast.makeText(context, "Á´½Ó´íÎó£¬Çë¼ì²éÍøÂç", Toast.LENGTH_LONG).show();
			return;
		}
		imageView.clearAnimation();
		imageView.setImageBitmap(picture);
		
	}
	
	private void showPictureWithAnim(Bitmap picture, ImageView imageView)
	{
		if (picture == null)
		{
			Toast.makeText(context, "Á´½Ó´íÎó£¬Çë¼ì²éÍøÂç", Toast.LENGTH_LONG).show();
			return;
		}
		imageView.clearAnimation();
		imageView.setImageBitmap(picture);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.image_fade_in);
		imageView.startAnimation(animation);
	}
	
	@Override
	public void handleFile(String fileName, Bitmap picture, ImageView imageView) {
		saveToMemCache(fileName, picture);
		showPictureWithAnim(picture, imageView);
	}
}

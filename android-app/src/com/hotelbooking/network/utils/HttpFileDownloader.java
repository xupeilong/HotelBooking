package com.hotelbooking.network.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;

public class HttpFileDownloader {
	static HttpClient client = null;
	
	private String url = null;
	private List<Parameter> params = null;
	private HttpFileHandler handler = null;
	
	private ImageView imageView;
	private String filePath;
	private String fileName;
	
	private int reqWidth;
	private int reqHeight;
	
	private File cacheFile;
	
	public HttpFileDownloader(String url, List<Parameter> params, HttpFileHandler handler, ImageView imageView, File cacheFile, String fileName) {
		this.url = url;
		this.params = params;
		this.handler = handler;
		this.imageView = imageView;
		this.cacheFile = cacheFile;
		this.fileName = fileName.replace("\\", "/");
		reqWidth = imageView.getLayoutParams().width;
		reqHeight = imageView.getLayoutParams().height;
	}
	
	private class NetworkTask extends AsyncTask<List<NameValuePair>, Object, Bitmap>
	{

		@Override
		protected Bitmap doInBackground(List<NameValuePair>... mPARAM) {
			// TODO Auto-generated method stub
			// for test
			File file = null;
			Bitmap bitmap = null; 
			if (url.equals("test"))
				return null;
			
			StringBuilder sb = new StringBuilder();
			String finalUrl = "";
			String testUrl = "";
			
			if ((params != null) && (params.size() > 0))
			{
				url = url + "?";
				for (NameValuePair p: params)
				{
					try {
						finalUrl = finalUrl + p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
						testUrl = testUrl + p.getName() + "=" + p.getValue();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (params.indexOf(p) != params.size() - 1)
					{
						finalUrl = finalUrl + "&";
						testUrl = testUrl + "&"; 
					}
				}
			}
			
			finalUrl = url + finalUrl;
			testUrl = url + testUrl;
			
			Log.d("urll", testUrl);
			
			
			client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(finalUrl);
			HttpResponse response;
    		try {
    			response = client.execute(httpget);  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	        HttpEntity entity = response.getEntity();
	        try
	        {
		        if(entity != null)
		        {
		        	InputStream input = new BufferedInputStream(entity.getContent());
		            OutputStream output = new FileOutputStream(cacheFile);

		            byte data[] = new byte[1024];
		            int count;
		            while ((count = input.read(data)) != -1) {
		                output.write(data, 0, count);
		            }
		            output.flush();
		            output.close();
		            input.close();
		        	
		            bitmap = BitmapFactory.decodeFile(cacheFile.getPath());
		        }
	        } catch (Exception e) {
	        	e.printStackTrace();
				return null;
			}
	        return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap file) {
			// TODO Auto-generated method stub
			super.onPostExecute(file);
			handler.handleFile(fileName, file, imageView);			
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setParams(List<Parameter> params) {
		this.params = params;
	}
	
	public void setHandler(HttpFileHandler handler) {
		this.handler = handler;
	}
	

	public void start()
	{
		NetworkTask newworkTask = new NetworkTask();
		newworkTask.execute();
	}
	
	
}

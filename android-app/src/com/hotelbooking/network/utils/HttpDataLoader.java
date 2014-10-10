package com.hotelbooking.network.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class HttpDataLoader {
	
	static HttpClient client = null;
	
	private String url = null;
	private List<Parameter> params = null;
	private HttpDataHandler handler = null;
	private int code = 0;
	
	public HttpDataLoader(String url, List<Parameter> params, HttpDataHandler handler, int code) {
		this.url = url;
		this.params = params;
		this.handler = handler;
		this.code = code;
	}
	
	private class NetworkTask extends AsyncTask<List<NameValuePair>, Object, String>
	{

		@Override
		protected String doInBackground(List<NameValuePair>... mPARAM) {
			// TODO Auto-generated method stub
			// for test
			if (url.equals("test"))
				return "";
			
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
//				Toast.makeText(context, "ÍøÂçÁ¬½Ó´íÎó£¡", Toast.LENGTH_LONG);
				return null;
			}
	        HttpEntity entity = response.getEntity();
	        try
	        {
		        if(entity != null)
		        {
		        	BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));
		        	String line=null;
		        	while((line=reader.readLine())!=null)
		        	{
		        		sb.append(line+"\n");
		        	}
		        	reader.close();
		        }
	        } catch (Exception e) {
				return null;
			}
	        return sb.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
				Log.d("dataa", String.valueOf(result));
			handler.handle(result, code);
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setParams(List<Parameter> params) {
		this.params = params;
	}
	
	public void setHandler(HttpDataHandler handler) {
		this.handler = handler;
	}
	

	public void start()
	{
		NetworkTask newworkTask = new NetworkTask();
		newworkTask.execute();
	}
	
	
}

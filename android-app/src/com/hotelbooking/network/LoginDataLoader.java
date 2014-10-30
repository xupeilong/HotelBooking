package com.hotelbooking.network;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.provider.SyncStateContract.Constants;
import android.widget.Toast;

import com.hotelbooking.HotelListActivity;
import com.hotelbooking.LoginActivity;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.User;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.network.utils.SHA1Coder;
import com.hotelbooking.utils.Const;


public class LoginDataLoader implements HttpDataHandler{

	private LoginActivity loginActivity;
	private HttpDataLoader httpDataLoader;
	
	public LoginDataLoader(LoginActivity loginActivity) {
		this.loginActivity = loginActivity;
	}
	
	public void startLogin(String account, String password)
	{
		String sha1Password = "";
		try {
			sha1Password = SHA1Coder.SHA1(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("account", account));
		parameters.add(new Parameter("password", sha1Password));
		httpDataLoader = new HttpDataLoader(Const.LoginURL, parameters, this, 0);
		httpDataLoader.start();
	}
	


	@Override
	public void handle(String data, int requestCode) {

		if (data == null)
		{
			Toast.makeText(loginActivity, "ÍøÂç´íÎó£¬Çë¼ì²éÍøÂç", Toast.LENGTH_LONG).show();
			return;
		}
		List<Hotel> hotels = new ArrayList<Hotel>();
		JSONTokener tokener = new JSONTokener(data);
		int resultCode = -1;
		User user = null;

		try {
			JSONObject obj = (JSONObject) tokener.nextValue();
			resultCode = obj.getInt("result_code");
			JSONObject userObj = obj.getJSONObject("user");
			int id = userObj.getInt("id");
			String name = userObj.getString("name");
			user = new User(id, name); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginActivity.onLoginFinished(resultCode, user);
	}
}

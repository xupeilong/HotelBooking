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

import com.hotelbooking.HotelListActivity;
import com.hotelbooking.LoginActivity;
import com.hotelbooking.RegisterActivity;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.User;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.network.utils.SHA1Coder;
import com.hotelbooking.utils.Const;


public class RegisterDataLoader implements HttpDataHandler{

	private RegisterActivity registerActivity;
	private HttpDataLoader httpDataLoader;
	
	public RegisterDataLoader(RegisterActivity registerActivity) {
		this.registerActivity = registerActivity;
	}
	
	public void startRegister(String account, String password, String name)
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
		parameters.add(new Parameter("name", name));
		httpDataLoader = new HttpDataLoader(Const.RegisterURL, parameters, this, 0);
		httpDataLoader.start();
	}
	


	@Override
	public void handle(String data, int requestCode) {

		List<Hotel> hotels = new ArrayList<Hotel>();
		JSONTokener tokener = new JSONTokener(data);
		int resultCode = -1;
		User user = null;
		try {
			JSONObject obj = (JSONObject) tokener.nextValue();
			requestCode = obj.getInt("result_code");
			int id = obj.getInt("id");
			String name = obj.getString("name");
			user = new User(id, name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registerActivity.onRegisterFinished(resultCode, user);
	}
}

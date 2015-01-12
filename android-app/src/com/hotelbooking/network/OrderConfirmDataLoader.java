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
import com.hotelbooking.OrderConfirmActivity;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.User;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.network.utils.SHA1Coder;
import com.hotelbooking.utils.Const;


public class OrderConfirmDataLoader implements HttpDataHandler{

	private OrderConfirmActivity orderConfirmActivity;
	private HttpDataLoader httpDataLoader;
	
	public OrderConfirmDataLoader(OrderConfirmActivity orderConfirmActivity) {
		this.orderConfirmActivity = orderConfirmActivity;
	}
	
	public void startCheckingCOde(String code)
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("code", code));
		httpDataLoader = new HttpDataLoader(Const.CheckCodeURL, parameters, this, 0);
		httpDataLoader.start();
	}
	


	@Override
	public void handle(String data, int requestCode) {

		if (data == null)
		{
			Toast.makeText(orderConfirmActivity, "ÍøÂç´íÎó£¬Çë¼ì²éÍøÂç", Toast.LENGTH_LONG).show();
			return;
		}
		JSONTokener tokener = new JSONTokener(data);
		boolean res = false;
		double cutFee = 0;
		try {
			JSONObject obj = (JSONObject) tokener.nextValue();
			int resultCode = obj.getInt("result");
			if (resultCode == 0)
			{
				res = true;
				cutFee = obj.getDouble("cut_fee");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderConfirmActivity.onCodeCheckOver(res, cutFee);
	}
}

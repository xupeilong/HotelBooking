package com.hotelbooking.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.provider.SyncStateContract.Constants;

import com.hotelbooking.HotelListActivity;
import com.hotelbooking.OrderActivity;
import com.hotelbooking.Adapter.OrderListAdapter;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Order;
import com.hotelbooking.network.utils.HttpDataHandler;
import com.hotelbooking.network.utils.HttpDataLoader;
import com.hotelbooking.network.utils.Parameter;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.DateFormater;


public class OrderListDataLoader implements HttpDataHandler{

	private OrderActivity orderActivity;
	private HttpDataLoader httpDataLoader;
	
	public OrderListDataLoader(OrderActivity orderActivity) {
		this.orderActivity = orderActivity;
	}
	
	public void startGettingOrderList(int userId, int page, int count)
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("user_id", userId));
		parameters.add(new Parameter("page", page));
		parameters.add(new Parameter("count", count));
		httpDataLoader = new HttpDataLoader(Const.GetOrderListURL, parameters, this, 0);
		httpDataLoader.start();
	}

	@Override
	public void handle(String data, int requestCode) {
		boolean isLast = false;
		List<Order> orders = new ArrayList<Order>();
		JSONTokener tokener = new JSONTokener(data);
		JSONArray array;
		try {
			JSONObject dataObj = (JSONObject) tokener.nextValue();
			int isLastCode = dataObj.getInt("is_last_page");
			if (isLastCode == 1)
				isLast = true;
			array = dataObj.getJSONArray("orders");
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				Order order = new Order(obj.getInt("id"),
						obj.getInt("state_code"), obj.getInt("pay_money"),
						obj.getString("hotel_name"),
						obj.getString("address"),
						DateFormater.toDate(obj.getString("checkin_date")),
						DateFormater.toDate(obj.getString("checkout_date")),
						obj.getInt("house_count"),
						obj.getString("house_name"),
						obj.getString("customer_name"));
				array.put(obj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderActivity.onOrderDataReady(isLast, orders);
	}
	

}

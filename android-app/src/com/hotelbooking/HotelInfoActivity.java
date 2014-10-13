package com.hotelbooking;

import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.HotelInfo;
import com.hotelbooking.model.Room;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HotelInfoActivity extends Activity {
	
	private TextView tvHotelName;
	private TextView tvLevel;
	private TextView tvIntro;
	private ImageView imgIcon;
	private ImageView tvAddress;
	private LinearLayout llHouses;
	
	private void initViews()
	{
		tvHotelName = (TextView) findViewById(R.id.text_hotel_name);
		tvLevel = (TextView) findViewById(R.id.text_level);
		tvIntro = (TextView) findViewById(R.id.text_intro);
		imgIcon = (ImageView) findViewById(R.id.image_hotel_icon);
		tvAddress = (ImageView) findViewById(R.id.text_address);
		llHouses = (LinearLayout) findViewById(R.id.view_gourp_houses);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_info);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_hotel_info, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		initViews();
		HotelInfoDataLoader hotelInfoDataLoader = new HotelInfoDataLoader(this);
		hotelInfoDataLoader.startGettingHotelInfo();
		
	}
	
	public void onDataReady(Hotel hotel, HotelInfo hotelInfo)
	{
		nameTextView.setText(hotel.getName());
		levelTextView.setText(hotel.getLevel());
		
		commentCountTextView.setText(hotelInfo.getCommentCountString());
		
		enviromentTextView.setText(hotelInfo.getEnvironmentString());
		equipmentTextView.setText(hotelInfo.getEquipmentString());
		serviceTextView.setText(hotelInfo.getServiceString());
		healthTextView.setText(hotelInfo.getHealthString());
		
		areaTextView.setText(hotel.getArea());
		openedTextView.setText(hotelInfo.getOpenedString());
		
		List<Room> rooms = hotelInfo.getRooms();
		roomListAdapter = new RoomListAdapter(this, rooms);
		roomListView.setAdapter(roomListAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_info, menu);
		return true;
	}

}

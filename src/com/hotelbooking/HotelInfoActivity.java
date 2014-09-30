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
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

public class HotelInfoActivity extends Activity {
	
	private TextView nameTextView;
	private TextView levelTextView;
	
	private TextView scoreTextView;
	private TextView commentCountTextView;
	
	private TextView enviromentTextView;
	private TextView equipmentTextView;
	private TextView serviceTextView;
	private TextView healthTextView;
	
	private TextView areaTextView;
	private TextView openedTextView;
	
	private ListView roomListView;
	private RoomListAdapter roomListAdapter;
	
	private void initViews()
	{
		nameTextView = (TextView) findViewById(R.id.text_hotel_name);
		levelTextView = (TextView) findViewById(R.id.text_level);
		
		scoreTextView = (TextView) findViewById(R.id.text_score);
		commentCountTextView = (TextView) findViewById(R.id.text_comment_count);
		
		enviromentTextView = (TextView) findViewById(R.id.text_environment);
		equipmentTextView = (TextView) findViewById(R.id.text_equipment);
		serviceTextView = (TextView) findViewById(R.id.text_service);
		healthTextView = (TextView) findViewById(R.id.text_health);
		
		areaTextView = (TextView) findViewById(R.id.text_area);
		openedTextView = (TextView) findViewById(R.id.text_opened);
		
		roomListView = (ListView) findViewById(R.id.list_room);
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
		
		scoreTextView.setText(hotel.getScoreString());
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

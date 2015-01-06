package com.hotelbooking;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hotelbooking.R.id;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;
import com.hotelbooking.network.HotelInfoDataLoader;
import com.hotelbooking.network.utils.PictureLoader;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.DateFormater;
import com.hotelbooking.utils.OrderHelper;
import com.hotelbooking.utils.Result;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HotelDetailActivity extends Activity {
	
	private ImageView imgBackButton;
	
	private TextView tvHotelName;
	private TextView tvLevel;
	private TextView tvIntro;
	private ImageView imgIcon;
	private TextView tvAddress;
	private TextView tvArea;
	private LinearLayout llHouses;
	private TextView tvMapButton;
	View actionBarView;
	
	private int hotelId;
	private Hotel hotel;
	
	private Calendar checkinDate;
	private int nights;
	
	private void initViews()
	{
		
		tvHotelName = (TextView) findViewById(R.id.text_hotel_name);
		tvLevel = (TextView) findViewById(R.id.text_level);
		tvIntro = (TextView) findViewById(R.id.text_intro);
		imgIcon = (ImageView) findViewById(R.id.image_hotel_icon);
		tvAddress = (TextView) findViewById(R.id.text_address);
		tvArea = (TextView) findViewById(R.id.text_area);
		llHouses = (LinearLayout) findViewById(R.id.view_gourp_houses);
		imgBackButton = (ImageView) actionBarView.findViewById(R.id.button_back);
		tvMapButton = (TextView) actionBarView.findViewById(R.id.text_button_map);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_detail);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_hotel_info, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		
		
		Intent intent = getIntent();
		hotelId = intent.getIntExtra("hotel_id", -1);
		checkinDate = (Calendar) intent.getSerializableExtra("checkin_date");
		nights = intent.getIntExtra("nights", 1);
		
		initViews();
		HotelInfoDataLoader hotelInfoDataLoader = new HotelInfoDataLoader(this);
		hotelInfoDataLoader.startGettingHotelInfo(hotelId);
		
		tvMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(HotelDetailActivity.this, MapActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("hotel", (Serializable)hotel);
				startActivity(intent);
			}
		});
		
		imgBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public void onDataReady(final Hotel hotel)
	{
		this.hotel = hotel;
		tvHotelName.setText(hotel.getName());
		tvLevel.setText(hotel.getLevel());
		tvIntro.setText(hotel.getIntro());
		PictureLoader pictureLoader = new PictureLoader(this);
		pictureLoader.startGettingPictrue(hotel.getImage_path(), imgIcon);
		tvAddress.setText(hotel.getAddress());
		tvArea.setText(hotel.getArea());
		List<House> houses = hotel.getHouses();
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (final House house: houses)
		{
			View houseView = inflater.inflate(R.layout.item_house, llHouses, false);
			TextView tvName = (TextView) houseView.findViewById(R.id.text_house_name);
			TextView tvType = (TextView) houseView.findViewById(R.id.text_house_type);
			TextView tvPrice = (TextView) houseView.findViewById(R.id.text_house_price);
			TextView tvBook = (TextView) houseView.findViewById(R.id.text_house_book);
			ImageView imgHouseIcon = (ImageView) houseView.findViewById(R.id.image_house_icon);
			
			tvName.setText(house.getName());
			String typeSring = getBedString(house.getBed()) + " " + getBreakfastString(house.getBreakfast()); 
			tvType.setText(typeSring);
			tvPrice.setText(String.valueOf(house.getPrice()));
			llHouses.addView(houseView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			String imagePath = house.getImagePath();
			if (!imagePath.equals("none"))
			{
				PictureLoader pictureLoader2 = new PictureLoader(this);
				pictureLoader2.startGettingPictrue(imagePath, imgHouseIcon);
			}
			else
				imgHouseIcon.setImageResource(R.drawable.no_pic);
			
			tvBook.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent();
					if (Const.currentUser != null)
					{
						intent.setClass(HotelDetailActivity.this, OrderConfirmActivity.class);
						intent.putExtra("hotel", hotel);
						intent.putExtra("house", house);
						intent.putExtra("checkin_date", checkinDate);
						intent.putExtra("nights", nights);
					}
					else
					{
						intent.setClass(HotelDetailActivity.this, LoginActivity.class);
					}
					startActivity(intent);
				}
			});
		}
	}
	
	
	
	private String getBedString(int bed)
	{
		switch (bed) {
		case 1:
			return "��";
		case 2:
			return "˫��";
		default:
			return "δ֪";
		}
	}
	
	private String getBreakfastString(int breakfast)
	{
		switch (breakfast) {
		case 0:
			return "����";
		case 1:
			return "����";
		case 2:
			return "˫��";
		default:
			return "δ֪";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_info, menu);
		return true;
	}

}

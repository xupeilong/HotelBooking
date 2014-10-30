package com.hotelbooking;

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
	
	private TextView tvHotelName;
	private TextView tvLevel;
	private TextView tvIntro;
	private ImageView imgIcon;
	private TextView tvAddress;
	private TextView tvArea;
	private LinearLayout llHouses;
	
	private int hotelId;
	
	private int count = 1;
	private Date checkinDate = DateFormater.toDate("2014-10-30");
	private Date checkoutDate = DateFormater.toDate("2014-11-3");;
	private String name = "test user";
	private String message = "test msg";
	
	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				Toast.makeText(HotelDetailActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();

			}
				break;
			default:
				break;
			}
		};
	};
	
	private void initViews()
	{
		tvHotelName = (TextView) findViewById(R.id.text_hotel_name);
		tvLevel = (TextView) findViewById(R.id.text_level);
		tvIntro = (TextView) findViewById(R.id.text_intro);
		imgIcon = (ImageView) findViewById(R.id.image_hotel_icon);
		tvAddress = (TextView) findViewById(R.id.text_address);
		tvArea = (TextView) findViewById(R.id.text_area);
		llHouses = (LinearLayout) findViewById(R.id.view_gourp_houses);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_detail);
		
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
		
		hotelId = getIntent().getIntExtra("hotel_id", -1);
		initViews();
		HotelInfoDataLoader hotelInfoDataLoader = new HotelInfoDataLoader(this);
		hotelInfoDataLoader.startGettingHotelInfo(hotelId);
		
	}
	
	public void onDataReady(final Hotel hotel)
	{
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
//						intent.setClass(HotelDetailActivity.this, cls)
						OrderHelper.order(HotelDetailActivity.this, mHandler, name, message, hotel, house, count, checkinDate, checkoutDate);
					}
					else
					{
						intent.setClass(HotelDetailActivity.this, LoginActivity.class);
						startActivity(intent);
					}
					
				}
			});
		}
	}
	
	
	
	private String getBedString(int bed)
	{
		switch (bed) {
		case 1:
			return "´ó´²";
		case 2:
			return "Ë«´²";
		default:
			return "Î´Öª";
		}
	}
	
	private String getBreakfastString(int breakfast)
	{
		switch (breakfast) {
		case 0:
			return "ÎÞÔç";
		case 1:
			return "µ¥Ôç";
		case 2:
			return "Ë«Ôç";
		default:
			return "Î´Öª";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_info, menu);
		return true;
	}

}

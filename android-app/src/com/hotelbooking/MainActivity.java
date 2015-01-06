package com.hotelbooking;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.hotelbooking.handler.DatePickerHandler;
import com.hotelbooking.model.City;
import com.hotelbooking.network.SearchDataLoader;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.DateFormater;
import com.hotelbooking.utils.UserChecker;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements DatePickerHandler{

	TextView button;
	
	private ActionBar actionBar;
	private View actionBarView;
	
	private TextView tvMyOrderBtn;
	private TextView tvMyLocation;
	private TextView tvCity;
	private View vCheckin;
	private TextView tvCheckin;
	private TextView tvCheckinDetail;
	private TextView tvCheckout;
	private TextView tvCheckoutDetail;
	private ImageView imgNightMinus;
	private ImageView imgNightPlus;
	private EditText etKeyword;
	private TextView tvPrice;
	private TextView tvSearch;
	
	private AlertDialog dlgSelectCity;
	private AlertDialog dlgSelectPrice;
	private AlertDialog dlgLocating;
	
	private String city;
	private Calendar checkinDate;
	private Calendar today;
	private int nights;
	private String keyword;
	private int priceLow;
	private int priceHigh;
	private String priceString;
	private int latitude;
	private int longitude;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_main, null);
		actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		initData();
		initViews();
		initListeners();
	}
	
	private void initData()
	{
		city = "北京";
		today = Calendar.getInstance();
		checkinDate = Calendar.getInstance();
		nights = 1;
	}
	
	private void initViews()
	{
		tvMyOrderBtn = (TextView) actionBarView.findViewById(R.id.text_my_order_bar);
		tvMyLocation = (TextView) findViewById(R.id.text_my_location_button);
		tvCity = (TextView) findViewById(R.id.text_city);
		vCheckin = findViewById(R.id.view_checkin_date);
		tvCheckin = (TextView) findViewById(R.id.text_checkin_date);
		tvCheckinDetail = (TextView) findViewById(R.id.text_checkin_date_detail);
		tvCheckout = (TextView) findViewById(R.id.text_checkout_date);
		tvCheckoutDetail = (TextView) findViewById(R.id.text_checkout_date_detail);
		imgNightMinus = (ImageView) findViewById(R.id.image_night_minus);
		imgNightPlus = (ImageView) findViewById(R.id.image_night_plus);
		etKeyword = (EditText) findViewById(R.id.edit_keyword);
		tvPrice = (TextView) findViewById(R.id.text_price);
		tvSearch = (TextView) findViewById(R.id.text_search);
		onSetDate(checkinDate);
		setNights(1);
	}
	
	public LocationClient locationClient = null;
	public BDLocationListener locationListener = new MyLocationListener();
			
	private class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation loc) {
			int type = loc.getLocType();
			if (type == BDLocation.TypeCacheLocation || type == BDLocation.TypeGpsLocation 
					|| type == BDLocation.TypeNetWorkLocation || type == BDLocation.TypeOffLineLocation)
			{
				latitude = (int) (loc.getLatitude() * 1e6);
				longitude = (int) (loc.getLongitude() * 1e6);
				city = "nearby";
				tvCity.setText("我附近的酒店");
				Toast.makeText(MainActivity.this, "定位成功", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(MainActivity.this, "定位失败，请开启GPS或网络", Toast.LENGTH_LONG).show();
			}
			dlgLocating.dismiss();
			
		}
		
	}
	
	private void initListeners()
	{
		tvMyOrderBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UserChecker userChecker = new UserChecker(MainActivity.this);
				userChecker.startActivityAfterLogin(MainActivity.this, OrderActivity.class);
			}
		});
		
		tvMyLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
				View loadingView = inflater.inflate(R.layout.dialog_locating, null, false);
				dlgLocating = new AlertDialog.Builder(MainActivity.this).setView(loadingView).create();
				dlgLocating.show();
				locationClient = new LocationClient(MainActivity.this);
				locationClient.registerLocationListener(locationListener);
				locationClient.start();
				int res = locationClient.requestLocation();
			}
		});
		
		tvCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
				View loadingView = inflater.inflate(R.layout.dialog_loading, null, false);
				dlgSelectCity = new AlertDialog.Builder(MainActivity.this).setView(loadingView).create();
				dlgSelectCity.show();
				SearchDataLoader searchDataLoader = new SearchDataLoader(MainActivity.this);
				searchDataLoader.startGettingCityList();
			}
		});
		
		vCheckin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DatePickerFragment newFragment = new DatePickerFragment();
				newFragment.setContext(MainActivity.this);
				newFragment.setHandler(MainActivity.this);
			    newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		
		imgNightMinus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNights(nights - 1);
			}
		});
		
		
		
		imgNightPlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNights(nights + 1);
			}
		});
		
		tvPrice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dlgSelectPrice = new AlertDialog.Builder(MainActivity.this)
				.setItems(Const.priceStringList, new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						priceLow = Const.priceLowList[arg1];
						priceHigh = Const.priceHighList[arg1];
						priceString = Const.priceStringList[arg1];
						tvPrice.setText(priceString);
					}
				}).create();
				dlgSelectPrice.show();
			}
		});
		
		tvSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				keyword = etKeyword.getText().toString();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HotelListActivity.class);
				intent.putExtra("city", city);
				intent.putExtra("checkin_date", checkinDate);
				intent.putExtra("nights", nights);
				intent.putExtra("keyword", keyword);
				intent.putExtra("low_pirce", priceLow);
				intent.putExtra("high_price", priceHigh);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				intent.putExtra("price_string", priceString);
				startActivity(intent);
			}
		});
	}
	
	private void enableNightMinusButton()
	{
		imgNightMinus.setImageResource(R.drawable.selector_night_minus_button);
		imgNightMinus.setClickable(true);
	}
	
	private void disableNightMinusButton()
	{
		imgNightMinus.setImageResource(R.drawable.order_ic_minus_disable);
		imgNightMinus.setClickable(false);
	}
	
	
	public void setNights(int nights)
	{
		this.nights = nights;
		tvCheckout.setText(getNightsDate(nights));
		tvCheckoutDetail.setText(getDateDetailString(getCheckoutDate()));
		if (nights == 1)
			disableNightMinusButton();
		else
			enableNightMinusButton();
	}
	
	private boolean isCheckinDateValid(Calendar date)
	{
		return date.compareTo(today) >= 0;
	}
	
	private Calendar getCheckoutDate()
	{
		Calendar checkoutDate = (Calendar) checkinDate.clone();
		checkoutDate.add(Calendar.DAY_OF_YEAR, nights);
		return checkoutDate;
	}
	
	private String getDateDesString(Calendar date)
	{
		Calendar nowDate = Calendar.getInstance();
        int dayDiff = DateFormater.getDiffDays(nowDate.getTime(), date.getTime());
        if (dayDiff < 0)
        {
        	Toast.makeText(this, "入住日期不能早于今天", Toast.LENGTH_LONG).show();
        	return "今天";
        }
        else if (dayDiff == 0)
        	return "今天";
        else if (dayDiff == 1)
        	return "明天";
        else if (dayDiff == 2)
        	return "后天";
        else
        	return getDateDetailString(date);
	}
	
	private String getDateDetailString(Calendar date)
	{
		return (date.get(Calendar.MONTH) + 1) + "月" + date.get(Calendar.DAY_OF_MONTH) + "日";
	}
	
	private String getNightsDate(int nights)
	{
		return nights + "夜";
	}
	
	
	public void onGotCityList(final List<City> cities)
	{
		if (cities == null)
		{
			dlgSelectCity.dismiss();
			return;
		}
		if (dlgSelectCity.isShowing())
		{
			dlgSelectCity.dismiss();
			List<String> cityStrings = new ArrayList<String>();
			for (City city: cities)
				cityStrings.add(city.getCityName() + "(" + city.getHotelCount() + ")");
			final String[] cityNameArr = new String[cities.size()];
			dlgSelectCity = new AlertDialog.Builder(this).setItems(cityStrings.toArray(cityNameArr), new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					city = cities.get(arg1).getCityName();
					tvCity.setText(cities.get(arg1).getCityName());
				}
			}).create();
			dlgSelectCity.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSetDate(Calendar date) {
		if (!isCheckinDateValid(date))
		{
			Toast.makeText(this, "入住时间不能早于今天", Toast.LENGTH_LONG).show();
			return;
		}
		checkinDate = date;
		tvCheckin.setText(getDateDesString(checkinDate));
		tvCheckinDetail.setText(getDateDetailString(checkinDate));
		setNights(nights);
	}
	

}

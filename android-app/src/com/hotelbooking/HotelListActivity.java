package com.hotelbooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hotelbooking.Adapter.HotelListAdapter;
import com.hotelbooking.Adapter.HotelListAdapter.ViewHolder;
import com.hotelbooking.model.City;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.network.HotelListDataLoader;
import com.hotelbooking.network.SearchDataLoader;
import com.hotelbooking.ui.LoadMoreListView;
import com.hotelbooking.ui.LoadMoreListView.OnLoadMoreListener;
import com.hotelbooking.ui.PullToRefreshListView;
import com.hotelbooking.ui.PullToRefreshListView.OnRefreshListener;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.ExitAppliation;
import com.hotelbooking.utils.PreferenceHelper;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HotelListActivity extends Activity {

	private static final int PAGE_SIZE = 7; 
	
	private LoadMoreListView listView;
	private View emptyView;
	private TextView tvMapButton;
	private AlertDialog dlgLoading;
	private AlertDialog dlgSelectCity;
	private AlertDialog dlgSelectPrice;
	private TextView tvFilterCity;
	private TextView tvFilterKeyword;
	private TextView tvFilterPrice;
	private EditText etKeyword;
	private View actionBarView;
	private ImageView imgBackButton;
	
	private HotelListAdapter hotelListAdapter = null;
	private List<Hotel> hotels = new ArrayList<Hotel>();
	private int pageNumber = 0;
	
	private String city;
	private Calendar checkinDate;
	private int nights;
	private String keyword;
	private int priceLow;
	private int priceHigh;
	private int latitude;
	private int longitude;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_list);
		ExitAppliation.getInstance().addActivity(this);

		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_hotel_list, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		imgBackButton = (ImageView) actionBarView.findViewById(R.id.button_back);
		
		tvMapButton = (TextView) actionBarView.findViewById(R.id.text_button_map);
		tvFilterCity = (TextView) findViewById(R.id.text_filter_city);
		tvFilterKeyword = (TextView) findViewById(R.id.text_filter_keyword);
		tvFilterPrice = (TextView) findViewById(R.id.text_filter_price);
		listView = (LoadMoreListView) findViewById(R.id.list_hotel);
		emptyView = findViewById(R.id.view_empty);
		listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		
		Intent intent = getIntent();
		city = intent.getStringExtra("city");
		checkinDate = (Calendar) intent.getSerializableExtra("checkin_date");
		nights = intent.getIntExtra("nights", 1);
		keyword = intent.getStringExtra("keyword");
		priceLow = intent.getIntExtra("low_pirce", 0);
		priceHigh = intent.getIntExtra("high_price", 0);
		latitude = intent.getIntExtra("latitude", 0);
		longitude = intent.getIntExtra("longitude", 0);
		String priceString = intent.getStringExtra("price_string");
		
		if (city.equals("nearby"))
			tvFilterCity.setText("我附近的酒店");
		else
			tvFilterCity.setText(city);
		tvFilterKeyword.setText(keyword);
		tvFilterPrice.setText(priceString);
		
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View loadingView = inflater.inflate(R.layout.dialog_loading, null, false);
		dlgLoading = new AlertDialog.Builder(this).setView(loadingView).create();
		dlgLoading.show();
		dlgLoading.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		startGettingHotelListData();
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				startGettingHotelListData();
			}
		});
		
		imgBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = hotels.get(arg2).getId();
				Intent intent = new Intent();
				intent.setClass(HotelListActivity.this, HotelDetailActivity.class);
				intent.putExtra("hotel_id", id);
				intent.putExtra("checkin_date", checkinDate);
				intent.putExtra("nights", nights);
				
				startActivity(intent);
			}
		});
		
		tvMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(HotelListActivity.this, MapActivity.class);
				intent.putExtra("type", 0);
				intent.putExtra("hotels", (Serializable)hotels);
				intent.putExtra("city", city);
				intent.putExtra("checkin_date", checkinDate);
				intent.putExtra("nights", nights);
				startActivity(intent);
			}
		});
		
		tvFilterCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				LayoutInflater inflater = LayoutInflater.from(HotelListActivity.this);
				View loadingView = inflater.inflate(R.layout.dialog_loading, null, false);
				dlgSelectCity = new AlertDialog.Builder(HotelListActivity.this).setView(loadingView).create();
				dlgSelectCity.show();
				SearchDataLoader searchDataLoader = new SearchDataLoader(HotelListActivity.this);
				searchDataLoader.startGettingCityList();
			}
		});
		
		tvFilterKeyword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(HotelListActivity.this);
				View keywordView = inflater.inflate(R.layout.dialog_keyword, null, false);
				etKeyword = (EditText) keywordView.findViewById(R.id.edit_keyword);
				new AlertDialog.Builder(HotelListActivity.this)
				 .setTitle("请输入关键字")
				 .setIcon(android.R.drawable.ic_dialog_alert)
				 .setView(keywordView)
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newKeyword = etKeyword.getText().toString();
						tvFilterKeyword.setText(newKeyword);
						keyword = newKeyword;
						restartGettingHotelListDate();
					}
				})
				 .setNegativeButton("取消", null)
				 .show();
			}
		});
		
		tvFilterPrice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dlgSelectPrice = new AlertDialog.Builder(HotelListActivity.this)
				.setItems(Const.priceStringList, new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						priceLow = Const.priceLowList[arg1];
						priceHigh = Const.priceHighList[arg1];
						tvFilterPrice.setText(Const.priceStringList[arg1]);
						restartGettingHotelListDate();
					}
				}).create();
				dlgSelectPrice.show();
			}
		});
		
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
			cityStrings.add("我附近的酒店");
			for (City city: cities)
				cityStrings.add(city.getCityName() + "(" + city.getHotelCount() + ")");
			final String[] cityNameArr = new String[cities.size()];
			dlgSelectCity = new AlertDialog.Builder(this).setItems(cityStrings.toArray(cityNameArr), new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (arg1 == 0)
						city = "nearby";
					else
						city = cities.get(arg1 - 1).getCityName();
					tvFilterCity.setText(cities.get(arg1).getCityName());
					restartGettingHotelListDate();
				}
			}).create();
			dlgSelectCity.show();
		}
	}
	
	public void restartGettingHotelListDate()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		View loadingView = inflater.inflate(R.layout.dialog_loading, null, false);
		dlgLoading = new AlertDialog.Builder(this).setView(loadingView).create();
		dlgLoading.show();
		pageNumber = 0;
		this.hotels.clear();
		listView.setEmptyView(null);
		hotelListAdapter.notifyDataSetChanged();
		startGettingHotelListData();
	}
	

	public void startGettingHotelListData() {
		HotelListDataLoader hotelListDataLoader = new HotelListDataLoader(this);
		hotelListDataLoader.startGettingHotelList(pageNumber, PAGE_SIZE, latitude, longitude, city,
				keyword, priceLow, priceHigh);
		pageNumber++;
	}

	public void onHotelListDataReady(List<Hotel> hotels) {
		dlgLoading.dismiss();
		this.hotels.addAll(hotels);
		if (this.hotels.size() == 0)
			listView.setEmptyView(emptyView);
		if (this.hotels.size() > 0 && city.equals("nearby"))
			city = this.hotels.get(0).getCity();
		if (hotelListAdapter == null)
		{
			hotelListAdapter = new HotelListAdapter(this, this.hotels);
			listView.setAdapter(hotelListAdapter);
		}
		hotelListAdapter.notifyDataSetChanged();
		listView.onLoadComplete();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			PreferenceHelper helper = new PreferenceHelper(HotelListActivity.this);
			helper.resetAccountInfo();
			Intent intent = new Intent();
			intent.setClass(HotelListActivity.this, LoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.HotelListAdapter.ViewHolder;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.ui.LoadMoreListView;
import com.hotelbooking.ui.LoadMoreListView.OnLoadMoreListener;
import com.hotelbooking.ui.PullToRefreshListView;
import com.hotelbooking.ui.PullToRefreshListView.OnRefreshListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HotelListActivity extends Activity {

	private static final int PAGE_SIZE = 7; 
	
	private LoadMoreListView listView;
	private HotelListAdapter hotelListAdapter = null;
	private List<Hotel> hotels = new ArrayList<Hotel>();
	private int pageNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_list);

		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_hotel_list, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		listView = (LoadMoreListView) findViewById(R.id.list_hotel);
		listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		startGettingHotelListData();
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				startGettingHotelListData();
			}
		});
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = hotels.get(arg2).getId();
				Intent intent = new Intent();
				intent.setClass(HotelListActivity.this, HotelInfoActivity.class);
				intent.putExtra("hotel_id", id);
				startActivity(intent);
			}
		});
		
	}

	public void startGettingHotelListData() {
		HotelListDataLoader hotelListDataLoader = new HotelListDataLoader(this);
		hotelListDataLoader.startGettingHotelList(pageNumber, PAGE_SIZE);
		pageNumber++;
	}

	public void onHotelListDataReady(List<Hotel> hotels) {
		this.hotels.addAll(hotels);
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

}

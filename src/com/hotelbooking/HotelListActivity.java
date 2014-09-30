package com.hotelbooking;

import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.ui.AutoListView;
import com.hotelbooking.ui.AutoListView.OnLoadMoreListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HotelListActivity extends Activity {

	private AutoListView listView;
	private HotelListAdapter hotelListAdapter;

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
		
		listView = (AutoListView) findViewById(R.id.list_hotel);
		listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
//				listView.onRefreshComplete();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 1)
					listView.onRefreshComplete();
			}
			
		});
		startGettingHotelListData();

	}

	public void startGettingHotelListData() {
		HotelListDataLoader hotelListDataLoader = new HotelListDataLoader(this);
		hotelListDataLoader.startGettingHotelList();
	}

	public void onHotelListDataReady(List<Hotel> hotels) {
		hotelListAdapter = new HotelListAdapter(this, hotels);
		listView.setAdapter(hotelListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_list, menu);
		return true;
	}

}

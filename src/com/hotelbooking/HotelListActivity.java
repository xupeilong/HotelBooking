package com.hotelbooking;

import java.util.List;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.ui.LoadMoreListView;
import com.hotelbooking.ui.LoadMoreListView.OnLoadMoreListener;
import com.hotelbooking.ui.PullToRefreshListView;
import com.hotelbooking.ui.PullToRefreshListView.OnRefreshListener;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HotelListActivity extends Activity {

	private LoadMoreListView listView;
	private HotelListAdapter hotelListAdapter;
	private List<Hotel> hotels;

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
				// TODO Auto-generated method stub
				
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int count = listView.getAdapter().getCount();
				Toast.makeText(HotelListActivity.this, "" + count, Toast.LENGTH_LONG).show();
				if (arg2 == count - 1)
				{
					Hotel hotel = new Hotel();
					hotel.setName("new沛龙大酒店");
					hotel.setScore((float) 5.0);
					hotel.setPrice(2999);
					hotel.setLevel("牛逼型酒店");
					hotel.setDiscoutn(true);
					hotel.setArea("海淀、中关村地区");
					hotel.setDistance((float) 213);
					if (count < 15)
					{
						hotels.add(hotel);
						hotelListAdapter.notifyDataSetChanged();
						listView.onLoadComplete();
					}
					else
					{
						listView.onLoadEnd();
					}
				}
			}
		});

	}

	public void startGettingHotelListData() {
		HotelListDataLoader hotelListDataLoader = new HotelListDataLoader(this);
		hotelListDataLoader.startGettingHotelList();
	}

	public void onHotelListDataReady(List<Hotel> hotels) {
		this.hotels = hotels;
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

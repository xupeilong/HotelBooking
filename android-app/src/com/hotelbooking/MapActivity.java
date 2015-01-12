package com.hotelbooking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.utils.ExitAppliation;
import com.hotelbooking.utils.PreferenceHelper;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {

	private static final int MAX_MARKERS = 10;
	
	private MapView mapView;
	private PoiSearch poiSearch;
	private BaiduMap baiduMap;
	
	// type: 0 = show list; 1 = show one;
	private int type;
	
	private String city;
	private List<Hotel> hotels;
	private MyManager myManager;
	
	private Hotel hotel;
	private Calendar checkinDate;
	private int nights;
	
	
	private class MyManager extends OverlayManager
	{
		private List<OverlayOptions> overlayOptionsList;
		public MyManager(BaiduMap arg0) {
			super(arg0);
			overlayOptionsList = new ArrayList<OverlayOptions>();
		}
		
		private void add(OverlayOptions overlayOptions)
		{
			this.overlayOptionsList.add(overlayOptions);
		}

		@Override
		public boolean onMarkerClick(Marker arg0) {
			return false;
		}

		@Override
		public List<OverlayOptions> getOverlayOptions() {
			// TODO Auto-generated method stub
			return overlayOptionsList;
		}
		
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_map);
		ExitAppliation.getInstance().addActivity(this);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_map, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		ImageView imgBackBtn = (ImageView) actionBarView.findViewById(R.id.button_back);
		imgBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		mapView = (MapView) findViewById(R.id.bmapView);
		baiduMap = mapView.getMap();
		myManager = new MyManager(baiduMap);
		
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		checkinDate = (Calendar) intent.getSerializableExtra("checkin_date");
		nights = intent.getIntExtra("nights", 1);
		if (type == 0)
		{
			city = intent.getStringExtra("city");
			hotels = (List<Hotel>) intent.getSerializableExtra("hotels");
			int count = 0;
			for (Hotel hotel: hotels)
			{
				Log.d("dataa", hotel.getCity());
				Log.d("dataa", city);
				if (count >= MAX_MARKERS)
					break;
				if (hotel.getCity().equals(city))
				{
					LatLng point = new LatLng((double)hotel.getLatitude() / 1e6, (double)hotel.getLongitude() / 1e6);
					BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
					Bundle bundle = new Bundle();
					bundle.putString("hotel_name", hotel.getName());
					bundle.putInt("hotel_price", hotel.getPrice());
					bundle.putInt("hotel_id", hotel.getId());
					MarkerOptions option = new MarkerOptions()  
				    .position(point)  
				    .icon(bitmap)
				    .extraInfo(bundle);  
					myManager.add(option);
					count++;
				}
			}
			baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker arg0) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View windowView = inflater.inflate(R.layout.pop_window_hotel, null, false);
					TextView tvHotelName = (TextView) windowView.findViewById(R.id.text_hotel_name);
					TextView tvHotelPrice = (TextView) windowView.findViewById(R.id.text_hotel_price);
					TextView tvDetailButton = (TextView) windowView.findViewById(R.id.text_detail_button);
					final Bundle bundle = arg0.getExtraInfo();
					tvHotelName.setText(bundle.getString("hotel_name"));
					tvHotelPrice.setText(String.valueOf(bundle.getInt("hotel_price")));
					tvDetailButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							int id = bundle.getInt("hotel_id");
							Intent intent = new Intent();
							intent.setClass(MapActivity.this, HotelDetailActivity.class);
							intent.putExtra("hotel_id", id);
							intent.putExtra("checkin_date", checkinDate);
							intent.putExtra("nights", nights);
							startActivity(intent);
						}
					});
					LatLng p = arg0.getPosition();
					InfoWindow infoWindow = new InfoWindow(windowView, p, -46);
					baiduMap.showInfoWindow(infoWindow);
					return false;
				}
			});
		}
		else if (type == 1)
		{
			hotel = (Hotel) intent.getSerializableExtra("hotel");
			LatLng point = new LatLng((double)hotel.getLatitude() / 1e6, (double)hotel.getLongitude() / 1e6);
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
			Bundle bundle = new Bundle();
			bundle.putString("hotel_name", hotel.getName());
			MarkerOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap)
		    .extraInfo(bundle);  
			myManager.add(option);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View windowView = inflater.inflate(R.layout.pop_window_hotel_name, null, false);
			TextView tvHotelName = (TextView) windowView.findViewById(R.id.text_hotel_name);
			tvHotelName.setText(hotel.getName());
			LatLng p = new LatLng((double) hotel.getLatitude() / 1e6, (double) hotel.getLongitude() / 1e6);
			InfoWindow infoWindow = new InfoWindow(windowView, p, -46);
			baiduMap.showInfoWindow(infoWindow);
		}
		
		
		myManager.addToMap();
		myManager.zoomToSpan();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			PreferenceHelper helper = new PreferenceHelper(MapActivity.this);
			helper.resetAccountInfo();
			Intent intent = new Intent();
			intent.setClass(MapActivity.this, LoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        mapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        mapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        mapView.onPause();  
        }  

}

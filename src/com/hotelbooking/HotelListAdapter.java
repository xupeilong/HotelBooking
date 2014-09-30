package com.hotelbooking;

import java.util.List;
import java.util.zip.Inflater;

import com.hotelbooking.model.Hotel;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HotelListAdapter extends BaseAdapter{

	private Context context;
	private List<Hotel> hotels;
	
	public HotelListAdapter(Context context, List<Hotel> hotels) {
		this.context = context;
		this.hotels = hotels;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotels.size();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hotel hotel = hotels.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.item_hotel_list, parent, false);
		TextView nameTextView = (TextView) itemView.findViewById(R.id.text_hotel_name);
		nameTextView.setText(hotel.getName());
		
		TextView scoreTextView = (TextView) itemView.findViewById(R.id.text_hotel_score);
		TextView priceTextView = (TextView) itemView.findViewById(R.id.text_hotel_price);
		scoreTextView.setText(String.valueOf(hotel.getScore()));
		priceTextView.setText(String.valueOf(hotel.getPrice()));
		
		TextView levelTextView = (TextView) itemView.findViewById(R.id.text_hotel_level);
		TextView discountTextView = (TextView) itemView.findViewById(R.id.text_hotel_discount);
		levelTextView.setText(hotel.getLevel());
		if (hotel.isDiscoutn())
			discountTextView.
			setVisibility(View.VISIBLE);
		else
			discountTextView.setVisibility(View.GONE);
		
		TextView areaTextView = (TextView) itemView.findViewById(R.id.text_hotel_area);
		TextView distanceTextView = (TextView) itemView.findViewById(R.id.text_hotel_distance);
		areaTextView.setText(hotel.getArea());
		distanceTextView.setText(String.valueOf(hotel.getDistance()));
		
		return itemView;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}



	

}

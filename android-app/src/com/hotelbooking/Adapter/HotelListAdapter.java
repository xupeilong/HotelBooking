package com.hotelbooking.Adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.hotelbooking.R;
import com.hotelbooking.R.drawable;
import com.hotelbooking.R.id;
import com.hotelbooking.R.layout;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.network.utils.PictureLoader;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotelListAdapter extends BaseAdapter{

	private Context context;
	private List<Hotel> hotels;
	
	public HotelListAdapter(Context context, List<Hotel> hotels) {
		this.context = context;
		this.hotels = hotels;
	}
	
	public void setHotels(List<Hotel> hotels)
	{
		this.hotels = hotels;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotels.size();
	}


	public class ViewHolder
	{
		public TextView nameTextView;
		public TextView priceTextView;
		public TextView levelTextView;
		public TextView areaTextView;
		public TextView distanceTextView;
		public ImageView imageView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Hotel hotel = hotels.get(position);
		ViewHolder holder = null;
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_hotel_list, parent, false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView.findViewById(R.id.text_hotel_name);
			holder.priceTextView = (TextView) convertView.findViewById(R.id.text_hotel_price);
			holder.levelTextView = (TextView) convertView.findViewById(R.id.text_hotel_level);
			holder.areaTextView = (TextView) convertView.findViewById(R.id.text_hotel_area);
			holder.distanceTextView = (TextView) convertView.findViewById(R.id.text_hotel_distance);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_room_icon);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.nameTextView .setText(hotel.getName());
		holder.priceTextView.setText(String.valueOf(hotel.getPrice()));
		holder.levelTextView .setText(hotel.getLevel());
		holder.areaTextView.setText(hotel.getArea());
		holder.distanceTextView.setText(String.valueOf(hotel.getDistance()));
		holder.imageView.setImageResource(R.drawable.bg_transparent);
		String imagePath = hotel.getImage_path();
		if (!imagePath.equals("none"))
		{
			PictureLoader pictureLoader = new PictureLoader(context);
			pictureLoader.startGettingPictrue(imagePath, holder.imageView);
		}
		return convertView;
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

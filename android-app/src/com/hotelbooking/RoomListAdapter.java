package com.hotelbooking;

import java.util.List;
import java.util.zip.Inflater;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Room;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoomListAdapter extends BaseAdapter{

	private Context context;
	private List<Room> houses;
	
	public RoomListAdapter(Context context, List<Room> houses) {
		this.context = context;
		this.houses = houses;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return houses.size();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Room house = houses.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.house_list_item, parent, false);
		TextView nameTextView = (TextView) itemView.findViewById(R.id.text_room_name);
		nameTextView.setText(house.getName());
		TextView typeTextView = (TextView) itemView.findViewById(R.id.text_room_type);
		typeTextView.setText(house.getFeaturesString());
		TextView priceTextView = (TextView) itemView.findViewById(R.id.text_room_price);
		priceTextView.setText(String.valueOf(house.getPrice()));
		
		
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

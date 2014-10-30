package com.hotelbooking.Adapter;

import java.util.List;

import com.hotelbooking.OrderActivity;
import com.hotelbooking.R;
import com.hotelbooking.model.Order;
import com.hotelbooking.utils.DateFormater;
import com.hotelbooking.utils.OrderHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter{

	private OrderActivity orderActivity;
	private List<Order> orders;
	
	public OrderListAdapter(OrderActivity orderActivity, List<Order> orders) {
		this.orderActivity = orderActivity;
		this.orders = orders;
	}
	
	@Override
	public int getCount() {
		return orders.size();
	}
	
	public class ViewHolder
	{
		public TextView tvStatus;
		public TextView tvMoneyCount;
		public TextView tvHotelName;
		public TextView tvAddress;
		public TextView tvOrderDate;
		public TextView tvHouseName;
		public TextView tvCustomerName;
		public TextView tvCancelBtn;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) orderActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_order, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.text_status);
			viewHolder.tvMoneyCount = (TextView) convertView.findViewById(R.id.text_money_count);
			viewHolder.tvHotelName = (TextView) convertView.findViewById(R.id.text_hotel_name);
			viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.text_hotel_address);
			viewHolder.tvOrderDate = (TextView) convertView.findViewById(R.id.text_order_date);
			viewHolder.tvHouseName = (TextView) convertView.findViewById(R.id.text_order_house_name);
			viewHolder.tvCustomerName = (TextView) convertView.findViewById(R.id.text_cutomer_name);
			viewHolder.tvCancelBtn = (TextView) convertView.findViewById(R.id.text_cancel_button);
			convertView.setTag(viewHolder);
		}
		else
			viewHolder = (ViewHolder) convertView.getTag();
		
		Order order = orders.get(position);
		
		viewHolder.tvStatus.setText(OrderHelper.getStatusString(order.getStatusCode()));
		viewHolder.tvMoneyCount.setText(String.valueOf(order.getPayMoney()));
		viewHolder.tvHotelName.setText(order.getHotelName());
		viewHolder.tvAddress.setText(order.getHotelAddress());
		viewHolder.tvOrderDate.setText(OrderHelper.getOrderDateString(order.getCheckInDate(), order.getCheckOutDate()));
		viewHolder.tvHouseName.setText(OrderHelper.getOrderHouseString(order.getHouseCount(), order.getHouseName()));
		viewHolder.tvCustomerName.setText(order.getCustomerName());
		viewHolder.tvCancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}
	
	

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


}

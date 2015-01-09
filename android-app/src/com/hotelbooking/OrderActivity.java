package com.hotelbooking;

import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.Adapter.OrderListAdapter;
import com.hotelbooking.model.Order;
import com.hotelbooking.network.OrderListDataLoader;
import com.hotelbooking.ui.LoadMoreListView;
import com.hotelbooking.ui.LoadMoreListView.OnLoadMoreListener;
import com.hotelbooking.utils.Const;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class OrderActivity extends Activity {
	
	private static final int PAGE_SIZE = 7;
	
	private LoadMoreListView listView;
	private View emptyView;
	private OrderListAdapter adapter;
	private List<Order> orders = new ArrayList<Order>();
	private int pageNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_order, null);
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
		
		listView = (LoadMoreListView) findViewById(R.id.list_orders);
		emptyView = findViewById(R.id.view_empty);
		listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		startGettingOrderListData();
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				startGettingOrderListData();
			}
		});
		
		
	}
	
	public void onOrderDataReady(boolean isLast, List<Order> orders)
	{
		Log.d("dataa", orders.size() + "");
		this.orders.addAll(orders);
		if (adapter == null)
		{
			adapter = new OrderListAdapter(this, this.orders);
			listView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		listView.onLoadComplete();
		if (isLast)
		{
			if (this.orders.size() == 0)
			{
				emptyView.setVisibility(View.VISIBLE);
				listView.setVisibility(View.INVISIBLE);
			}
			listView.onLoadEnd();
		}
	}
	
	private void startGettingOrderListData()
	{
		OrderListDataLoader orderListDataLoader = new OrderListDataLoader(this);
		orderListDataLoader.startGettingOrderList(Const.currentUser.getId(), pageNum, PAGE_SIZE);
		pageNum++;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
		return true;
	}

}

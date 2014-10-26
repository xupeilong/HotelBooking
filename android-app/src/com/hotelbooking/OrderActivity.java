package com.hotelbooking;

import java.util.List;

import com.hotelbooking.Adapter.OrderListAdapter;
import com.hotelbooking.model.Order;
import com.hotelbooking.network.OrderListDataLoader;
import com.hotelbooking.ui.LoadMoreListView;
import com.hotelbooking.ui.LoadMoreListView.OnLoadMoreListener;
import com.hotelbooking.utils.Const;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class OrderActivity extends Activity {
	
	private static final int PAGE_SIZE = 7;
	
	private LoadMoreListView listView;
	private OrderListAdapter adapter;
	private List<Order> orders;
	private int pageNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		listView = (LoadMoreListView) findViewById(R.id.list_orders);
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
		this.orders.addAll(orders);
		if (orders == null)
		{
			adapter = new OrderListAdapter(this, orders);
			listView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		listView.onLoadComplete();
		if (isLast)
			listView.onLoadEnd();
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

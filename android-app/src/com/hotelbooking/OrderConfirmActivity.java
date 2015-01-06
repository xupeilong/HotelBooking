package com.hotelbooking;

import java.util.Calendar;
import java.util.Date;

import com.hotelbooking.handler.DatePickerHandler;
import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.House;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.DateFormater;
import com.hotelbooking.utils.OrderHelper;
import com.hotelbooking.utils.Result;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderConfirmActivity extends Activity implements DatePickerHandler{
	
	
	private ActionBar actionBar;
	private View actionBarView;
	
	private TextView tvHotelName;
	private TextView tvHotelPrice;
	private View vHouse;
	private TextView tvHouseName;
	private TextView tvHouseCount;
	private EditText etCutstomer;
	private TextView tvMessage;
	private ImageView imgDelButton;
	private TextView tvPayType;
	private TextView tvConfirmButton;
	
	private View vCheckin;
	private TextView tvCheckin;
	private TextView tvCheckinDetail;
	private TextView tvCheckout;
	private TextView tvCheckoutDetail;
	private ImageView imgNightMinus;
	private ImageView imgNightPlus;
	
	private AlertDialog dlgSelectCount;
	private AlertDialog dlgSelectMessage;
	private AlertDialog dlgSelectPayType;
	
	private int payType;
	private Calendar today;
	private Calendar checkinDate;
	private int nights;
	private int count;
	private String name;
	private String message;
	
	private Hotel hotel;
	private House house;
	
	private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;
	
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				Toast.makeText(OrderConfirmActivity.this, (String)msg.obj,
						Toast.LENGTH_SHORT).show();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(OrderConfirmActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OrderConfirmActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(OrderConfirmActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(OrderConfirmActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirm);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_order_confirm, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		Intent intent = getIntent();
		hotel = (Hotel) intent.getSerializableExtra("hotel");
		house = (House) intent.getSerializableExtra("house");
		
		payType = 0;
		today = Calendar.getInstance();
		today.set(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), 
				today.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		checkinDate = (Calendar) intent.getSerializableExtra("checkin_date");
		nights = intent.getIntExtra("nights", 1);
		count = 1;
		
		initViews();
		initListeners();
	}
	
	private void initViews()
	{
		tvHotelName = (TextView) findViewById(R.id.text_hotel_name);
		tvHotelPrice = (TextView) findViewById(R.id.text_hotel_price);
		vHouse = findViewById(R.id.view_house);
		tvHouseName = (TextView) findViewById(R.id.text_house_name);
		tvHouseCount = (TextView) findViewById(R.id.text_house_count);
		etCutstomer = (EditText) findViewById(R.id.edit_customer);
		tvMessage = (TextView) findViewById(R.id.edit_message);
		imgDelButton = (ImageView) findViewById(R.id.image_del_button);
		tvPayType = (TextView) findViewById(R.id.text_pay_type);
		vCheckin = findViewById(R.id.view_checkin_date);
		tvCheckin = (TextView) findViewById(R.id.text_checkin_date);
		tvCheckinDetail = (TextView) findViewById(R.id.text_checkin_date_detail);
		tvCheckout = (TextView) findViewById(R.id.text_checkout_date);
		tvCheckoutDetail = (TextView) findViewById(R.id.text_checkout_date_detail);
		imgNightMinus = (ImageView) findViewById(R.id.image_night_minus);
		imgNightPlus = (ImageView) findViewById(R.id.image_night_plus);
		tvConfirmButton = (TextView) findViewById(R.id.text_confirm_button);
		etCutstomer.setText(Const.currentUser.getName());
		onSetDate(checkinDate);
		tvHotelName.setText(hotel.getName());
		tvHouseName.setText(house.getName());
	}

	private void initListeners()
	{
		
		vCheckin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DatePickerFragment newFragment = new DatePickerFragment();
				newFragment.setContext(OrderConfirmActivity.this);
				newFragment.setHandler(OrderConfirmActivity.this);
			    newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		
		imgNightMinus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNights(nights - 1);
			}
		});
		
		
		
		imgNightPlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNights(nights + 1);
			}
		});
		
		tvConfirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (payType == 0)
				{
					
				}
				else if (payType == 1)
				{
					name = Const.currentUser.getName();
					message = tvMessage.getText().toString();
					OrderHelper.order(OrderConfirmActivity.this, mHandler, name, message, hotel, house, count,
							checkinDate.getTime(), getCheckoutDate().getTime());
				}
			}
		});
		
		vHouse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dlgSelectCount = new AlertDialog.Builder(OrderConfirmActivity.this)
				.setItems(Const.countStringList, new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						count = arg1 + 1;
						tvHouseCount.setText(Const.countStringList[arg1]);
						resetPrice();
					}
				}).create();
				dlgSelectCount.show();
			}
		});
		
		tvMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dlgSelectMessage = new AlertDialog.Builder(OrderConfirmActivity.this)
				.setItems(Const.messageStringList, new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String text = tvMessage.getText().toString();
						if (text.length() > 0)
							text = text + ";";
						tvMessage.setText(text + Const.messageStringList[arg1]);
					}
				}).create();
				dlgSelectMessage.show();
			}
		});

		tvMessage.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (arg0.length() == 0)
					imgDelButton.setVisibility(View.GONE);
				else
					imgDelButton.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imgDelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tvMessage.setText("");
			}
		});
		
		tvPayType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(OrderConfirmActivity.this);
				View dialogView = inflater.inflate(R.layout.dialog_pay_type, null, false);
				View type1 = dialogView.findViewById(R.id.view_pay_type_1);
				View type2 = dialogView.findViewById(R.id.view_pay_type_2);
				type1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						tvPayType.setText(Const.payTypeStringList[0]);
						payType = 0;
						dlgSelectPayType.dismiss();
					}
				});
				type2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						tvPayType.setText(Const.payTypeStringList[1]);
						payType = 1;
						dlgSelectPayType.dismiss();
					}
				});
				dlgSelectPayType = new AlertDialog.Builder(OrderConfirmActivity.this)
				.setTitle("请选择支付方式")
				.setView(dialogView).create();
				dlgSelectPayType.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_confirm, menu);
		return true;
	}
	
	private void resetPrice()
	{
		int price = house.getPrice() * count * nights;
		tvHotelPrice.setText("￥" + String.valueOf(price));
	}

	
	private boolean isCheckinDateValid(Calendar date)
	{
		return date.compareTo(today) >= 0;
	}
	
	private String getDateDesString(Calendar date)
	{
		Calendar nowDate = Calendar.getInstance();
        int dayDiff = DateFormater.getDiffDays(nowDate.getTime(), date.getTime());
        if (dayDiff < 0)
        {
        	Toast.makeText(this, "入住日期不能早于今天", Toast.LENGTH_LONG).show();
        	return "今天";
        }
        else if (dayDiff == 0)
        	return "今天";
        else if (dayDiff == 1)
        	return "明天";
        else if (dayDiff == 2)
        	return "后天";
        else
        	return getDateDetailString(date);
	}
	
	private String getDateDetailString(Calendar date)
	{
		return (date.get(Calendar.MONTH) + 1) + "月" + date.get(Calendar.DAY_OF_MONTH) + "日";
	}
	private void enableNightMinusButton()
	{
		imgNightMinus.setImageResource(R.drawable.selector_night_minus_button);
		imgNightMinus.setClickable(true);
	}
	
	private void disableNightMinusButton()
	{
		imgNightMinus.setImageResource(R.drawable.order_ic_minus_disable);
		imgNightMinus.setClickable(false);
	}
	
	private String getNightsDate(int nights)
	{
		return nights + "夜";
	}
	
	private Calendar getCheckoutDate()
	{
		Calendar checkoutDate = (Calendar) checkinDate.clone();
		checkoutDate.add(Calendar.DAY_OF_YEAR, nights);
		return checkoutDate;
	}
	
	public void setNights(int nights)
	{
		this.nights = nights;
		tvCheckout.setText(getNightsDate(nights));
		tvCheckoutDetail.setText(getDateDetailString(getCheckoutDate()));
		if (nights == 1)
			disableNightMinusButton();
		else
			enableNightMinusButton();
		resetPrice();
	}
	
	@Override
	public void onSetDate(Calendar date) {
		if (!isCheckinDateValid(date))
		{
			Toast.makeText(this, "入住时间不能早于今天", Toast.LENGTH_LONG).show();
			return;
		}
		checkinDate = date;
		tvCheckin.setText(getDateDesString(checkinDate));
		tvCheckinDetail.setText(getDateDetailString(checkinDate));
		setNights(nights);
	}

}

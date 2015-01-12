package com.hotelbooking;

import com.hotelbooking.model.User;
import com.hotelbooking.network.LoginDataLoader;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.ExitAppliation;
import com.hotelbooking.utils.InputChecker;
import com.hotelbooking.utils.PreferenceHelper;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	
	private AutoCompleteTextView tvAccount;
	private EditText etPassword;
	private Button btnLogin;
	private TextView tvRegister;
	private CheckBox cbAutoLogin;
	private LinearLayout llAutoLogin;
	
	String account;
	String password;
	boolean isAutoTemp;
	ActionBar actionBar;
	View actionBarView;
	private ImageView imgBackButton;
	
	private Class nextPageClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ExitAppliation.getInstance().addActivity(this);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_login, null);
		actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		isAutoTemp = false;
		
		try {
			nextPageClass = (Class) getIntent().getExtras().get("next_activity_class");	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		intiViews();
		initListeners();
	}
	
	private void intiViews()
	{
		tvAccount = (AutoCompleteTextView) findViewById(R.id.text_account);
		etPassword = (EditText) findViewById(R.id.text_password);
		btnLogin = (Button) findViewById(R.id.button_login);
		tvRegister = (TextView) actionBarView.findViewById(R.id.text_register_action_bar);
		imgBackButton = (ImageView) actionBarView.findViewById(R.id.button_back);
		cbAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
		llAutoLogin = (LinearLayout) findViewById(R.id.ll_auto_login);
	}
	
	public void initListeners()
	{
		llAutoLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				cbAutoLogin.toggle();
			}
		});
		cbAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				isAutoTemp = arg1;
			}
		});
		
		imgBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				account = tvAccount.getText().toString();
				password = etPassword.getText().toString();
				InputChecker checker = new InputChecker(LoginActivity.this);
				if (checker.checkAccount(account) && checker.checkPassword(password))
				{
					LoginDataLoader loginDataLoader = new LoginDataLoader(LoginActivity.this);
					loginDataLoader.startLogin(account, password);
				}
			}
		});
		
		tvRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent); 
			}
		});
	}
	
	public void onLoginFinished(int resultCode, User user)
	{
		if (resultCode == 0)
		{
			PreferenceHelper helper = new PreferenceHelper(this);
			helper.saveAutoLoginStatus(isAutoTemp);
			if (isAutoTemp)
			{
				Log.d("dataa", "save: " + account);
				if (account != null)
				{
					helper.saveAccount(account);
					helper.savePassword(password);
				}
			}
			
			Const.currentUser = user;
			Intent intent = new Intent();
			if (nextPageClass != null)
			{
				intent.setClass(this, nextPageClass);
				intent.putExtras(getIntent().getExtras());
			}
			else
				intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else if (resultCode == 1)
		{
			prompt("账号或密码错误");
		}
	}
	
	private long exitTime = 0;
	@Override
	public void onBackPressed() {
		long curTime = System.currentTimeMillis();
		if(curTime - exitTime > 2000){
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = curTime;
		}
		else{
			super.onBackPressed();
			ExitAppliation.getInstance().exit();
		}
	}
	
	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	

}

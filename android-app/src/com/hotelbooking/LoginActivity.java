package com.hotelbooking;

import com.hotelbooking.model.User;
import com.hotelbooking.network.LoginDataLoader;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.InputChecker;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	
	private AutoCompleteTextView tvAccount;
	private EditText etPassword;
	private Button btnLogin;
	private TextView tvRegister;
	
	ActionBar actionBar;
	View actionBarView;
	private ImageView imgBackButton;
	
	private Class nextPageClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
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
	}
	
	public void initListeners()
	{
		imgBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String account = tvAccount.getText().toString();
				String password = etPassword.getText().toString();
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
			prompt("’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	

}

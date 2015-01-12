package com.hotelbooking;

import com.hotelbooking.model.User;
import com.hotelbooking.network.RegisterDataLoader;
import com.hotelbooking.utils.ExitAppliation;
import com.hotelbooking.utils.InputChecker;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private EditText etAccount;
	private EditText etName;
	private EditText etPassword;
	private EditText etPasswordRepeat;
	private Button btnRegister;
	private TextView tvLogin;
	private ImageView imgBackBtn;
	
	private ActionBar actionBar;
	private View actionBarView;
	
	private void initViews()
	{
		etAccount = (EditText) findViewById(R.id.edit_account);
		etPassword = (EditText) findViewById(R.id.edit_password);
		etPasswordRepeat = (EditText) findViewById(R.id.edit_password_repeat); 
		etName = (EditText) findViewById(R.id.edit_name);
		btnRegister = (Button) findViewById(R.id.button_register);
		tvLogin = (TextView) actionBarView.findViewById(R.id.text_login_action_bar);
		imgBackBtn = (ImageView) actionBarView.findViewById(R.id.button_back);
	}
	
	private void initListeners()
	{
		imgBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String account = etAccount.getText().toString();
				String password = etPassword.getText().toString();
				String repeatPassword = etPasswordRepeat.getText().toString();
				String name = etName.getText().toString();
				InputChecker checker = new InputChecker(RegisterActivity.this);
				if (checker.checkAccount(account) && checker.checkPassword(password, repeatPassword) && checker.checkName(name))
				{
					RegisterDataLoader registerDataLoader = new RegisterDataLoader(RegisterActivity.this);
					registerDataLoader.startRegister(account, password, name);
				}
			}
		});
		
		tvLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent); 
			}
		});
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ExitAppliation.getInstance().addActivity(this);
		
		ActionBar.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		actionBarView = LayoutInflater.from(this).inflate(
				R.layout.action_bar_register, null);
		actionBar = getActionBar();
		actionBar.setCustomView(actionBarView, layoutParams);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		initViews();
		initListeners();
		
		
	}
	
	public void onRegisterFinished(int resultCode, User user)
	{
		if (resultCode == 0)
		{
			prompt("注册成功");
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
		}
		else if (resultCode == 1)
		{
			prompt("账号已被注册");
		}
		else if (resultCode == 2)
		{
			prompt("格式错误");
		}
		else if (resultCode == 3)
		{
			prompt("数据库错误");
		}
		
	}

	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}

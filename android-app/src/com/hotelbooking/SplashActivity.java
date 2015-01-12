package com.hotelbooking;

import com.hotelbooking.model.User;
import com.hotelbooking.network.LoginDataLoader;
import com.hotelbooking.utils.Const;
import com.hotelbooking.utils.PreferenceHelper;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				PreferenceHelper helper = new PreferenceHelper(SplashActivity.this);
				Boolean isAutoLogin = helper.getAutoLoginStatus();
				String account = helper.getAccount();
				String password = helper.getPassword();
				if (isAutoLogin && account != null && password != null)
				{
					LoginDataLoader loginDataLoader = new LoginDataLoader(SplashActivity.this);
					loginDataLoader.startPreLogin(account, password);
				}
				else
				{
					Intent intent = new Intent(SplashActivity.this,
							LoginActivity.class);
					SplashActivity.this.startActivity(intent);
					SplashActivity.this.finish();
				}
			}
		}, 2000);
	}
	
	public void onLoginFinished(int resultCode, User user)
	{
		if (resultCode == 0)
		{
			Const.currentUser = user;
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		else if (resultCode == 1)
		{
			prompt("’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void prompt(String message)
	{
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

}

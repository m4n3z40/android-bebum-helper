package io.ignit.bebumhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private Handler handler;
	private Runnable starter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		starter = new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);

				finish();
			}
		};

		handler = new Handler();
		handler.postDelayed(starter, 1500);
	}
	
	@Override
	public void onBackPressed() {
		handler.removeCallbacks(starter);

		super.onBackPressed();
	}

}

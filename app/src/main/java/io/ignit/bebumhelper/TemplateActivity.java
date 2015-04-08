package io.ignit.bebumhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class TemplateActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		applyImageTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_info:
				if (!(this instanceof InfoActivity)) {
					Intent infoIntent = new Intent(this, InfoActivity.class);
					startActivity(infoIntent);
				}
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void applyImageTitle() {
		ActionBar actionBar = getSupportActionBar();

		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT,
				Gravity.START | Gravity.CENTER_VERTICAL
		);
		layoutParams.leftMargin = 40;

		ImageView image = new ImageView(actionBar.getThemedContext());
		image.setScaleType(ImageView.ScaleType.CENTER);
		image.setImageResource(R.drawable.logo);
		image.setLayoutParams(layoutParams);

		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(image);
	}

}

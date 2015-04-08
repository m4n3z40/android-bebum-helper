package io.ignit.bebumhelper;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends TemplateActivity {

	private boolean isDualPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		if (findViewById(R.id.content) != null) {
			isDualPane = true;
		}
	}
	
	public void showResult(Bundle args) {
		if (isDualPane) {
			OutputActivity.showResultsInFragment(this, args);
		} else {
			Intent intent = new Intent(MainActivity.this, OutputActivity.class);
			intent.putExtras(args);

			startActivity(intent);
		}
	}
}

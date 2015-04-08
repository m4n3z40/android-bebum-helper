package io.ignit.bebumhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.ignit.bebumhelper.fragments.OutputFragment;

public class OutputActivity extends TemplateActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_output);

		showResultsInFragment(this, getIntent().getExtras());
	}

	public static void showResultsInFragment(TemplateActivity context, Bundle args) {
		Fragment fragment = new OutputFragment();
		fragment.setArguments(args);

		context.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content, fragment)
				.commit();
	}

}

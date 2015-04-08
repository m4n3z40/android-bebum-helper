package io.ignit.bebumhelper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

import io.ignit.bebumhelper.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.ignit.bebumhelper.MainActivity;
import io.ignit.bebumhelper.utils.Utils;


public class InputFragment extends Fragment {

	private static final String CALENDAR = "calendar";
	private static final String BEER_QUANTITY_1 = "beer_1";
	private static final String BEER_QUANTITY_2 = "beer_2";
	private static final String BEER_QUANTITY_3 = "beer_3";
	private static final String WINE_QUANTITY_1 = "wine_1";
	private static final String WINE_QUANTITY_2 = "wine_2";
	private static final String WINE_QUANTITY_3 = "wine_3";
	private static final String VODKA_QUANTITY_1 = "vodka_1";
	private static final String VODKA_QUANTITY_2 = "vodka_2";
	private static final String VODKA_QUANTITY_3 = "vodka_3";
	private static final int WEIGHT_OFFSET = 30;
	private static final int HEIGHT_OFFSET = 140;
	private static final int OLD_OFFSET = 18;

	private Calendar calendar;
	private ToggleButton btnSex;
	private ToggleButton btnRare;
	private ToggleButton btnOften;
	private ToggleButton btnSometimes;

	private QuantityWrapper beerQuantity1 = new QuantityWrapper();
	private QuantityWrapper beerQuantity2 = new QuantityWrapper();
	private QuantityWrapper beerQuantity3 = new QuantityWrapper();
	private QuantityWrapper wineQuantity1 = new QuantityWrapper();
	private QuantityWrapper wineQuantity2 = new QuantityWrapper();
	private QuantityWrapper wineQuantity3 = new QuantityWrapper();
	private QuantityWrapper vodkaQuantity1 = new QuantityWrapper();
	private QuantityWrapper vodkaQuantity2 = new QuantityWrapper();
	private QuantityWrapper vodkaQuantity3 = new QuantityWrapper();

	private DatePicker datePicker;
	private TimePicker timePicker;
	private SeekBar sbWeight;
	private SeekBar sbHeight;
	private SeekBar sbOld;
	private TextView tvMale;
	private TextView tvFemale;

	private TextView tvWeightResult;
	private TextView tvHeightResult;
	private TextView tvOldResult;

	private Button btnMinusBeer1;
	private Button btnPlusBeer1;
	private Button btnMinusBeer2;
	private Button btnPlusBeer2;
	private Button btnMinusBeer3;
	private Button btnPlusBeer3;
	private Button btnMinusWine1;
	private Button btnPlusWine1;
	private Button btnMinusWine2;
	private Button btnPlusWine2;
	private Button btnMinusWine3;
	private Button btnPlusWine3;
	private Button btnMinusVodka1;
	private Button btnPlusVodka1;
	private Button btnMinusVodka2;
	private Button btnPlusVodka2;
	private Button btnMinusVodka3;
	private Button btnPlusVodka3;

	private Button btnResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_input, container);
		associatePropertiesToViews(rootView);

		calendar = GregorianCalendar.getInstance();
		timePicker.setIs24HourView(true);
		datePicker.setMaxDate(calendar.getTimeInMillis());

		recoverSavedState(savedInstanceState);

		tvMale.setTextColor(getResources().getColor(R.color.white));
		tvFemale.setTextColor(getResources().getColor(R.color.red));

		btnSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (btnSex.isChecked()) {
					tvMale.setTextColor(getResources().getColor(R.color.white));
					tvFemale.setTextColor(getResources().getColor(R.color.red));
				} else {
					tvMale.setTextColor(getResources().getColor(R.color.red));
					tvFemale.setTextColor(getResources()
							.getColor(R.color.white));

				}
			}
		});

		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				Calendar now = GregorianCalendar.getInstance();

				if (Utils.isDatePickerSetTo(datePicker, now)) {
					if (timePicker.getCurrentHour() >= now.get(Calendar.HOUR_OF_DAY)) {
						timePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY) - 1);
						timePicker.setCurrentMinute(0);
					}

					if (timePicker.getCurrentHour() >= now.get(Calendar.HOUR_OF_DAY) - 1) {
						timePicker.setCurrentMinute(0);
					}
				}

				calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
				calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
			}
		});

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			Calendar now = GregorianCalendar.getInstance();

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				if (Utils.isDatePickerSetTo(datePicker, now)) {
					timePicker.setCurrentHour(8);
					timePicker.setCurrentMinute(0);
				}

				calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
			}
		});

		datePicker.updateDate(year, month, day);

		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(0);

		beerQuantity1.tv.setText(String.valueOf(beerQuantity1.quantity));
		beerQuantity2.tv.setText(String.valueOf(beerQuantity2.quantity));
		beerQuantity3.tv.setText(String.valueOf(beerQuantity3.quantity));
		wineQuantity1.tv.setText(String.valueOf(wineQuantity1.quantity));
		wineQuantity2.tv.setText(String.valueOf(wineQuantity2.quantity));
		wineQuantity3.tv.setText(String.valueOf(wineQuantity3.quantity));
		vodkaQuantity1.tv.setText(String.valueOf(vodkaQuantity1.quantity));
		vodkaQuantity2.tv.setText(String.valueOf(vodkaQuantity2.quantity));
		vodkaQuantity3.tv.setText(String.valueOf(vodkaQuantity3.quantity));
		
		btnOften.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnOften.setChecked(true);
				btnSometimes.setChecked(false);
				btnRare.setChecked(false);
			}
		});

		btnSometimes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnSometimes.setChecked(true);
				btnRare.setChecked(false);
				btnOften.setChecked(false);
			}
		});

		btnRare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnRare.setChecked(true);
				btnSometimes.setChecked(false);
				btnOften.setChecked(false);
			}
		});

		sbWeight.setOnSeekBarChangeListener(new SeekBarListener(tvWeightResult, WEIGHT_OFFSET, getResources().getString(R.string.kg)));

		sbHeight.setOnSeekBarChangeListener(new SeekBarListener(tvHeightResult, HEIGHT_OFFSET, getResources().getString(R.string.cm)));

		sbOld.setOnSeekBarChangeListener(new SeekBarListener(tvOldResult, OLD_OFFSET, getResources().getString(R.string.year)));

		btnMinusBeer1.setOnClickListener(new MinusListener(beerQuantity1));
		btnPlusBeer1.setOnClickListener(new PlusListener(beerQuantity1));
		btnMinusBeer2.setOnClickListener(new MinusListener(beerQuantity2));
		btnPlusBeer2.setOnClickListener(new PlusListener(beerQuantity2));
		btnMinusBeer3.setOnClickListener(new MinusListener(beerQuantity3));
		btnPlusBeer3.setOnClickListener(new PlusListener(beerQuantity3));
		btnMinusWine1.setOnClickListener(new MinusListener(wineQuantity1));
		btnPlusWine1.setOnClickListener(new PlusListener(wineQuantity1));
		btnMinusWine2.setOnClickListener(new MinusListener(wineQuantity2));
		btnPlusWine2.setOnClickListener(new PlusListener(wineQuantity2));
		btnMinusWine3.setOnClickListener(new MinusListener(wineQuantity3));
		btnPlusWine3.setOnClickListener(new PlusListener(wineQuantity3));
		btnMinusVodka1.setOnClickListener(new MinusListener(vodkaQuantity1));
		btnPlusVodka1.setOnClickListener(new PlusListener(vodkaQuantity1));
		btnMinusVodka2.setOnClickListener(new MinusListener(vodkaQuantity2));
		btnPlusVodka2.setOnClickListener(new PlusListener(vodkaQuantity2));
		btnMinusVodka3.setOnClickListener(new MinusListener(vodkaQuantity3));
		btnPlusVodka3.setOnClickListener(new PlusListener(vodkaQuantity3));

		btnResult.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				double obBeer = ((beerQuantity1.quantity * 500 * 0.03) * 0.79)
						+ ((beerQuantity2.quantity * 500 * 0.045) * 0.79)
						+ ((beerQuantity3.quantity * 500 * 0.06) * 0.79);
				double obWine = ((wineQuantity1.quantity * 200 * 0.12) * 0.79)
						+ ((wineQuantity2.quantity * 200 * 0.15) * 0.79)
						+ ((wineQuantity3.quantity * 200 * 0.18) * 0.79);
				double obVodka = ((vodkaQuantity1.quantity * 50 * 0.40) * 0.79)
						+ ((vodkaQuantity2.quantity * 50 * 0.50) * 0.79)
						+ ((vodkaQuantity3.quantity * 50 * 0.80) * 0.79);

				double a = obBeer + obWine + obVodka;

				int weight = sbWeight.getProgress() + WEIGHT_OFFSET;
				int height = sbHeight.getProgress() + HEIGHT_OFFSET;
				int old = sbOld.getProgress() + OLD_OFFSET;
				int time = Utils.countHoursToNow(calendar);

				double e;
				if (btnRare.isChecked()) {
					e = 0.1;
				} else if (btnOften.isChecked()) {
					e = 0.2;
				} else {
					e = 0.15;
				}

				double tbw;

				if (btnSex.isChecked()) {
					tbw = 2.447 - (0.09156 * old) + (0.1074 * height)
							+ (0.3362 * weight);

				} else {
					tbw = -2.097 + (0.1069 * height) + (0.2466 * weight);
				}

				double p = (a / tbw) * 0.8 - (time * e);

				Bundle args = new Bundle();
				args.putString(OutputFragment.START_DATE, Utils.dateToString(calendar.getTime()));
				args.putDouble(OutputFragment.ALCOHOL_CONCENTRATION, p);
				args.putDouble(OutputFragment.ELIMINATION_COEF, e);
				args.putInt(OutputFragment.DURATION, time);
				((MainActivity) getActivity()).showResult(args);
			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		refreshCalendar();
		outState.putLong(CALENDAR, calendar.getTimeInMillis());
		outState.putInt(BEER_QUANTITY_1, beerQuantity1.quantity);
		outState.putInt(BEER_QUANTITY_2, beerQuantity2.quantity);
		outState.putInt(BEER_QUANTITY_3, beerQuantity3.quantity);
		outState.putInt(WINE_QUANTITY_1, wineQuantity1.quantity);
		outState.putInt(WINE_QUANTITY_2, wineQuantity2.quantity);
		outState.putInt(WINE_QUANTITY_3, wineQuantity3.quantity);
		outState.putInt(VODKA_QUANTITY_1, vodkaQuantity1.quantity);
		outState.putInt(VODKA_QUANTITY_2, vodkaQuantity2.quantity);
		outState.putInt(VODKA_QUANTITY_3, vodkaQuantity3.quantity);
	}

	protected void associatePropertiesToViews(View rootView) {
		btnSex = (ToggleButton) rootView.findViewById(R.id.btn_sex);
		btnRare = (ToggleButton) rootView.findViewById(R.id.btn_rare);
		btnOften = (ToggleButton) rootView.findViewById(R.id.btn_often);
		btnSometimes = (ToggleButton) rootView.findViewById(R.id.btn_sometimes);
		btnResult = (Button) rootView.findViewById(R.id.btn_result);
		datePicker = (DatePicker) rootView.findViewById(R.id.date_picker);
		timePicker = (TimePicker) rootView.findViewById(R.id.time_picker);
		sbWeight = (SeekBar) rootView.findViewById(R.id.sb_weight);
		sbHeight = (SeekBar) rootView.findViewById(R.id.sb_height);
		sbOld = (SeekBar) rootView.findViewById(R.id.sb_old);
		tvMale = (TextView) rootView.findViewById(R.id.tv_male);
		tvFemale = (TextView) rootView.findViewById(R.id.tv_female);
		tvWeightResult = (TextView) rootView.findViewById(R.id.tv_weight_result);
		tvHeightResult = (TextView) rootView.findViewById(R.id.tv_height_result);
		tvOldResult = (TextView) rootView.findViewById(R.id.tv_old_result);
		beerQuantity1.tv = (TextView) rootView.findViewById(R.id.tv_beer1_val);
		beerQuantity2.tv = (TextView) rootView.findViewById(R.id.tv_beer2_val);
		beerQuantity3.tv = (TextView) rootView.findViewById(R.id.tv_beer3_val);
		wineQuantity1.tv = (TextView) rootView.findViewById(R.id.tv_wine1_val);
		wineQuantity2.tv = (TextView) rootView.findViewById(R.id.tv_wine2_val);
		wineQuantity3.tv = (TextView) rootView.findViewById(R.id.tv_wine3_val);
		vodkaQuantity1.tv = (TextView) rootView.findViewById(R.id.tv_vodka1_val);
		vodkaQuantity2.tv = (TextView) rootView.findViewById(R.id.tv_vodka2_val);
		vodkaQuantity3.tv = (TextView) rootView.findViewById(R.id.tv_vodka3_val);
		btnMinusBeer1 = (Button) rootView.findViewById(R.id.btn_minus_beer1);
		btnPlusBeer1 = (Button) rootView.findViewById(R.id.btn_plus_beer1);
		btnMinusBeer2 = (Button) rootView.findViewById(R.id.btn_minus_beer2);
		btnPlusBeer2 = (Button) rootView.findViewById(R.id.btn_plus_beer2);
		btnMinusBeer3 = (Button) rootView.findViewById(R.id.btn_minus_beer3);
		btnPlusBeer3 = (Button) rootView.findViewById(R.id.btn_plus_beer3);
		btnMinusWine1 = (Button) rootView.findViewById(R.id.btn_minus_wine1);
		btnPlusWine1 = (Button) rootView.findViewById(R.id.btn_plus_wine1);
		btnMinusWine2 = (Button) rootView.findViewById(R.id.btn_minus_wine2);
		btnPlusWine2 = (Button) rootView.findViewById(R.id.btn_plus_wine2);
		btnMinusWine3 = (Button) rootView.findViewById(R.id.btn_minus_wine3);
		btnPlusWine3 = (Button) rootView.findViewById(R.id.btn_plus_wine3);
		btnMinusVodka1 = (Button) rootView.findViewById(R.id.btn_minus_vodka1);
		btnPlusVodka1 = (Button) rootView.findViewById(R.id.btn_plus_vodka1);
		btnMinusVodka2 = (Button) rootView.findViewById(R.id.btn_minus_vodka2);
		btnPlusVodka2 = (Button) rootView.findViewById(R.id.btn_plus_vodka2);
		btnMinusVodka3 = (Button) rootView.findViewById(R.id.btn_minus_vodka3);
		btnPlusVodka3 = (Button) rootView.findViewById(R.id.btn_plus_vodka3);
	}

	protected void recoverSavedState(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			calendar.set(Calendar.HOUR_OF_DAY, 20);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		} else {
			calendar.setTimeInMillis(savedInstanceState.getLong(CALENDAR));
			beerQuantity1.quantity = savedInstanceState.getInt(BEER_QUANTITY_1);
			beerQuantity2.quantity = savedInstanceState.getInt(BEER_QUANTITY_2);
			beerQuantity3.quantity = savedInstanceState.getInt(BEER_QUANTITY_3);
			wineQuantity1.quantity = savedInstanceState.getInt(WINE_QUANTITY_1);
			wineQuantity2.quantity = savedInstanceState.getInt(WINE_QUANTITY_2);
			wineQuantity3.quantity = savedInstanceState.getInt(WINE_QUANTITY_3);
			vodkaQuantity1.quantity = savedInstanceState.getInt(VODKA_QUANTITY_1);
			vodkaQuantity2.quantity = savedInstanceState.getInt(VODKA_QUANTITY_2);
			vodkaQuantity3.quantity = savedInstanceState.getInt(VODKA_QUANTITY_3);
		}
	}

	private void refreshCalendar() {
		calendar.set(
			datePicker.getYear(),
			datePicker.getMonth(),
			datePicker.getDayOfMonth(),
			timePicker.getCurrentHour(),
			timePicker.getCurrentMinute()
		);
	}

	private class SeekBarListener implements OnSeekBarChangeListener {
		private TextView tv;
		private int offset;
		private String unit;

		public SeekBarListener(TextView tv, int offset, String unit) {
			this.tv = tv;
			this.offset = offset;
			this.unit = unit;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			this.tv.setText(String.valueOf(progress + this.offset) + " " + this.unit);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	private class MinusListener implements OnClickListener {
		private QuantityWrapper wrapper;

		public MinusListener(QuantityWrapper wrapper) {
			this.wrapper = wrapper;
		}

		@Override
		public void onClick(View v) {
			if (wrapper.quantity > 0) {
				--wrapper.quantity;
				wrapper.tv.setText(String.valueOf(wrapper.quantity));
			}
		}
	}

	private class PlusListener implements OnClickListener {
		private QuantityWrapper wrapper;

		private PlusListener(QuantityWrapper wrapper) {
			this.wrapper = wrapper;
		}

		@Override
		public void onClick(View v) {
			wrapper.quantity++;
			wrapper.tv.setText(String.valueOf(wrapper.quantity));
		}
	}

	private class QuantityWrapper {
		int quantity;
		TextView tv;
	}
}

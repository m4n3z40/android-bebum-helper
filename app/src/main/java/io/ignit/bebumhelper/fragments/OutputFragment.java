package io.ignit.bebumhelper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import io.ignit.bebumhelper.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.ignit.bebumhelper.adapters.ResultListAdapter;
import io.ignit.bebumhelper.model.Result;
import io.ignit.bebumhelper.utils.Utils;


public class OutputFragment extends Fragment {

	public static final String ALCOHOL_CONCENTRATION = "alco_contrent";
	public static final String START_DATE = "start_date";
	public static final String DURATION = "duration";
	public static final String ELIMINATION_COEF = "elim_coef";

	private DecimalFormat df = new DecimalFormat("#0.00");
	private Result currentValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_output, container, false);

		double p = getArguments().getDouble(ALCOHOL_CONCENTRATION);
		Date startDate = Utils.stringToDate(getArguments().getString(START_DATE));
		int duration = getArguments().getInt(DURATION);
		double e = getArguments().getDouble(ELIMINATION_COEF);

		List<Result> data = prepareData(p, startDate, duration, e);

		applyHeaderContentsAndStyle(rootView, data);

		ResultListAdapter adapter = new ResultListAdapter(getActivity(), data);

		ListView list = (ListView) rootView.findViewById(R.id.list);
		list.setAdapter(adapter);

		return rootView;
	}

	protected void applyHeaderContentsAndStyle(View rootView, List<Result> data) {
		if (data.size() == 0) return;

		TextView tvResult = (TextView) rootView.findViewById(R.id.tv_result);

		tvResult.setText(df.format(currentValue.getValue()) + getResources().getString(R.string.promil));

		if (currentValue.getValue() > 0.50) {
			tvResult.setTextColor(getResources().getColor(R.color.red));
		} else if (currentValue.getValue() > 0.20) {
			tvResult.setTextColor(getResources().getColor(R.color.orange));
		} else {
			tvResult.setTextColor(getResources().getColor(R.color.green));
		}

		Calendar cNow = Calendar.getInstance();
		cNow.setTime(Utils.stringToDate(data.get(data.size() - 1).getDate()));
		int oursToDetox = Utils.countHoursToNow(cNow);

		if (oursToDetox > 0) {
			oursToDetox = 0;
		} else {
			oursToDetox = oursToDetox * -1;
		}

		TextView tvResultDetox = (TextView) rootView.findViewById(R.id.tv_result_detox);

		String resultDetoxText = "" + oursToDetox + " " + getResources().getString(R.string.h);

		tvResultDetox.setText(oursToDetox == 0 ? getResources().getString(R.string.not_drunk) : resultDetoxText);
	}

	private List<Result> prepareData(double p, Date startDate, int duration, double e) {
		List<Result> data = new ArrayList<>();
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(startDate);
		sDate.add(Calendar.MINUTE, 30);

		double raise = p / (duration * 2);
		double fall = e / 2;
		double value = 0;
		int period = 0;
		boolean hasPassedNow = false;
		Calendar eDate = Calendar.getInstance();

		while(value >= 0) {
			Result result = new Result();
			if (period < duration * 2)
				value += raise;
			else {
				value -= fall;
			}

			result.setValue(value < 0 ? 0 : value);
			result.setDate(Utils.dateToString(sDate.getTime()));
			data.add(result);

			if (!hasPassedNow && (sDate.equals(eDate) || sDate.after(eDate))) {
				currentValue = result;
				hasPassedNow = true;
			}

			sDate.add(Calendar.MINUTE, 30);

			period++;
		}

		if (currentValue == null) {
			currentValue = new Result();
			currentValue.setValue(value < 0 ? 0 : value);
			currentValue.setDate(Utils.dateToString(sDate.getTime()));
		}

		return data;
	}
}

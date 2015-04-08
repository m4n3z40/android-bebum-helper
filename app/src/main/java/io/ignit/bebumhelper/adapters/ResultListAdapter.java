package io.ignit.bebumhelper.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.List;

import io.ignit.bebumhelper.R;
import io.ignit.bebumhelper.model.Result;

public class ResultListAdapter extends ArrayAdapter<Result> {

	private Context context;
	private List<Result> data;
	private int layoutResourceId;
	private DecimalFormat df = new DecimalFormat("#0.00");

	public ResultListAdapter(Context context, List<Result> data) {
		super(context, R.layout.item_result, data);

		this.context = context;
		this.layoutResourceId = R.layout.item_result;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ResultHolder holder = null;

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			view = inflater.inflate(layoutResourceId, parent, false);

			holder = new ResultHolder();
			holder.tvDate = (TextView) view.findViewById(R.id.tv_result_date);
			holder.tvValue = (TextView) view.findViewById(R.id.tv_result_value);

			view.setTag(holder);
		} else {
			view = convertView;

			holder = (ResultHolder) view.getTag();
		}

		applyItemContentAndStyle(holder, position);

		return view;
	}

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}

		return data.size();
	}

	@Override
	public Result getItem(int position) {
		return data.get(position);
	}

	public void addData(List<Result> results) {
		for (Result result : results) {
			int index = data.indexOf(result);

			if (index == -1) {
				data.add(result);
			} else {
				data.set(index, result);
			}
		}

		notifyDataSetChanged();
	}

	protected void applyItemContentAndStyle(ResultHolder holder, int position) {
		Resources resources = context.getResources();

		holder.tvDate.setText(data.get(position).getDate());
		holder.tvDate.setTextColor(resources.getColor(R.color.white));

		if (data.get(position).getValue() > 0.50) {
			holder.tvDate.setBackgroundColor(resources.getColor(R.color.red));
			holder.tvValue.setBackgroundColor(resources.getColor(R.color.red));
		} else if (data.get(position).getValue() > 0.20) {
			holder.tvDate.setBackgroundColor(resources.getColor(R.color.orange));
			holder.tvValue.setBackgroundColor(resources.getColor(R.color.orange));
		} else {
			holder.tvDate.setBackgroundColor(resources.getColor(R.color.green));
			holder.tvValue.setBackgroundColor(resources.getColor(R.color.green));
		}

		holder.tvValue.setText(df.format(data.get(position).getValue()) + context.getResources().getString(R.string.promil));
		holder.tvValue.setTextColor(resources.getColor(R.color.white));
	}

	private class ResultHolder {
		TextView tvDate;
		TextView tvValue;
	}
}

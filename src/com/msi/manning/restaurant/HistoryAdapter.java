package com.msi.manning.restaurant;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msi.manning.restaurant.data.Review;

import java.util.*;

/**
 * Custom adapter for "History" model objects.
 * 
 * @author charliecollins
 */
public class HistoryAdapter extends BaseAdapter {

	private static final String CLASSTAG = HistoryAdapter.class.getSimpleName();
	private final Context context;
	private final List<String> history;

	public HistoryAdapter(Context context, List<String> history) {
		this.context = context;
		this.history = history;
		Log.v(Constants.LOGTAG, " " + HistoryAdapter.CLASSTAG
				+ " reviews size - " + this.history.size());
	}

	@Override
	public int getCount() {
		return this.history.size();
	}

	@Override
	public Object getItem(int position) {
		return this.history.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String history = this.history.get(position);
		return new HistoryListView(this.context, history);
	}

	/**
	 * ReviewListView that adapter returns as it's view item per row.
	 * 
	 * @author charliecollins
	 */
	private final class HistoryListView extends LinearLayout {

		private TextView history;

		public HistoryListView(Context context, String history) {
			super(context);
			
			setOrientation(LinearLayout.VERTICAL);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 3, 5, 0);
			//Toast.makeText(this, , 20000).show();
			this.history = new TextView(context);
			this.history.setText(history);
			this.history.setTextSize(20f);
			this.history.setTextColor(Color.RED);
			this.addView(this.history, params);
			
		}
	}

}

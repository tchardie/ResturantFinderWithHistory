package com.msi.manning.restaurant;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.msi.manning.restaurant.data.Review;
import com.msi.manning.restaurant.data.ReviewFetcher;

import java.util.*;

/**
 * "List" of reviews screen - show reviews that match Criteria user selected.
 * Users ReviewFetcher which makes a Google Base call via Rome.
 * 
 * @author charliecollins
 */
public class HistoryList extends ListActivity {

	private static final String CLASSTAG = HistoryList.class.getSimpleName();
	private static final int MENU_CHANGE_CRITERIA = Menu.FIRST + 1;
	private static final int MENU_GET_NEXT_PAGE = Menu.FIRST;
	private static final int NUM_RESULTS_PER_PAGE = 5;

	private TextView empty;
	private ProgressDialog progressDialog;
	private HistoryAdapter historyAdapter;

	private List<String> history;
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(final Message msg) {
			Log.v(Constants.LOGTAG, " " + HistoryList.CLASSTAG
					+ " worker thread done, setup ReviewAdapter");
			progressDialog.dismiss();
			if ((history == null) || (history.size() == 0)) {
				empty.setText("No Data");
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(Constants.LOGTAG, " " + HistoryList.CLASSTAG + " onCreate");

		// NOTE* This Activity MUST contain a ListView named "@android:id/list"
		// (or "list" in code) in order to be customized
		// http://code.google.com/android/reference/android/app/ListActivity.html
		this.setContentView(R.layout.history_list);

		this.empty = (TextView) findViewById(R.id.empty);

		// set list properties
		ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setEmptyView(this.empty);

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(Constants.LOGTAG, " " + HistoryList.CLASSTAG + " onResume");

		loadHistory();

		// Toast.makeText(this, "After Load", 20000).show();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		String s = history.get(position);
		
		RestaurantFinderApplication.RecentHistory<Integer, String> recentHistory = application
				.getRecentHistory();
		recentHistory.put(s.hashCode(), s);
		
		historyAdapter = new HistoryAdapter(HistoryList.this, history);
		setListAdapter(historyAdapter);
		Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
		startActivity(intent);
	}

	private void loadHistory() {

		Log.v(Constants.LOGTAG, " " + HistoryList.CLASSTAG + " loadHistory");
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		RestaurantFinderApplication.RecentHistory<Integer, String> recentHistory = application
				.getRecentHistory();
		history = new ArrayList<String>();
		Collection<String> c = (Collection<String>) recentHistory.values();
		Iterator<String> i = c.iterator();
		while (i.hasNext()) {
			history.add(0, i.next());
		}
		if ((history == null) || (history.size() == 0)) {
			empty.setText("No Data");
		} else {
			historyAdapter = new HistoryAdapter(HistoryList.this, history);
			setListAdapter(historyAdapter);
			// setListAdapter(new ArrayAdapter<String>(this,
			// R.layout.history_list, history));
		}
		// Toast.makeText(this, recentHistory.values().toString(),
		// 20000).show();
	}
}

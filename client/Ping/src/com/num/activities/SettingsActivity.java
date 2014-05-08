package com.num.activities;

import java.util.ArrayList;
import com.num.R;
import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.FakeListener;
import com.num.models.ActivityItem;
import com.num.models.Row;
import com.num.tasks.ValuesTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class SettingsActivity extends Activity {
	private ListView listview;
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		listview = (ListView) findViewById(R.id.settings_list_view);

		serverhelper = new ThreadPoolHelper(10, 30);

		serverhelper.execute(new ValuesTask(this, new FakeListener()));
		ServiceHelper.processRestartService(this);
		ArrayList<Row> cells = new ArrayList<Row>();

		cells.add(new Row(new ActivityItem("Choose Data Cap",
				"Choose how much data you get by cycle", new Handler() {
					public void handleMessage(Message msg) {
						Intent myIntent = new Intent(activity,
								DataCapActivity.class);
						myIntent.putExtra("force", true);
						startActivity(myIntent);
					}
				}, R.drawable.throughput)));
		
		cells.add(new Row(new ActivityItem("Choose Monthly Price",
				"Choose how much you pay by month", new Handler() {
					public void handleMessage(Message msg) {
						Intent myIntent = new Intent(activity,
								BillingCostActivity.class);
						myIntent.putExtra("force", true);
						startActivity(myIntent);
					}
				}, R.drawable.price)));
		
		cells.add(new Row(new ActivityItem("Choose Billing Cycle",
				"Choose when your billing cycle begins", new Handler() {
					public void handleMessage(Message msg) {
						Intent myIntent = new Intent(activity,
								BillingCycleActivity.class);
						myIntent.putExtra("force", true);
						startActivity(myIntent);
					}
				}, R.drawable.date)));
		ItemAdapter itemadapter = new ItemAdapter(activity, cells);
		for (Row cell : cells)
			itemadapter.add(cell);
		listview.setAdapter(itemadapter);
		itemadapter.notifyDataSetChanged();
		UIUtil.setListViewHeightBasedOnChildren(listview, itemadapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

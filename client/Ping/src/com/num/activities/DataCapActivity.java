package com.num.activities;

import com.num.R;
import com.num.helpers.UserDataHelper;
import com.num.utils.PreferencesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DataCapActivity extends Activity {

	private RadioGroup radioGroup;
	private int[] limit_val= {-1,0,250,500,750,1000,2000,9999};
	private String[] limit_text = {"Don't have one","Don't know","250 MB","500 MB","750 MB","1 GB","2 GB","More than 2GB"};
	private boolean force = false;
	private UserDataHelper userhelp;
	private Button saveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		userhelp = new UserDataHelper(this);
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		
		//Skip if data is already present
		if(!force && PreferencesUtil.contains("dataLimit", this)){
			finish();
			Intent myIntent = new Intent(this, AnalysisActivity.class);
			startActivity(myIntent);
		}
		
		setContentView(R.layout.activity_data_cap);
		int cap = userhelp.getDataCap();
		radioGroup = (RadioGroup) findViewById(R.id.data_cap_radio_group);
		LinearLayout.LayoutParams rg = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		for(int i=0; i<limit_text.length; i++){
			RadioButton radiobutton = new RadioButton(this);
			radiobutton.setText(limit_text[i]);
			radiobutton.setId(i);
			radiobutton.setTextColor(Color.GRAY);
			if(cap==limit_val[i])
				radiobutton.setChecked(true);
			radioGroup.addView(radiobutton, rg);
		}
		saveButton = (Button) findViewById(R.id.data_cap_save_button);
		saveButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
				if(checkedRadioButton<0) return;
				userhelp.setDataCap(limit_val[checkedRadioButton]);
				finish();
				Intent myIntent;
				if(force){
					myIntent = new Intent(v.getContext(), DataFormActivity.class);					
				}
				else{
					myIntent = new Intent(v.getContext(), BillingCycleActivity.class);
				}
				myIntent.putExtra("force", force);
				startActivity(myIntent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_cap, menu);
		return true;
	}

}

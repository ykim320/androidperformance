package com.num.activities;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.R;
import com.num.helpers.UserDataHelper;
import com.num.utils.PreferencesUtil;

public class EmailActivity extends TrackedActivity{

	private UserDataHelper userhelp;
	private Activity activity;
	private boolean force;
	private Button enterButton, skipButton;
	private EditText emailInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raffle);
		activity = this;
		Bundle extras = getIntent().getExtras();
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		
		//Skip if data is already present
		if(!force && PreferencesUtil.contains("emailData", this)){
			finish();
			Intent myIntent = new Intent(this, MainActivity.class);
			startActivity(myIntent);
		}
		
		userhelp = new UserDataHelper(this);
		enterButton = (Button) findViewById(R.id.raffle_enter_button);
		skipButton = (Button) findViewById(R.id.raffle_skip_button);
		emailInput = (EditText) findViewById(R.id.email_for_raffle);
		emailInput.setText(getAccountEmail());
		
		enterButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				String text = emailInput.getText().toString();
				if(text.length()!=0 && !text.equals("Enter email address")){
					userhelp.setEmailData(text);
				}else{
					userhelp.setEmailData("N/A");
				}
				finish();
				if(force){
					finishActivity(0);
					return;
				}
				else{
					Intent myIntent = new Intent(v.getContext(), MainActivity.class);
					myIntent.putExtra("force", force);
					startActivity(myIntent);
				}
			}
		});
		
		skipButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				finish();
				if(force){
					finishActivity(0);
					return;		
				}
				else{
					Intent myIntent = new Intent(v.getContext(), MainActivity.class);
					myIntent.putExtra("force", force);
					startActivity(myIntent);
				}
			}
		});
	}
	
	private String getAccountEmail(){
		Account account = null;
		try{
			account = AccountManager.get(activity.getBaseContext()).getAccounts()[0];
		}catch(Exception e){
			
		}
		
		if(account == null){
			return "Enter email address";
		}else{
			return account.name;
		}
	}
	
}

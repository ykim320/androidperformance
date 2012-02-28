package com.ping.tasks;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ping.helpers.DeviceHelper;
import com.ping.helpers.PingHelper;
import com.ping.listeners.ResponseListener;
import com.ping.models.Address;
import com.ping.models.Measurement;
import com.ping.models.Ping;
import com.ping.utils.HTTPUtil;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class PingTask extends ServerTask{
	Address dst;
	int count;
	public PingTask(Context context, Map<String, String> reqParams, Address dst, int count,
			ResponseListener listener) {
		super(context, reqParams, listener);
		this.dst  = dst;
		this.count = count;
	}

	@Override
	public void runTask() {
		
		Ping ping = PingHelper.pingHelp(dst, count);
		this.getResponseListener().onCompletePing(ping);
	}

	@Override
	public String toString() {
		return "Ping Task";
	}
	

}

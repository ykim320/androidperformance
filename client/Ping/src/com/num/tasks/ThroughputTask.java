package com.num.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Message;

import com.num.helpers.ThroughputHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.ResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.ThroughputData;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.utils.DeviceUtil;
import com.num.utils.ThroughputDataSource;

public class ThroughputTask extends ServerTask{
	
	public ThroughputTask(Context context, Map<String, String> reqParams, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		
	}

	@Override
	public void runTask() {
		
		try {
			
			Throughput t = ThroughputHelper.getThroughput(getContext(),getResponseListener());
			getResponseListener().onCompleteThroughput(t);

			String connection = DeviceUtil.getNetworkInfo(getContext());
			
			ThroughputDataSource datasource = new ThroughputDataSource(getContext());
			datasource.open();
			
			List<ThroughputData> values = datasource.getAllThroughputData();
			datasource.createThroughput(t, connection);
			values = datasource.getAllThroughputData();
			datasource.close();
		} catch (Exception e) {
			getResponseListener().onException(e);
		}
		
	}

	@Override
	public String toString() {
		return "ThroughputTask";
	}
	
}

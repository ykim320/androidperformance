package com.num.tasks;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.num.helpers.PingHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Address;
import com.num.models.LastMile;
import com.num.models.Ping;
import com.num.models.PingData;
import com.num.models.ThroughputData;
import com.num.utils.DeviceUtil;
import com.num.utils.PingDataSource;

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
		if (dst.getType().equals("ping")) {
			Ping ping = PingHelper.pingHelp(dst, count);
			this.getResponseListener().onCompletePing(ping);

			String connection = DeviceUtil.getNetworkInfo(getContext());
			//PingDataSource datasource = new PingDataSource(getContext());
			//datasource.open();
			//List<PingData> values = datasource.getAllPingData();
			//datasource.createPing(ping, connection);
			//values = datasource.getAllPingData();
			//datasource.close();
			
		}
		else if (dst.getType().equals("firsthop")){
			LastMile lastMile = PingHelper.firstHopHelp(dst, count);
			this.getResponseListener().onCompleteLastMile(lastMile);
		}
	}

	@Override
	public String toString() {
		return "Ping Task";
	}
	

}

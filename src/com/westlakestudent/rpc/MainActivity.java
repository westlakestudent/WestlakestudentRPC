package com.westlakestudent.rpc;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.westlakestudent.rpc.service.KeyService;
import com.westlakestudent.rpc.service.aidlKeyService;
import com.westlakestudent.rpc.service.aidlKeyService.Stub;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private KeyService.ServiceBinder serviceBinder = null;
	private aidlKeyService.Stub mBinder = null;
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBinder = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
//			serviceBinder = (ServiceBinder) service;
			mBinder = (Stub) service;
			Log.d(TAG, "mBinder init");
		}
	};
	
	private void BindService(){
		Intent i = new Intent("westlakestudent.aidlkeyservice");
		bindService(i, serviceConnection, Service.BIND_AUTO_CREATE);
	}
	
	private void unBindService(){
		unbindService(serviceConnection);
	}
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BindService();
		findViewById(R.id.set).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					mBinder.setKey(10086);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		unBindService();
		super.onDestroy();
	}

	

}

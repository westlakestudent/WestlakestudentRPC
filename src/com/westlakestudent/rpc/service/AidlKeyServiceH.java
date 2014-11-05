package com.westlakestudent.rpc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 *
 * AidlKeyService
 * @author chendong
 * 2014年11月5日 下午5:13:17
 * @version 1.0.0
 *
 */
public class AidlKeyServiceH extends Service {

	private int keyH = 0;
	private boolean running = true;
	
	private static final String TAG = "AidlKeyServiceH";
	@Override
	public IBinder onBind(Intent binder) {
		return mBinder;
	}

	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		
		new Thread(){

			@Override
			public void run() {
				super.run();
				while(running){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.e(TAG, "sleep error:" + e.getMessage());
					}
					keyH ++;
					Log.i(TAG, "key : " + keyH);
				}
			}
			
		}.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
		Log.d(TAG, "onDestroy");
	}



	private aidlKeyService.Stub mBinder = new aidlKeyService.Stub() {
		
		@Override
		public void setKey(int key) throws RemoteException {
			keyH = key;
			Log.d(TAG, "keyH set to " + key);
		}
	};
}

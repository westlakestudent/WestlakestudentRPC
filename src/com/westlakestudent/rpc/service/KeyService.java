package com.westlakestudent.rpc.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 *
 * KeyService
 * @author chendong
 * 2014年11月5日 下午4:31:00
 * @version 1.0.0
 *
 */
public class KeyService extends Service{

	private int key;
	private boolean running = true;
	private ServiceBinder mBinder = new ServiceBinder();
	private static final String TAG = "KeyService";
	
	
	
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
					key ++;
					Log.i(TAG, "key : " + key);
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

	@Override
	public IBinder onBind(Intent binder) {
		return mBinder;
	}
	
	
	public class ServiceBinder extends Binder implements IkeyService{

		public ServiceBinder(){
			Log.d(TAG, "ServiceBinder create");
		}
		@Override
		public int getkey() {
			return key;
		}

		@Override
		public void setKey(int key) {
			KeyService.this.key = key;
			Log.i(TAG, "key set to : " + key );
		}
		
	}

}

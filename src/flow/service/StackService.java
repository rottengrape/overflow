package flow.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StackService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		
		
		return null;
		
		
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		
		Log.e("stackservice", "#onCreate");
		
		
		
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
		
		super.onStart(intent, startId);
		
		Log.e("stackservice", "#onStart");
		
		
		
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		super.onStartCommand(intent, flags, startId);
		
		Log.e("stackservice", "#onStartCommand");
		
		
		
		
		return 0;
		
		
		
		
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		Log.e("stackservice", "#onDestroy");
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

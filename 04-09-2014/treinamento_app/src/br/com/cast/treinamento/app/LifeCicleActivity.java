package br.com.cast.treinamento.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public abstract class LifeCicleActivity extends ActionBarActivity {

	private static final String TAG = "LIFECICLE";

	public abstract String getActivityName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, getActivityName() + " : onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, getActivityName() + " : onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, getActivityName() + " : onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, getActivityName() + " : onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, getActivityName() + " : onRestart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, getActivityName() + " : onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, getActivityName() + " : onDestroy");
	}

}

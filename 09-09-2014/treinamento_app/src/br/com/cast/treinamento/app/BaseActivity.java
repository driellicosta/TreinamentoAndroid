package br.com.cast.treinamento.app;

import android.view.View;

public abstract class BaseActivity extends LifeCicleActivity {
	
	@SuppressWarnings("unchecked")
	protected <T extends View> T recuperarControle(int id) {
		return (T) findViewById(id);
	}
}

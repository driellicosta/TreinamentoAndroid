package br.com.cast.treinamento.app;

import java.util.Map;

import br.com.cast.treinamento.app.domain.ExcecaoNegocio;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity {

	private EditText txtNomeFiltro, txtTelefoneFiltro;
	private Button btnPesquisar;
	Context contexto;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contexto = this;
		recuperarControles();
		configurarPesquisa();
	}

	private void configurarPesquisa() {
		getSupportActionBar().setSubtitle("Pesquisar Contatos");

		btnPesquisar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				try {

					Intent intent = new Intent(contexto, ListaContatosActivity.class);
					intent.putExtra("filtroNome", txtNomeFiltro.getText().toString());
					intent.putExtra("filtroTelefone", txtTelefoneFiltro.getText().toString());
					
					if (intent != null) {
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.i("MAIN", e.getMessage());
				}
			}
		});
	}

	private void recuperarControles() {
		txtNomeFiltro = recuperarControle(R.id.txtNomeFiltro);
		txtTelefoneFiltro = recuperarControle(R.id.txtTelefoneFiltro);
		btnPesquisar = recuperarControle(R.id.btnPesquisar);
	}

}

package br.com.cast.treinamento.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.cast.treinamento.service.ContatoService;

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
					String nome = txtNomeFiltro.getText().toString();
					String telefone = txtTelefoneFiltro.getText().toString();
					
					if (!TextUtils.isEmpty(nome) || !TextUtils.isEmpty(telefone)) {
						if (ContatoService.getINSTANCIA(MainActivity.this).consultarQuantidade(nome, telefone) > 0) {
							
							intent.putExtra("filtroNome", nome);
							intent.putExtra("filtroTelefone", telefone);							
						} else {
							Toast.makeText(MainActivity.this, R.string.nenhum_contato_encontrado, Toast.LENGTH_SHORT).show();
							return;
						}
					}
					startActivity(intent);

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

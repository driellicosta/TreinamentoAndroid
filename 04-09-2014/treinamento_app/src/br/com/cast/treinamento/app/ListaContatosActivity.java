package br.com.cast.treinamento.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import br.com.cast.treinamento.entidades.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ListaContatosActivity extends BaseActivity {

	private ContatoService service;
	private ListView listViewContatos;
	private Contato contatoSelecionado;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	public ListaContatosActivity() {
		service = ContatoService.getINSTANCIA(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos);
		listViewContatos = recuperarControle(R.id.listViewContatos);		
		super.registerForContextMenu(listViewContatos);

	}

	@Override
	protected void onResume() {
		carregarListView();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.action_novo:
			intent = carregarTelaContato();
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private Intent carregarTelaContato() {
		return new Intent(this, ContatoActivity.class);
	}

	private List<Contato> carregarContatos() {
		return service.listarTodos();
	}

	private ContatoAdapter criarAdapter() {
		return new ContatoAdapter(this, carregarContatos());
	}

	private void carregarListView() {
		listViewContatos.setAdapter(criarAdapter());
		listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos_context, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.action_editar:
			intent = carregarTelaContato();
			intent.putExtra(ContatoActivity.CONTATO_EDITAR, contatoSelecionado);
			break;
		case R.id.action_excluir:
			new AlertDialog.Builder(this).setMessage(getString(R.string.msg_confirmacao_exclusao, contatoSelecionado.getNome()))
					.setPositiveButton(R.string.msg_sim, new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							service.excluir(contatoSelecionado);
							carregarListView();
						}
					}).setNeutralButton(R.string.msg_nao, null).create().show(); // Neutral sempre fecha a popup
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
		return super.onContextItemSelected(item);
	}
}

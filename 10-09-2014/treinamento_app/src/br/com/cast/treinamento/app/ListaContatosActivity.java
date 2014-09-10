package br.com.cast.treinamento.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import br.com.cast.treinamento.app.widget.ContatoAdapterClickListener;
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
		getSupportActionBar().setSubtitle(R.string.listar_contatos);
		super.registerForContextMenu(listViewContatos);
	}

	@Override
	protected void onResume() {
		carregarListView();
		super.onResume();
	}

	private Intent carregarTelaContato() {
		return new Intent(this, ContatoActivity.class);
	}

	private List<Contato> carregarContatos() {
		String filtroNome = getIntent().getStringExtra("filtroNome");
		String filtroTelefone = getIntent().getStringExtra("filtroTelefone");
		// return service.listarTodos();
		return service.consultar(filtroNome, filtroTelefone);
	}

	private ContatoAdapter criarAdapter() {
		return new ContatoAdapter(this, carregarContatos());
	}

	private void carregarListView() {

		listViewContatos.setAdapter(criarAdapter());
		ContatoAdapterClickListener contatoListener = new ContatoAdapterClickListener(this);

		listViewContatos.setOnItemLongClickListener(contatoListener);
		listViewContatos.setOnItemClickListener(contatoListener);
	}

	public void recuperarContatoSelecionado(AdapterView<?> adapter, int posicao) {
		contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
	}

	/* OPÇÕES MENU */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_contatos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		// menu.findItem(R.id.action_editar).setVisible(contatoSelecionado != null);
		// menu.findItem(R.id.action_excluir).setVisible(contatoSelecionado != null);

		menu.setGroupVisible(R.id.group_selecao_obrigatoria, contatoSelecionado != null);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		verificarOpcaoMenu(item);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu_contatos, menu);
		menu.findItem(R.id.action_novo).setVisible(false);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		verificarOpcaoMenu(item);
		return super.onContextItemSelected(item);
	}

	private void verificarOpcaoMenu(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.action_novo:
			intent = carregarTelaContato();
			break;
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
		case R.id.action_ligar:
			intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel: " + contatoSelecionado.getTelefone()));
			super.startActivity(intent);
			break;

		case R.id.action_sms:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("sms: " + contatoSelecionado.getTelefone()));
			intent.putExtra("sms_body", "Olá, olá, olá");
			super.startActivity(intent);
			break;
		case R.id.action_site:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http:// " + contatoSelecionado.getTelefone()));			
			super.startActivity(intent);
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
	}
}

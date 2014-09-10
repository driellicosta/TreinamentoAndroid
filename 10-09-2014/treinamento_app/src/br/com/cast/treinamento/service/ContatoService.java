package br.com.cast.treinamento.service;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.domain.ExcecaoNegocio;
import br.com.cast.treinamento.dao.ContatoDAO;
import br.com.cast.treinamento.entidades.Contato;

public class ContatoService  {

	private static ContatoService INSTANCIA;	
	private Context contexto;

	public ContatoService(Context contexto) {
		super();
		this.contexto = contexto;
	}
	
	public static ContatoService getINSTANCIA(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoService(contexto);
		}

		return INSTANCIA;
	}

	public void salvar(Contato contato) throws ExcecaoNegocio {
		ExcecaoNegocio ex = new ExcecaoNegocio();

		if (TextUtils.isEmpty(contato.getNome())) {
			ex.getMapaErros().put(R.id.txtNome, R.string.erro_obrigatorio);
		}

		if (TextUtils.isEmpty(contato.getEndereco())) {
			ex.getMapaErros().put(R.id.txtEndereco, R.string.erro_obrigatorio);
		}

		if (TextUtils.isEmpty(contato.getSite())) {
			ex.getMapaErros().put(R.id.txtSite, R.string.erro_obrigatorio);
		}

		if (TextUtils.isEmpty(contato.getTelefone())) {
			ex.getMapaErros().put(R.id.txtTelefone, R.string.erro_obrigatorio);
		}
		
		if (!ex.getMapaErros().isEmpty()) {
			throw ex;
		}

		ContatoDAO.getINSTANCIA(contexto).salvar(contato);
	}
	
	public void excluir(Contato contato)
	{
		ContatoDAO.getINSTANCIA(contexto).excluir(contato);
	}
	
	public List<Contato> listarTodos() {
		return ContatoDAO.getINSTANCIA(contexto).listarTodos();
	}
	
	public List<Contato> consultar(String nome, String telefone) {
		return ContatoDAO.getINSTANCIA(contexto).consultar(nome, telefone);
	}
	
	public long consultarQuantidade(String nome, String telefone) {
		return ContatoDAO.getINSTANCIA(contexto).consultarQuantidade(nome, telefone);
	}
}

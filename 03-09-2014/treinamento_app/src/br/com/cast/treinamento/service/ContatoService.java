package br.com.cast.treinamento.service;

import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.domain.ExcecaoNegocio;
import br.com.cast.treinamento.dao.ContatoDAO;
import br.com.cast.treinamento.entidades.Contato;

public class ContatoService extends BaseService<Contato> {

	@SuppressWarnings("unused")
	private ContatoDAO dao;

	public ContatoService() {
		super(ContatoDAO.getINSTANCIA());
		dao = ContatoDAO.getINSTANCIA();
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

		super.salvar(contato);
	}
}

package br.com.cast.treinamento.service;

import java.util.List;

import br.com.cast.treinamento.app.domain.ExcecaoNegocio;
import br.com.cast.treinamento.dao.BaseMemoryDAO;
import br.com.cast.treinamento.entidades.IEntidade;

public class BaseService<T extends IEntidade> {

	private BaseMemoryDAO<T> dao;

	public BaseService(BaseMemoryDAO<T> dao) {
		this.dao = dao;
	}

	public List<T> listar() {
		return dao.listar();
	}

	public void salvar(T entidade) throws ExcecaoNegocio {
		if (entidade.getId() != null && entidade.getId() > 0) {
			dao.alterar(entidade);
		} else {
			dao.inserir(entidade);
		}
	}

	public void excluir(T entidade) {
		dao.excluir(entidade);
	}

	public T recuperar(Long id) {
		return dao.recuperar(id);
	}

}

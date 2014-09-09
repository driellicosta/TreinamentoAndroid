package br.com.cast.treinamento.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.treinamento.entidades.IEntidade;

public class BaseMemoryDAO<T extends IEntidade> {

	private Long sequence = 1L;

	private List<T> dados = new ArrayList<T>();

	public List<T> listar() {
		return dados;
	}

	public void inserir(T entidade) {
		entidade.setId(sequence++);
		dados.add(entidade);
	}

	public void alterar(T entidade) {
		int indice = dados.indexOf(entidade);
		dados.set(indice, entidade);
	}

	public void excluir(T entidade) {
		dados.remove(entidade);
	}

	public T recuperar(Long id) {
		for (T entidade : dados) {
			if (entidade.getId() == id) {
				return entidade;
			}
		}
		return null;
	}
}

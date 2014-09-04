package br.com.cast.treinamento.dao;

import br.com.cast.treinamento.entidades.Contato;

public class ContatoDAO extends BaseMemoryDAO<Contato> {

	private static ContatoDAO INSTANCIA;

	static {
		INSTANCIA = new ContatoDAO();
	}

	public static ContatoDAO getINSTANCIA() {
		return INSTANCIA;
	}

	private ContatoDAO() {

	}
}

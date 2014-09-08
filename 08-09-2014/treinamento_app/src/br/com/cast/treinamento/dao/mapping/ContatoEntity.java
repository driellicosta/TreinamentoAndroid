package br.com.cast.treinamento.dao.mapping;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.treinamento.entidades.Contato;
import android.database.Cursor;
import android.provider.BaseColumns;

public class ContatoEntity implements BaseColumns {
	// BaseColumns utilizada para reaproveitar as contantes id e count

	public static final String TABELA = "tb_contato";

	public static final String COLUNA_ID = "id";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_ENDERECO = "endereco";
	public static final String COLUNA_SITE = "site";
	public static final String COLUNA_TELEFONE = "telefone";
	public static final String COLUNA_AVALIACAO = "avaliacao";

	public static final String[] COLUNAS = { COLUNA_ID, COLUNA_NOME, COLUNA_ENDERECO, COLUNA_SITE, COLUNA_TELEFONE, COLUNA_AVALIACAO };

	private ContatoEntity() {
		super();
	}

	public static Contato bindContato(Cursor cr) {
		Contato contato = null;
		if (!cr.isBeforeFirst() || cr.moveToNext()) {
			contato = new Contato();
			contato.setId(cr.getLong(cr.getColumnIndex(COLUNA_ID)));
			contato.setNome(cr.getString(cr.getColumnIndex(COLUNA_NOME)));
			contato.setEndereco(cr.getString(cr.getColumnIndex(COLUNA_ENDERECO)));
			contato.setTelefone(cr.getString(cr.getColumnIndex(COLUNA_TELEFONE)));
			contato.setSite(cr.getString(cr.getColumnIndex(COLUNA_SITE)));
			int indexAvaliacao = cr.getColumnIndex(COLUNA_AVALIACAO);
			if (!cr.isNull(indexAvaliacao)) {
				contato.setAvaliacao(cr.getFloat(indexAvaliacao));
			}
		}

		return contato;
	}

	public static List<Contato> bindContatos(Cursor cr) {
		List<Contato> lstContato = new ArrayList<Contato>();

		while (cr.moveToNext()) {
			Contato contato = ContatoEntity.bindContato(cr);
			if (contato != null) {
				lstContato.add(contato);
			}
		}

		return lstContato;
	}
}

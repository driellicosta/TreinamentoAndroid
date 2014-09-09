package br.com.cast.treinamento.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import br.com.cast.treinamento.dao.mapping.ContatoEntity;
import br.com.cast.treinamento.entidades.Contato;

public class ContatoDAO extends BaseDAO {

	private static ContatoDAO INSTANCIA;
	String CONDICAO_WHERE = ContatoEntity.COLUNA_ID + " = ?";

	public static ContatoDAO getINSTANCIA(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoDAO(contexto);
		}

		return INSTANCIA;
	}

	private ContatoDAO(Context contexto) {
		super(contexto);
	}

	public List<Contato> listarTodos() throws SQLException {
		try {
			SQLiteDatabase db = super.getReadableDatabase();
			String colunaNome = String.format("UPPER(%s) %s", ContatoEntity.COLUNA_NOME, "ASC");
			Cursor cursor = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS, null, null, null, null, colunaNome);

			/*
			 * Outra forma de fazer String sqlTodos = String.format("Select * from %s ORDER BY %s", ContatoEntity.TABELA, ContatoEntity.COLUNA_NOME); cursor =
			 * db.rawQuery(sqlTodos, null);
			 */

			List<Contato> lstContato = ContatoEntity.bindContatos(cursor);

			return lstContato;
		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage());
			throw ex;
		} finally {
			super.close();
		}
	}

	public List<Contato> consultar(String nome, String telefone) throws SQLException {
		try {
			SQLiteDatabase db = super.getReadableDatabase();
			String colunaNome = String.format("UPPER(%s) %s", ContatoEntity.COLUNA_NOME, "ASC");

			String condicaoWhere = "";
			String[] argumentosWhere = new String[] {};

			if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(telefone)) {
				nome = "%" + nome + "%";
				telefone = "%" + telefone + "%";
				condicaoWhere = ContatoEntity.COLUNA_NOME + " like ? AND " + ContatoEntity.COLUNA_TELEFONE + " like ?";
				argumentosWhere = new String[] { nome, telefone };
			} else {
				if (!TextUtils.isEmpty(nome)) {
					nome = "%" + nome + "%";					
					condicaoWhere = ContatoEntity.COLUNA_NOME + " like ?";
					argumentosWhere = new String[] { nome };
				}

				if (!TextUtils.isEmpty(telefone)) {
					telefone = "%" + telefone + "%";
					condicaoWhere = ContatoEntity.COLUNA_TELEFONE + " like ?";
					argumentosWhere = new String[] { telefone };
				}
			}

			Cursor cursor = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS, condicaoWhere, argumentosWhere, null, null, colunaNome);

			/*
			 * Outra forma de fazer String sqlTodos = String.format("Select * from %s ORDER BY %s", ContatoEntity.TABELA, ContatoEntity.COLUNA_NOME); cursor =
			 * db.rawQuery(sqlTodos, null);
			 */

			List<Contato> lstContato = ContatoEntity.bindContatos(cursor);

			return lstContato;
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
			throw ex;
		} finally {
			super.close();
		}
	}

	public void salvar(Contato contato) throws SQLException {
		SQLiteDatabase db = super.getWritableDatabase();
		db.beginTransaction();

		try {
			ContentValues valores = new ContentValues();
			valores.put(ContatoEntity.COLUNA_NOME, contato.getNome());
			valores.put(ContatoEntity.COLUNA_ENDERECO, contato.getNome());
			valores.put(ContatoEntity.COLUNA_SITE, contato.getSite());
			valores.put(ContatoEntity.COLUNA_TELEFONE, contato.getTelefone());

			if (contato.getAvaliacao().floatValue() == 0) {
				valores.putNull(ContatoEntity.COLUNA_AVALIACAO);
			} else {
				valores.put(ContatoEntity.COLUNA_AVALIACAO, contato.getAvaliacao());
			}

			if (contato.getId() == null) {
				db.insert(ContatoEntity.TABELA, null, valores);

			} else {
				String[] argumentosWhere = new String[] { contato.getId().toString() };
				db.update(ContatoEntity.TABELA, valores, CONDICAO_WHERE, argumentosWhere);
			}

			db.setTransactionSuccessful();

		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage());
			throw ex;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

	public void excluir(Contato contato) throws SQLException {
		SQLiteDatabase db = super.getWritableDatabase();
		db.beginTransaction();

		try {
			String[] argumentosWhere = new String[] { contato.getId().toString() };
			db.delete(ContatoEntity.TABELA, CONDICAO_WHERE, argumentosWhere);

			/*
			 * Outra forma de fazer String sqlDelete = String.format("DELETE FROM %s WHERE %s = ?", ContatoEntity.TABELA, ContatoEntity.COLUNA_ID);
			 * SQLiteStatement comando = db.compileStatement(sqlDelete); comando.bindLong(1, contato.getId()); comando.execute();
			 */

			db.setTransactionSuccessful();

		} catch (SQLException ex) {
			Log.e(TAG, ex.getMessage());
			throw ex;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

}

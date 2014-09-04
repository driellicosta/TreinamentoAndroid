package br.com.cast.treinamento.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.cast.treinamento.entidades.Contato;

public class ContatoDAO extends BaseDAO {

	private static ContatoDAO INSTANCIA;

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
			String[] colunas = { "id", "nome", "endereco", "site", "telefone", "avaliacao" };
			Cursor cursor = db.query("tb_contato", colunas, null, null, null, null, null);

			List<Contato> lstContato = new ArrayList<Contato>();

			while (cursor.moveToNext()) {
				Contato contato = new Contato();
				contato.setId(cursor.getLong(0));
				int indiceColunaNome = cursor.getColumnIndex("nome");
				contato.setNome(cursor.getString(indiceColunaNome));
				contato.setEndereco(cursor.getString(2));
				contato.setSite(cursor.getString(3));
				contato.setTelefone(cursor.getString(4));
				contato.setAvaliacao(cursor.getFloat(5));
				lstContato.add(contato);
			}
			return lstContato;
		} catch (SQLException ex) {
			Log.e("DAO", ex.getMessage());
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
			valores.put("nome", contato.getNome());
			valores.put("endereco", contato.getNome());
			valores.put("site", contato.getSite());
			valores.put("telefone", contato.getTelefone());
			valores.put("avaliacao", contato.getAvaliacao());

			if (contato.getId() == null) {
				db.insert("tb_contato", null, valores);

			} else {
				db.update("tb_contato", valores, "id=?", new String[] { contato.getId().toString() });
			}

			db.setTransactionSuccessful();

		} catch (SQLException ex) {
			Log.e("DAO", ex.getMessage());
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

			db.delete("tb_contato", "id=?", new String[] { contato.getId().toString() });
			db.setTransactionSuccessful();

		} catch (SQLException ex) {
			Log.e("DAO", ex.getMessage());
			throw ex;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

}

package br.com.cast.treinamento.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public abstract class BaseDAO extends SQLiteOpenHelper {

	protected static final String TAG = "DAO";
	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSAO = 2; // 2 = sqlite com arquivo
	private Context contexto;

	public BaseDAO(Context contexto) {
		super(contexto, DB_NAME, null, DB_VERSAO);
		this.contexto = contexto;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			executarScriptAssets(db, "creates.sql");
		} catch (Exception ex) {
			String create = "CREATE TABLE tb_contato(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT NOT NULL, site TEXT NOT NULL, telefone TEXT NOT NULL, avaliacao REAL);";
			db.execSQL(create);
		}
	}

	private void executarScriptAssets(SQLiteDatabase db, String arquivoSQL) throws IOException, SQLException {
		db.beginTransaction();
		try {
			InputStream arquivoCreate = contexto.getAssets().open(arquivoSQL);
			BufferedReader br = new BufferedReader(new InputStreamReader(arquivoCreate));
			String linha;
			while ((linha = br.readLine()) != null) {
				SQLiteStatement comando = db.compileStatement(linha);
				comando.execute();
			}
			db.setTransactionSuccessful();
		} catch (IOException ex) {
			Log.i(TAG, "IO: " + ex.getMessage());
			throw ex;
		} catch (SQLException ex) {
			Log.i(TAG, "SQL: " + ex.getMessage());
			throw ex;
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			executarScriptAssets(db, "drops.sql");
		} catch (Exception ex) {
			String drop = "DROP TABLE IF EXISTS tb_contato;";
			db.execSQL(drop);
		}
		
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}

}

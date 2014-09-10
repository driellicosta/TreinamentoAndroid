package br.com.cast.treinamento.app;

import java.io.File;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import br.com.cast.treinamento.app.domain.ExcecaoNegocio;
import br.com.cast.treinamento.entidades.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ContatoActivity extends BaseActivity {

	private static final int REQUEST_CODE_CAMERA = 12345;

	public static final String CONTATO_EDITAR = "CONTATO_EDITAR";

	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private ImageView imgFoto;
	private RatingBar barAvaliacao;
	private Button btnSalvar;
	private ContatoService service;
	private Contato contato;
	private String caminhoFoto;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	public ContatoActivity() {
		service = ContatoService.getINSTANCIA(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		recuperarControles();
		configurarSalvar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_contatos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_novo) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void configurarSalvar() {
		contato = (Contato) getIntent().getSerializableExtra(CONTATO_EDITAR);

		int subtitulo = R.string.incluir_contato;
		if (contato == null) {
			contato = new Contato();
		} else {
			carregarDadosContatoTela();
			subtitulo = R.string.alterar_contato;
		}

		getSupportActionBar().setSubtitle(subtitulo);

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				recuperarContato();
				try {
					service.salvar(contato);
					ContatoActivity.this.finish();
				} catch (ExcecaoNegocio e) {
					for (Map.Entry<Integer, Integer> erro : e.getMapaErros().entrySet()) {
						EditText campoErro = recuperarControle(erro.getKey());
						campoErro.setError(getString(erro.getValue()));
					}
				}
			}
		});

		imgFoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				caminhoFoto = Environment.getExternalStorageDirectory().toString() + File.separator + System.currentTimeMillis() + ".png";
				File arquivoFoto = new File(caminhoFoto);

				Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));

				startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_CAMERA) {
			if (resultCode == RESULT_OK) { // usuário enviou a foto
				contato.setFoto(caminhoFoto);
				carregarFotoContato();
			} else {
				Toast.makeText(this, R.string.nenhuma_foto_foi_adicionada, Toast.LENGTH_SHORT).show();
			}

		}

	}

	private void carregarFotoContato() {
		Bitmap fotoContato = BitmapFactory.decodeFile(contato.getFoto());
		Bitmap fotopqna = Bitmap.createScaledBitmap(fotoContato, 300, 300, true);
		imgFoto.setImageBitmap(fotopqna);
	}

	private void recuperarControles() {
		txtNome = recuperarControle(R.id.txtNome);
		txtEndereco = recuperarControle(R.id.txtEndereco);
		txtSite = recuperarControle(R.id.txtSite);
		txtTelefone = recuperarControle(R.id.txtTelefone);
		barAvaliacao = recuperarControle(R.id.barAvaliacao);
		btnSalvar = recuperarControle(R.id.btnSalvar);
		imgFoto = recuperarControle(R.id.img_foto);
	}

	private void carregarDadosContatoTela() {
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());

		if (contato.getAvaliacao() != null) {
			barAvaliacao.setRating(contato.getAvaliacao());
		}

		if (contato.getFoto() != null) {
			carregarFotoContato();
		}
	}

	private void recuperarContato() {
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setAvaliacao(barAvaliacao.getRating());
	}
}

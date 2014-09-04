package br.com.cast.treinamento.app.widget;

import java.util.List;

import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.entidades.Contato;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class ContatoAdapter extends BaseAdapter {

	private List<Contato> lstContato;
	private Activity contexto;

	public ContatoAdapter(Activity contexto, List<Contato> lstContato) {
		super();
		this.lstContato = lstContato;
		this.contexto = contexto;
	}

	@Override
	public int getCount() {
		return lstContato.size();
	}

	@Override
	public Contato getItem(int posicao) {
		return lstContato.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		Contato item = getItem(posicao);
		return item.getId();
	}

	@Override
	public View getView(int posicao, View view, ViewGroup viewPai) {
		LayoutInflater layoutInflater = contexto.getLayoutInflater();
		View layoutItem = layoutInflater.inflate(R.layout.item_list_view_contato, null);

		TextView lblNome = (TextView) layoutItem.findViewById(R.id.lblNome);
		lblNome.setText(getItem(posicao).getNome());

		TextView lblTelefone = (TextView) layoutItem.findViewById(R.id.lblTelefone);
		lblTelefone.setText(getItem(posicao).getTelefone());

		if (posicao % 2 == 0) {
			layoutItem.setBackgroundColor(Color.GRAY);
		}

		return layoutItem;
	}
}

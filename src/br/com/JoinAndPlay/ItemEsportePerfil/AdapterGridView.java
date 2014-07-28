package br.com.JoinAndPlay.ItemEsportePerfil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class AdapterGridView extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<ItemEsporte> itens;
	
	
	public AdapterGridView(Context context, ArrayList<ItemEsporte> itens) {
		this.itens = itens;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view!=null) return view;
		//Pega o item de acordo com a posção.
		ItemEsporte item = itens.get(position);
		//infla o layout para podermos preencher os dados
		view = mInflater.inflate(R.layout.tab_layout_perfil_esporte, parent, false);
		item.drawerView(view);
		return view;
	}

}
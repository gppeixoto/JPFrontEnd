package br.com.JoinAndPlay.ListPlace;

import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterListViewPlace extends BaseAdapter
{
	private LayoutInflater mInflater;
	private ArrayList<ItemPlace> itens;
	
	public AdapterListViewPlace(Context context, ArrayList<ItemPlace> itens)
	{
		//Itens que preencheram o listview
		this.itens = itens;
		//responsavel por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);
		
	}
	
	public int getCount()
	{
		return itens.size();
	}

	public ItemPlace getItem(int position)
	{
		return itens.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}
	@Override

	public View getView(int position, View view, ViewGroup parent)
	{
		//Pega o item de acordo com a posção.
		ItemPlace item = itens.get(position);
		//infla o layout para podermos preencher os dados
		if(view!=null){
			return view;
		} else {
			view = mInflater.inflate(R.layout.place_fragment, null);
		}
		
		item.drawerView(view);
		
			//atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
		//ao item e definimos as informações.
		// ((TextView) view.findViewById(R.id.)).setText(item.getTexto());
		return view;
	}
}
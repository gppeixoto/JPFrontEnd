package br.com.JoinAndPlay.ListEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdapterListView extends BaseAdapter
{
	private LayoutInflater mInflater;
	private ArrayList<ItemEvent> itens;
	Bitmap[] amigos;
	public AdapterListView(Context context, ArrayList<ItemEvent> itens)
	{
		//Itens que preencheram o listview
		this.itens = itens;
		//responsavel por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);
		amigos=new Bitmap[7];
		for (int j = 0; j < amigos.length; j++) {

			InputStream ims = null;
			try {
				ims = context.getAssets().open("av"+(j+1)+".png");
			} catch (IOException e) {
				e.printStackTrace();
			}    
		   amigos[j]=BitmapFactory.decodeStream(ims);

		   
		}
	}
	public int getCount()
	{
		return itens.size();
	}


	public ItemEvent getItem(int position)
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
		if(view==null){
			
			view = mInflater.inflate(R.layout.item_list, parent,false);
		}
		//Pega o item de acordo com a posção.
		ItemEvent item = itens.get(position);
		//infla o layout para podermos preencher os dados
		
		item.drawerView(view,amigos);

Log.v("uhu3",item+ " "+item.titulo);
		;		//atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
		//ao item e definimos as informações.
		// ((TextView) view.findViewById(R.id.)).setText(item.getTexto());
		//  ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());
		return view;
	}



}
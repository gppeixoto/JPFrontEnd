package br.com.JoinAndPlay.ListEvent;

import java.util.ArrayList;


import br.com.JoinAndPlay.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterListView extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<ItemEvent> itens;
 
    public AdapterListView(Context context, ArrayList<ItemEvent> itens)
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
        //Pega o item de acordo com a posção.
        ItemEvent item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_list, null);
 
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
       // ((TextView) view.findViewById(R.id.)).setText(item.getTexto());
      //  ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());
        return view;
    }
	

	
}
package br.com.JoinAndPlay.ListFriend;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterListViewFriend extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<ItemFriend> itens;
	
	
	public AdapterListViewFriend(Context context, ArrayList<ItemFriend> itens) {
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
		if (view == null){
			view = mInflater.inflate(R.layout.show_friend, parent, false);
		}
		ItemFriend item = itens.get(position);
		item.drawerView(view);
		return view;
	}

}
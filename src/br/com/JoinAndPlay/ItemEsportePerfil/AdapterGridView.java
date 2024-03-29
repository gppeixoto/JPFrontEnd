package br.com.JoinAndPlay.ItemEsportePerfil;
import java.util.ArrayList;
import br.com.JoinAndPlay.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		if (view == null){
			view = mInflater.inflate(R.layout.item_perfil_esporte, parent, false);
		}
		ItemEsporte item = itens.get(position);
		item.drawerView(view);
		return view;
	}

}
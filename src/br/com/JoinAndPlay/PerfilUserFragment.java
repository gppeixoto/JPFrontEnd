package br.com.JoinAndPlay;

import java.util.ArrayList;

import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.ItemEsportePerfil.MyGridView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class PerfilUserFragment extends Fragment {
	static final int NUM_SPORTS = 6; //debugging purposes, change once linked with the BD
	RelativeLayout ret;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) return null;
		RelativeLayout ret = (RelativeLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);
		GridView gridView = ((GridView)ret.findViewById(R.id.myGridView1));
		ArrayList<ItemEsporte> lista = new ArrayList<ItemEsporte>();
		for (int i=0; i < NUM_SPORTS; ++i){
			lista.add(new ItemEsporte());
		}
		gridView.setAdapter(new AdapterGridView(getActivity(),lista));
		return ret;
	}

}

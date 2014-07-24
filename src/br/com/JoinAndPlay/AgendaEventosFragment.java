package br.com.JoinAndPlay;

import java.util.ArrayList;


import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import br.com.JoinAndPlay.ListEvent.MyListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaEventosFragment extends Fragment implements OnItemClickListener{


	static ArrayList<ItemEvent> lista = new ArrayList<ItemEvent>();
	static AdapterListView adapter ;
	MyListView listV;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);


	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		adapter	= new AdapterListView(getActivity(),lista);
		listV = new MyListView(getActivity());
		listV.setAdapter(adapter);
		listV.setOnItemClickListener(this);
		return  listV;

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		arg2--;
		//ItemEvent item = lista.get(arg2);
		//lista.remove(arg2);

		//adapter.notifyDataSetChanged();

	}
}
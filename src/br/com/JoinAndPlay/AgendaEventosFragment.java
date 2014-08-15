package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.MyListView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaEventosFragment extends Fragment implements OnItemClickListener,Connecter<Vector<Evento>>{


	LayoutInflater inflater=null;	
	private MyListView listV;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater=inflater;
		listV = new MyListView(getActivity());
		listV.setOnItemClickListener(this);			
		Server.user_agenda(getActivity(), this);
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
	@Override
	public void onTerminado(Vector<Evento> in) {
		// TODO Auto-generated method stub
		ArrayList<Evento> lista = new ArrayList<Evento>();
		final AdapterListView adapter =new AdapterListView(inflater, lista);
listV.post(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		listV.setAdapter(adapter);
	}
});

		for (Iterator<Evento> iterator = in.iterator(); iterator.hasNext();) {
			Evento evento = (Evento) iterator.next();
lista.add(evento);
			
		}
	}
}
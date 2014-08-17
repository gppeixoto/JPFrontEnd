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
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaEventosFragment extends ListEventosFragment{


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.inflater=inflater;
		ViewGroup tela=(ViewGroup)inflater.inflate(R.layout.fragment_list_event,container,false) ;

		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		listV.setAdapter(null);

		Button Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setVisibility(View.INVISIBLE);
		tela.removeView(Button_criar);
		Server.user_agenda(getActivity(), this);
		return  tela;

	}

	@Override
	public void onTerminado(Vector<Evento> vector) {
		// TODO Auto-generated method stub
		if(vector!=null && vector.size()<=0)return;
		super.onTerminado(vector);
	}
}
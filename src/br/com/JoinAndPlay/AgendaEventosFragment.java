package br.com.JoinAndPlay;


import java.util.Iterator;
import java.util.Vector;

import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AgendaEventosFragment extends ListEventosFragment implements Runnable{

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
		if(getArguments()!=null && getArguments().containsKey("anteriores")){
			ID=4;
			Server.get_past(MainActivity.self, this);
		}else{
			Server.user_agenda(MainActivity.self, this);
			ID=3;
		}
		((MainActivity) MainActivity.self).loadTela(ID);

		return  tela;
	}

	@Override
	public void onTerminado(Vector<Evento> vector) {
		// TODO Auto-generated method stub
		if(vector!=null && vector.size()<=0){;
		((MainActivity) MainActivity.self).popLoadTela(ID);


		}else
			super.onTerminado(vector);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connecter<Vector<Evento>> evento = new Connecter<Vector<Evento>>() {

			@Override
			public void onTerminado(Vector<Evento> in) {
				// TODO Auto-generated method stub
				if(in!=null){
					for (Iterator<Evento> iterator = in.iterator(); iterator.hasNext();) {
						Evento evento = (Evento) iterator.next();
						if(!mapaID.containsKey(evento.getId())){
							lista.add(evento);
							mapaID.put(evento.getId(), evento.getId());


						}
					



					
					
					}

				}
			}
		};
		if(ID==4){
			Server.get_past(MainActivity.self, evento);
		}else if(ID==3){
			Server.user_agenda(MainActivity.self, evento);
		}else{
			return;	
		}



	}


}
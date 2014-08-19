package br.com.JoinAndPlay;


import java.util.Vector;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class AgendaEventosFragment extends ListEventosFragment{
	static int ID=3;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).loadTela(ID);

		super.inflater=inflater;

		ViewGroup tela=(ViewGroup)inflater.inflate(R.layout.fragment_list_event,container,false) ;

		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		listV.setAdapter(null);

		Button Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setVisibility(View.INVISIBLE);
		tela.removeView(Button_criar);
		if(getArguments()!=null && getArguments().containsKey("anteriores")){
		
		Server.user_agenda(getActivity(), this);
		}else{
			
			Server.get_past(getActivity(), this);
		}
		
		return  tela;

	}

	@Override
	public void onTerminado(Vector<Evento> vector) {
		// TODO Auto-generated method stub
		if(vector!=null && vector.size()<=0)return;
		super.onTerminado(vector);
	}
}
package br.com.JoinAndPlay.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.PerfilUserFragment;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AmigosFragment extends Fragment implements OnItemClickListener ,Connecter<Vector<Usuario>>{
	LayoutInflater inflater;
	ListView listV;
	int ID;
	private ArrayList<Usuario> vetor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

		this.inflater=inflater;
		ViewGroup tela=(ViewGroup)inflater.inflate(R.layout.fragment_list_event,container,false) ;

		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		vetor=null;
		if(getArguments()!=null){
			vetor= getArguments().getParcelableArrayList("users");
			if(vetor==null){
				MainActivity.self.loadTela(ID);
				Server.get_friends(MainActivity.self,this );
			}
			ID=getArguments().getInt("idTab");
		}

		if(vetor!=null)
			listV.setAdapter(new AdapterAmigo(vetor, inflater,null));

		Button Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setVisibility(View.INVISIBLE);
		tela.removeView(Button_criar);

		return  tela;


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
		PerfilUserFragment fm = new  PerfilUserFragment();
		Bundle arg = new Bundle();
		arg.putString("idUser",vetor.get(arg2-1).getId());
		arg.putInt("idTab", ID);
		if(vetor.get(arg2-1).getTags()!=null && vetor.get(arg2-1).getTags().size()>0){
			String[] badges = new String[vetor.get(arg2-1).getTags().size()];
			for (int i = 0; i < badges.length; i++) {
				//badges=vetor.get(arg2-1).;
			}
		//	arg.putStringArray("badges",(String[]) );
		}
		fm.setArguments(arg);
		((MainActivity)MainActivity.self).mudarAbaAtual(fm);
	}

	@Override
	public void onTerminado(Vector<Usuario> in) {
		// TODO Auto-generated method stub
		MainActivity.self.popLoadTela(ID);


		if(in==null){
			//bolafora
			return;
		}
		vetor=new ArrayList<Usuario>();
		for (Iterator<Usuario> iterator = in.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();			
			vetor.add(usuario);
			
		}
		listV.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				listV.setAdapter(new AdapterAmigo(vetor, inflater,null));

			}
		});


	}

}

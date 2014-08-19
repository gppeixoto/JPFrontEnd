package br.com.JoinAndPlay.Event;

import java.util.ArrayList;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.PerfilUserFragment;
import br.com.JoinAndPlay.R;
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

public class AmigosFragment extends Fragment implements OnItemClickListener {
	LayoutInflater inflater;
	ListView listV;
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
		fm.setArguments(arg);
		((MainActivity)getActivity()).mudarAbaAtual(fm);
	}

}

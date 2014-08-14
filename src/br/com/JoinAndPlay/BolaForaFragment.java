package br.com.JoinAndPlay;

import java.util.Vector;

import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Endereco;
import br.com.JoinAndPlay.Server.Server;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

class Botao extends BaseAdapter {

	private Endereco[] list;
	OnClickListener eu;
	Context cont;

	public Botao(Endereco[] arg1, Context c){
		this.list = arg1;
		this.cont = c;
		//this.eu=eu;
	}
	
	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Button bt = new Button(cont);
		String txt_nome = list[arg0].getName();
		String txt_end = list[arg0].getAddress();
		bt.setBackgroundResource(R.drawable.fora_button);
		bt.setText(txt_nome + "\n" + txt_end);
		return bt;
	}
}

public class BolaForaFragment extends Fragment implements OnItemClickListener {

	private TextView texto;
	private ListView lv;
	private String address;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v=inflater.inflate(R.layout.bola_fora, container,false);

		texto = (TextView) v.findViewById(R.id.bolaForaTxt);

		if(getArguments()!=null){
			Bundle args= getArguments();
			
			Log.v("vedbhvbshbvhjsh ", args.getParcelableArray("enderecos").length+"");
			
			if (args.getParcelableArray("enderecos").length == 0){
				texto.setText("Nenhum evento foi encontrado pela sua pesquisa.");
			} else {
				lv = (ListView) v.findViewById(R.id.bolaForaListView);
				lv.setOnItemClickListener(this);
				lv.setDividerHeight(22);
				Botao bt = new Botao( (Endereco[]) args.getParcelableArray("enderecos"),getActivity());
				address = ((Endereco[]) args.getParcelableArray("enderecos"))[0].getName();
				lv.setAdapter(bt);
			}
		}

		return v;
	}
	/*@Override
	public void onClick(View arg0) {
		Bundle argsR= getArguments();
		
		ListEventosFragment frag = new ListEventosFragment();
		Bundle args_ = new Bundle();
		
		args_.putString("data", argsR.getString("data"));
		args_.putString("horaInicio", argsR.getString("horaInicio"));
		args_.putString("horaTermino", argsR.getString("horaTermino"));
		args_.putStringArray("esportes", argsR.getStringArray("esporte"));
		
		//args_.putString("endereco");
		
	}*/
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle argsR= getArguments();
		
		Log.v("aBAbaBABba","TOU AQUIII");
		ListEventosFragment frag = new ListEventosFragment();
		Bundle args_ = new Bundle();
		
		args_.putString("data", argsR.getString("data"));
		args_.putString("horaInicio", argsR.getString("horaInicio"));
		args_.putString("horaTermino", argsR.getString("horaTermino"));
		args_.putStringArray("esportes", argsR.getStringArray("esporte"));
		
		Endereco addr = ((Endereco[]) argsR.getParcelableArray("enderecos"))[arg2];
		String s = addr.getName()+addr.getAddress();
		Log.v("aaaaaaaaa", s);
		args_.putString("endereco",s);
		((MainActivity)getActivity()).replaceTab(frag);
	}
}

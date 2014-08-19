package br.com.JoinAndPlay;

import br.com.JoinAndPlay.Server.Endereco;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

class Botao extends BaseAdapter implements OnClickListener{

	private Endereco[] list;
	Context cont;
	Bundle bede;
	String addr;

	public Botao(Endereco[] arg1, Context c, Bundle bd){
		this.list = arg1;
		this.cont = c;
		this.bede = bd;
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
		String txt_nome = list[arg0].getName();
		String txt_end = list[arg0].getAddress();
		this.addr = txt_end;
		Button bt = new Button(cont);
		bt.setFocusable(false);
		bt.setOnClickListener(this);
		bt.setBackgroundResource(R.drawable.fora_button);
		bt.setText(txt_nome + "\n" + txt_end);
		return bt;
	}

	@Override
	public void onClick(View arg0) {
		ListEventosFragment frag = new ListEventosFragment();
		Bundle args_ = new Bundle();
		
		args_.putString("data", bede.getString("data"));
		args_.putString("horaInicio", bede.getString("horaInicio"));
		args_.putString("horaTermino", bede.getString("horaTermino"));
		args_.putStringArray("esportes", bede.getStringArray("esporte"));
		args_.putString("endereco",addr);
		args_.putBoolean("getA", false);
		
		frag.setArguments(args_);
		((MainActivity) cont).replaceTab(frag);
	}
}

public class BolaForaFragment extends Fragment implements OnItemClickListener {

	private TextView texto;
	private ListView lv;
	private String address;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).popLoadTela(0);

		View v=inflater.inflate(R.layout.bola_fora, container,false);
		
		Bundle args= getArguments();
		
		texto = (TextView) v.findViewById(R.id.bolaForaTxt);
		lv = (ListView) v.findViewById(R.id.bolaForaListView);
		lv.setOnItemClickListener(this);

		if(getArguments()!=null){
			if(args.getBoolean("internet") == false ){
				texto.setText("Verifique sua conexão com a Internet.");

				
			}else if(args.getBoolean("conflito") == false || args.getParcelableArray("enderecos").length == 0) {
				texto.setText("Nenhum evento foi encontrado pela sua pesquisa.");
			} else{
			
				lv.setDividerHeight(22);
				Botao bt = new Botao( (Endereco[]) args.getParcelableArray("enderecos"),getActivity(),args);
				address = ((Endereco[]) args.getParcelableArray("enderecos"))[0].getName();
				lv.setAdapter(bt);
			}
		}

		return v;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle argsR= getArguments();
		ListEventosFragment frag = new ListEventosFragment();
		Bundle args_ = new Bundle();
		
		args_.putString("data", argsR.getString("data"));
		args_.putString("horaInicio", argsR.getString("horaInicio"));
		args_.putString("horaTermino", argsR.getString("horaTermino"));
		args_.putStringArray("esportes", argsR.getStringArray("esporte"));
		
		Endereco addr = ((Endereco[]) argsR.getParcelableArray("enderecos"))[arg2];
		String s = addr.getName()+addr.getAddress();
		args_.putString("endereco",s);
		((MainActivity)getActivity()).replaceTab(frag);
	}
}

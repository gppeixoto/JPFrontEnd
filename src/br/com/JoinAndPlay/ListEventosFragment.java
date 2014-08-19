package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Vector;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Endereco;

public class ListEventosFragment extends Fragment implements OnClickListener,OnItemClickListener,Connecter<Vector<Evento>>{
	static ArrayList<Evento> lista;
	protected int ID=0;
	ListView listV;
	protected Button Button_criar;
	LayoutInflater inflater=null;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("classe", "listEventos");
		if (container == null) {
			return null;
		}
		View tela=inflater.inflate(R.layout.fragment_list_event,container,false) ;
		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		listV.setAdapter(null);

		Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setText("Criar Evento");
		Button_criar.setOnClickListener(this);
		Button_criar.setTextColor(0xffffffff);
		this.inflater=inflater;
		final Bundle args= getArguments();
		final ListEventosFragment self=this;
		String[] esportes_temp=null;
		boolean getA =false;
		if(getArguments()!=null){

			esportes_temp=args.getInt("esportes_qtd")>0?args.getStringArray("esportes"):null;
			getA= args.getBoolean("getA");
		}
		if (getA && args.getString("endereco")!=null && args.getString("endereco").length()>0){
			((MainActivity) getActivity()).loadTela(ID);
			final String[] esportes=esportes_temp;

			Server.getAddresses(args.getString("endereco"), null, null, null, new  Connecter<Vector<Endereco>>() {
				@Override
				public void onTerminado(Vector<Endereco> in) {
					if(in == null){
						Log.v("in", ""+in);

						Bundle args2 = new Bundle();
						((MainActivity) getActivity()).popLoadTela(ID);

						args2.putBoolean("internet",false);
						BolaForaFragment bfm = new BolaForaFragment();
						bfm.setArguments(args2);
						((MainActivity) self.getActivity()).replaceTab(bfm);

					}else if (in.size() == 0){
						((MainActivity) getActivity()).popLoadTela(ID);
						Log.v("in", ""+in.size());

						Bundle args2 = new Bundle();
						args2.putBoolean("internet",true);
						args2.putParcelableArray("enderecos", new Endereco[0]);
						args2.putString("data", args.getString("data"));
						args2.putString("horaInicio", args.getString("horaInicio"));
						args2.putString("horaTermino", args.getString("horaTermino"));
						args2.putStringArray("esportes", args.getStringArray("esportes"));
						args2.putBoolean("conflito",false);
						BolaForaFragment bfm = new BolaForaFragment();
						bfm.setArguments(args2);
						((MainActivity) self.getActivity()).replaceTab(bfm);
					} else if(in.size() == 1){
						Log.v("in", ""+in.size());
						Server.get_matched_events(getActivity(),args.getString("endereco"),args.getString("data") ,args.getString("horaInicio"),args.getString("horaTermino"), esportes, self);	
					} else {
						Log.v("in", ""+in.size());

						Bundle args2 = new Bundle();
						Endereco arr[] = new Endereco[in.size()];
						in.toArray(arr);
						args2.putBoolean("internet",true);
						args2.putParcelableArray("enderecos", arr);
						args2.putString("data", args.getString("data"));
						args2.putString("horaInicio", args.getString("horaInicio"));
						args2.putString("horaTermino", args.getString("horaTermino"));
						args2.putStringArray("esportes", args.getStringArray("esportes"));
						args2.putBoolean("conflito", true);
						BolaForaFragment bfm = new BolaForaFragment();
						bfm.setArguments(args2);
						((MainActivity) getActivity()).popLoadTela(ID);

						((MainActivity) self.getActivity()).replaceTab(bfm);
					}
				}
			});
		}

		//Log.v("parametros", "esportes: " + esportes[0] + " endereco: " + args.getString("endereco") + " data: " + args.getString("data")
		//		+ " hora de inicio: " + args.getString("horaInicio") + " hora de termino: " + args.getString("horaTermino"));
		else {
			Log.v("in", "notIN");

			Location local =((MainActivity)(getActivity())).location;
			((MainActivity) getActivity()).loadTela(ID);

			if(local!=null){
				Server.get_future_events(getActivity(),local.getLatitude()+","+local.getLongitude(),this);	

			}else{
				Server.get_future_events(getActivity(),this);	

			}
			Log.v("in", "fimNotIn");

		}
		return tela;
	}

	@Override
	public void onClick(View v) {
		CriarEventosFragment criar = new CriarEventosFragment();
		((MainActivity)getActivity()).mudarAbaAtual(criar);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public    void  onSaveInstanceState( Bundle outState){




	}
	@Override
	public void onTerminado(Vector<Evento> vector) {
		if(vector==null){

			Bundle args2 = new Bundle();
			args2.putBoolean("internet",false);
			BolaForaFragment bfm = new BolaForaFragment();
			bfm.setArguments(args2);
			((MainActivity) getActivity()).popLoadTela(ID);

			((MainActivity) getActivity()).replaceTab(bfm);
		}else if(vector.size()<=0){

			Bundle args2 = new Bundle();
			args2.putBoolean("conflito",false);
			BolaForaFragment bfm = new BolaForaFragment();
			bfm.setArguments(args2);
			((MainActivity) getActivity()).popLoadTela(ID);

			((MainActivity) getActivity()).replaceTab(bfm);
		}else{
			lista=new ArrayList<Evento>();
			for (int i = 0; i <vector.size(); i++) {
				lista.add(vector.get(i));
			}


			listV.post(new Runnable() {

				@Override
				public void run() {
					listV.setAdapter(new AdapterListView(inflater, lista));
					((MainActivity) getActivity()).popLoadTela(ID);
				}
			});

		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(lista!=null && lista.size()>(arg2-1) ){

			Bundle arg= new Bundle();
			arg.putString("evento",lista.get(arg2-1).getId() );
			arg.putInt("idTab", ID);
			Fragment fragment = new EventFragment();
			fragment.setArguments(arg);
			((MainActivity)getActivity()).mudarAbaAtual(fragment);
		}

	}





}


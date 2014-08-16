package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import org.joda.time.chrono.BuddhistChronology;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Esporte;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.Server.Endereco;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;


public class ListEventosFragment extends Fragment implements OnClickListener, OnTouchListener,OnItemClickListener,Connecter<Vector<Evento>>,LocationListener{
	static ArrayList<Evento> lista;
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
		Button_criar.setOnTouchListener(this);
		this.inflater=inflater;
		if(getArguments()!=null){
			final Bundle args= getArguments();
			final ListEventosFragment self=this;

			final String[] esportes=args.getInt("esportes_qtd")>0?args.getStringArray("esportes"):null;
			boolean getA = args.getBoolean("getA");
			Log.v("getiA", getA+"");
			if (getA && args.getString("endereco")!=null && args.getString("endereco").length()>0){
				Server.getAddresses(args.getString("endereco"), null, null, null, new  Connecter<Vector<Endereco>>() {
					@Override
					public void onTerminado(Vector<Endereco> in) {
						Log.v("ncjbfdebvhjbhj", in+"");
						if(in == null){
							Bundle args2 = new Bundle();

							args2.putBoolean("internet",false);
							BolaForaFragment bfm = new BolaForaFragment();
							bfm.setArguments(args2);
							((MainActivity) self.getActivity()).replaceTab(bfm);

						}else if (in.size() == 0){
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
							Server.get_matched_events(getActivity(),args.getString("endereco"),args.getString("data") ,args.getString("horaInicio"),args.getString("horaTermino"), esportes, self);	
						} else {
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
							((MainActivity) self.getActivity()).replaceTab(bfm);
						}
					}
				});
			} else {
				Log.v("aaaf", "pegou");
				Server.get_matched_events(getActivity(),args.getString("endereco"),args.getString("data") ,args.getString("horaInicio"),args.getString("horaTermino"), esportes, self);	
			}
			//Log.v("parametros", "esportes: " + esportes[0] + " endereco: " + args.getString("endereco") + " data: " + args.getString("data")
			//		+ " hora de inicio: " + args.getString("horaInicio") + " hora de termino: " + args.getString("horaTermino"));
		}else{
			LocationManager lManager = (LocationManager)getActivity().getSystemService(getActivity().LOCATION_SERVICE);
			Location location =lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Log.v("local", ""+location);
			if(location!=null){
				Server.get_future_events(getActivity(),location.getLatitude()+","+location.getLongitude(),this);	


			}else{
				Server.get_future_events(getActivity(),this);	

			}

		}
		return tela;
	}
	boolean login = true;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		login=false;

		CriarEventosFragment criar = new CriarEventosFragment();
		((MainActivity)getActivity()).mudarAbaAtual(criar);

	}


	@Override
	public void onResume(){
		super.onResume();


	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public    void  onSaveInstanceState( Bundle outState){




	}
	@Override
	public boolean onTouch(View arg0,MotionEvent  arg1) {
		// TODO Auto-generated method stub
		switch (arg1.getAction()&255) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			Button_criar.setTextColor(getResources().getColor(R.color.red));


			break;

		default:
			Button_criar.setTextColor(getResources().getColor(R.color.white));

			break;
		}


		return false;
	}

	@Override
	public void onTerminado(Vector<Evento> vector) {
		// TODO Auto-generated method stub
		Log.v("uhu", "oi"+vector);

		if(vector==null){

			Bundle args2 = new Bundle();
			args2.putBoolean("internet",false);
			BolaForaFragment bfm = new BolaForaFragment();
			bfm.setArguments(args2);
			((MainActivity) getActivity()).replaceTab(bfm);
		}else if(vector.size()<=0){

			Bundle args2 = new Bundle();
			args2.putBoolean("conflito",false);
			BolaForaFragment bfm = new BolaForaFragment();
			bfm.setArguments(args2);
			((MainActivity) getActivity()).replaceTab(bfm);
		}else{
			lista=new ArrayList<Evento>();
			for (int i = 0; i <vector.size(); i++) {
				lista.add(vector.get(i));
			}


			listV.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					listV.setAdapter(new AdapterListView(inflater, lista));

				}
			});

		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		if(lista!=null && lista.size()>(arg2-1) ){

			Bundle arg= new Bundle();
			arg.putString("evento",lista.get(arg2-1).getId() );
			Fragment fragment = new EventFragment();
			fragment.setArguments(arg);
			((MainActivity)getActivity()).mudarAbaAtual(fragment);
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.v("local", ""+location);
		if(location!=null){
			Server.get_future_events(getActivity(),location.getLatitude()+","+location.getLongitude(),this);	


		}else{
			Server.get_future_events(getActivity(),this);	

		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}



}


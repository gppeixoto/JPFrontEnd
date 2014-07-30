package br.com.JoinAndPlay.Event;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.JoinAndPlay.ListEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import br.com.JoinAndPlay.Server.Evento;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.joda.time.DateTime;

public class EventFragment extends Fragment implements OnClickListener{
	private ItemEvent myEvent;
	SupportMapFragment suportMap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapsInitializer.initialize(getActivity());

		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.event_fragment, container, false);;
		Button b = (Button)v.findViewById(R.id.como_chegar);
		b.setOnClickListener(this);
		if(getArguments()!=null){

			myEvent= (ItemEvent)getArguments().getParcelable("evento");
			myEvent= myEvent==null?new ItemEvent():myEvent;
					if(myEvent!=null)
			setValuesEvent(v, myEvent);
		}
		suportMap= new SupportMapFragment();
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_frag, suportMap).commit();



		return  v;
	}
	@Override
	public void onActivityCreated( Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getView().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GoogleMap map = suportMap.getMap();

				LatLng target= new LatLng( -7.9900227  , -34.83929520000004);
				CameraPosition cameraPosition = new CameraPosition.Builder().target(target).zoom(12).build();
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				MarkerOptions marker = new MarkerOptions();
				marker.position(target).title("Iateclub");
				map.addMarker(marker);

			}
		});
	}
	
	public void setValuesEvent(View view,ItemEvent eventItem){
		Evento evento = eventItem.evento;
		TextView descricao_horario = (TextView)view.findViewById(R.id.descricao_horario);
		TextView descricao_local = (TextView)view.findViewById(R.id.descricao_local);
		
		TextView qtd_confirmados = (TextView)view.findViewById(R.id.qtd_confirmados);
		TextView qtd_no_local = (TextView)view.findViewById(R.id.qtd_no_local);
		
		ImageView pessoa1 = (ImageView)view.findViewById(R.id.imagem_perfil1);
		ImageView pessoa2 = (ImageView)view.findViewById(R.id.imagem_perfil2);
		ImageView pessoa3 = (ImageView)view.findViewById(R.id.imagem_perfil3);
		ImageView pessoa4 = (ImageView)view.findViewById(R.id.imagem_perfil4);
		ImageView pessoa5 = (ImageView)view.findViewById(R.id.imagem_perfil5);
		ImageView pessoa6 = (ImageView)view.findViewById(R.id.imagem_perfil6);	
		TextView qtd_amigos_amais = (TextView)view.findViewById(R.id.qtd_amigos_amais);

		ImageView imagem_da_partida = (ImageView)view.findViewById(R.id.imagem_da_partida);
		TextView tipo_da_partida = (TextView)view.findViewById(R.id.tipo_da_partida);	
		
		TextView descricao_do_esporte = (TextView)view.findViewById(R.id.descricao_do_esporte);
		
	//	descricao_horario.setText(evento.getDate() + " as " + evento.getStartTime() + " horas");
//		descricao_local.setText(evento.getLocalizationName()+", "+evento.getLocalizationAddress());
		
	//	qtd_confirmados.setText(evento.);
	//	qtd_no_local(evento.);
		
	//	pessoa1.
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		((MainActivity)getActivity()).mudarAbaAtual(new ListEventosFragment());
	}

}

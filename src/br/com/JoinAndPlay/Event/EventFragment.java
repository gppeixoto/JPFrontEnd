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
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Evento;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
	
	public String parseMonth(int n){
		if(n == 1) return "Janeiro";
		else if (n == 2) return "Fevereiro";
		else if (n == 3) return "Março";
		else if (n == 4) return "Abril";
		else if (n == 5) return "Maio";
		else if (n == 6) return "Junho";
		else if (n == 7) return "Julho";
		else if (n == 8) return "Agosto";
		else if (n == 9) return "Setembro";
		else if (n == 10) return "Outubro";
		else if (n == 11) return "Novembro";
		else if (n == 12) return "Dezembro";
		else return "fail";
	}
	
	public void setValuesEvent(View view,ItemEvent eventItem){
		Evento evento = eventItem.evento;
		if(evento == null) return;
		TextView descricao_horario = (TextView)view.findViewById(R.id.descricao_horario);
		TextView descricao_local = (TextView)view.findViewById(R.id.descricao_local);
		
		TextView qtd_confirmados = (TextView)view.findViewById(R.id.qtd_confirmados);
		TextView qtd_no_local = (TextView)view.findViewById(R.id.qtd_no_local);
		
		LinearLayout pessoas = (LinearLayout)view.findViewById(R.id.imagem_perfil_dad);
		
		TextView qtd_amigos_amais = (TextView)view.findViewById(R.id.qtd_amigos_amais);

		ImageView imagem_da_partida = (ImageView)view.findViewById(R.id.imagem_da_partida);
		TextView tipo_da_partida = (TextView)view.findViewById(R.id.tipo_da_partida);	
		
		TextView descricao_do_esporte = (TextView)view.findViewById(R.id.descricao_do_esporte);
		
		String data = evento.getDate();
		String dia = data.substring(0, 2);
		data = parseMonth(((int)(data.charAt(3)-'0'))*10 + ((int)(data.charAt(4)-'0')));
		descricao_horario.setText(dia + " de " + data + " as " + evento.getStartTime() + " horas");
		descricao_local.setText(evento.getLocalizationName()+", "+evento.getLocalizationAddress());
		
	//	qtd_confirmados.setText(evento.); FALTA NO SERVIDOR
	//	qtd_no_local(evento.);
		
	//	pessoa1.
		for (int i = 0; i < Math.min(eventItem.amigos.length,ItemEvent.MAX_AMIGOS_QTD); i++) {
			if(pessoas.getChildCount()-1>i){
				ImageView imagem = (ImageView) pessoas.getChildAt(i);

				new DownloadImagemAsyncTask(view.getContext(),imagem).execute(eventItem.amigos[i]);
			}else break;
		}		

		for (int i = eventItem.amigos.length; i <ItemEvent.MAX_AMIGOS_QTD; i++) {
			if(pessoas.getChildCount()-1>i){
				ImageView imagem = (ImageView) pessoas.getChildAt(i);

				imagem.setVisibility(View.INVISIBLE);
			}else break;
		}
		
		qtd_amigos_amais.setText("+ " + (eventItem.amigos.length > 6 ? eventItem.amigos.length - 6 : 0) + " amigo" + (eventItem.amigos.length > 7 ? "s" : ""));
		tipo_da_partida.setText(evento.getSport());
		descricao_do_esporte.setText(evento.getDescription());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		((MainActivity)getActivity()).mudarAbaAtual(new ListEventosFragment());
	}

}

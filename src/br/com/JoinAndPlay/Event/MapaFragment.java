package br.com.JoinAndPlay.Event;

import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MapaFragment extends Fragment {
	SupportMapFragment suportMap;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MapsInitializer.initialize(MainActivity.self);

		if (container == null) {
			return null;
		}

		MapsInitializer.initialize(MainActivity.self);

		View view = inflater.inflate(R.layout.mapa_fragment, container, false);
		suportMap= new SupportMapFragment();
		TextView nome_evento = (TextView)view.findViewById(R.id.nome_evento);
		Button maximizar = (Button) view.findViewById(R.id.maximizar);


		if(getArguments()!=null){
			final Bundle arg= getArguments();
			final MainActivity act = (MainActivity)MainActivity.self;
			nome_evento.setText(arg.getString("nome"));
			if(arg.getBoolean("isMax")){
				maximizar.setBackgroundResource(R.drawable.menos);
				maximizar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						act.retirarAbaAtual();

					}
				});

			}else{
				maximizar.setBackgroundResource(R.drawable.mais);
				maximizar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						MapaFragment mapa= new MapaFragment();
						arg.putBoolean("isMax", true);
						mapa.setArguments(arg);
						((MainActivity)MainActivity.self).mudarAbaAtual(mapa);
					}
				});
			}
			final double[] latlng= arg.getDoubleArray("latlng");
			final String titulo=arg.getString("local");
			view.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					GoogleMap map = suportMap.getMap();
					if(map!=null && latlng!=null){
						LatLng target= new LatLng(latlng[0], latlng[1]);
						CameraPosition cameraPosition = new CameraPosition.Builder().target(target).zoom(12).build();
						map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						MarkerOptions marker = new MarkerOptions();
						marker.position(target).title(titulo);
						map.addMarker(marker).showInfoWindow();

					}
				}
			});

		}

		view.post(new Runnable() {
			@Override
			public void run() {
				if(suportMap.getMap()!=null)
					suportMap.getMap().getUiSettings().setZoomControlsEnabled(false);
			}
		});
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_frag_suport,suportMap).commit();

		return view;
	}

}

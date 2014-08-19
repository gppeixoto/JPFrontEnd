package br.com.JoinAndPlay;

import java.util.ArrayList;
import br.com.JoinAndPlay.ListPlace.AdapterListViewPlace;
import br.com.JoinAndPlay.ListPlace.ItemPlace;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChoosePlaceFragment extends Fragment implements OnItemClickListener, OnClickListener {
	
	SupportMapFragment supportMap;
	static ArrayList<ItemPlace> lista = new ArrayList<ItemPlace>();
	protected AdapterListViewPlace adapter ;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
lista.add(new ItemPlace());
		adapter = new AdapterListViewPlace(getActivity(),lista);

	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		MapsInitializer.initialize(getActivity());
		supportMap= new SupportMapFragment();
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_fragPlaces, supportMap).commit();
		
		View view = inflater.inflate(R.layout.choose_place, container,false);
		
		ListView listV=(ListView) view.findViewById(R.id.views_place);
		listV.setOnItemClickListener(this);
		listV.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	boolean login = true;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
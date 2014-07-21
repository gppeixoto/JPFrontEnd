package br.com.JoinAndPlay;


import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class AgendaEventosFragment extends ListEventosFragment{


	static ArrayList<ItemEvent> lista = new ArrayList<ItemEvent>();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		adapter = new AdapterListView(getActivity(),lista);

		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v=super.onCreateView(inflater, container, savedInstanceState);
		Button_criar.setText("notificações");
		
		return v;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	 ((MainActivity) getActivity()).mudarAba(2,new EventFragment());
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
arg2--;
		ItemEvent item = lista.get(arg2);
		lista.remove(arg2);
		AgendaEventosFragmentAntigos.lista.add(	item);

		adapter.notifyDataSetChanged();

	}
}
class AgendaEventosFragmentAntigos extends AgendaEventosFragment {
	static ArrayList<ItemEvent> lista = new ArrayList<ItemEvent>();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		adapter = new AdapterListView(getActivity(),lista);


	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup v=(ViewGroup)super.onCreateView(inflater, container, savedInstanceState);
		v.removeView(Button_criar);
		return v;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		getFragmentManager().popBackStack();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
}
package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.Noticacao.NotificacaoAdapter;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Notificacao;
import br.com.JoinAndPlay.Server.Server;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NotificacaoFragment extends Fragment implements Connecter<Map<String,Vector<Notificacao>>>,OnItemClickListener {

	LayoutInflater inflater;
	ListView list;
	ArrayList<Notificacao> notifi ;
	static final int ID=2;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).loadTela(ID);

		if (container == null) {
			return null;
		}
		Configuration config = getActivity().getResources().getConfiguration();
		LinearLayout v = (LinearLayout)inflater.inflate(R.layout.notificacao_fragment, container, false);

		if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){

			v.setOrientation(LinearLayout.HORIZONTAL);
		}
		final NotificacaoFragment self = this;
		this.inflater=inflater;
		maker((ListView)v.findViewById(R.id.lista_view_convites_1),inflater);
		ConfigJP.getUserID(getActivity(), new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				// TODO Auto-generated method stub
				Log.v("invite",""+ConfigJP.UserId);
				Server.get_invites(ConfigJP.UserId,self);
			}
		});


		return  v;
	}

	private void maker(ListView list,LayoutInflater inflater){
		this.list=list;
		list.setDivider(getResources().getDrawable(R.drawable.linha));
		list.setDividerHeight(1);
		list.setVisibility(View.VISIBLE);
	}

	@Override
	public void onTerminado(Map<String, Vector<Notificacao>> in) {
		if(in==null)return;
		if(in.size()==0)return;
		notifi = new ArrayList<Notificacao>();
		for (Iterator<Entry<String, Vector<Notificacao>>> iterator = in.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Vector<Notificacao>> type = (Entry<String, Vector<Notificacao>>) iterator.next();
			for (Iterator<Notificacao> iterator2 = type.getValue().iterator(); iterator2
					.hasNext();) {
				Notificacao not = (Notificacao) iterator2.next();
				not.esporte=type.getKey();
				notifi.add(not);
			}

		}
		list.setOnItemClickListener(this);
		list.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				list.setAdapter(new NotificacaoAdapter(inflater,notifi));
				((MainActivity) getActivity()).popLoadTela(ID);

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
		// TODO Auto-generated method stub
		if(notifi!=null && notifi.size()>(i) ){

			Bundle arg= new Bundle();
			arg.putString("evento",notifi.get(i).getEventId() );
			Fragment fragment = new EventFragment();
			fragment.setArguments(arg);
			((MainActivity)getActivity()).mudarAbaAtual(fragment);
		}	
	}

}

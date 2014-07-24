package br.com.JoinAndPlay;

import br.com.JoinAndPlay.Noticacao.NotificacaoAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NotificacaoFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	
		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.notificacao_fragment, container, false);;
		ListView list = (ListView)v.findViewById(R.id.lista_view_convites);
		list.setAdapter(new NotificacaoAdapter(inflater));
		return  v;
	}

}

package br.com.JoinAndPlay;

import br.com.JoinAndPlay.Noticacao.NotificacaoAdapter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NotificacaoFragment extends Fragment  {
	LinearLayout.LayoutParams parans_max= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
	LinearLayout.LayoutParams parans_min= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		if (container == null) {
			return null;
		}
		Configuration config = getActivity().getResources().getConfiguration();
		LinearLayout v = (LinearLayout)inflater.inflate(R.layout.notificacao_fragment, container, false);

	if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
		
		v.setOrientation(LinearLayout.HORIZONTAL);
	}
	maker((ListView)v.findViewById(R.id.lista_view_convites_1),inflater);
	maker((ListView)v.findViewById(R.id.lista_view_convites_2),inflater);

		return  v;
	}

	private void maker(ListView list,LayoutInflater inflater){
		
		list.setAdapter(new NotificacaoAdapter(inflater));
		list.setDivider(getResources().getDrawable(R.drawable.linha));
		list.setDividerHeight(20);
		list.setVisibility(View.VISIBLE);
	//	View v2=new View(getActivity());
		//v2.setMinimumHeight(15);
	//	list.addHeaderView(v2);
	}


	

}

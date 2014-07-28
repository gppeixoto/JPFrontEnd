package br.com.JoinAndPlay;

import br.com.JoinAndPlay.Noticacao.NotificacaoAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NotificacaoFragment extends Fragment implements OnClickListener {
	LinearLayout.LayoutParams parans_max= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
	LinearLayout.LayoutParams parans_min= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		if (container == null) {
			return null;
		}
		
			View v = inflater.inflate(R.layout.notificacao_fragment, container, false);
			ExpandableListView list = (ExpandableListView)v.findViewById(R.id.lista_view_convites);
			list.setAdapter(new NotificacaoAdapter(inflater));

			list.setDivider(getResources().getDrawable(R.drawable.linha));
			list.setDividerHeight(20);
			View v2=new View(getActivity());
			v2.setMinimumHeight(15);
			list.addHeaderView(v2);
		
		return  v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		View aux=v.findViewById(R.id.lista_view_convites);
		switch (aux.getVisibility()) {
		case View.VISIBLE:
			aux.setVisibility(View.INVISIBLE);
		     v.setLayoutParams(parans_min);
			v.getParent().requestLayout();
			((View) v.getParent()).invalidate();
			break;
		case View.INVISIBLE:
			aux.setVisibility(View.VISIBLE);
		     v.setLayoutParams(parans_max);
			v.getParent().requestLayout();
			((View) v.getParent()).invalidate();
			
			break;
		default:
			break;
		}
		
	}

	

}

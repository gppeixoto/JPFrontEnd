package br.com.JoinAndPlay;

import br.com.tabActive.TabFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class CriarEventosCompFragment extends Fragment implements OnItemClickListener, OnTabChangeListener {
	
	private Button bCriarEvento;
	private Button bParticular;
	private Button bPublico;
	
	private TabHost tabhost;
	
	private EditText eNomeEvento;
	
	private boolean privado;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		
		privado = true;
		
		View view = inflater.inflate(R.layout.criar_evento_comp, container,false);
		
		tabhost = (TabHost) view.findViewById(R.id.tabhost);
		tabhost.setup();
		tabhost.setOnTabChangedListener(this);
		
		eNomeEvento = (EditText) view.findViewById(R.id.escolha_nome_evento);
		
		bCriarEvento = (Button) view.findViewById(R.id.criar_evento_button);
		bCriarEvento.setText("Criar Evento");
		
		bParticular = (Button) view.findViewById(R.id.particular);
		bParticular.setText("Particular");
		bParticular.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bParticular.setBackgroundResource(R.drawable.red_button);
				bParticular.setPadding(10, 10, 10, 10);
				bPublico.setBackgroundResource(R.drawable.gray_button);
				bPublico.setPadding(10, 10, 10, 10);
				privado = true;	
			}
		});
		
		bPublico = (Button) view.findViewById(R.id.publico);
		bPublico.setText("Público");
		bPublico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bPublico.setBackgroundResource(R.drawable.red_button);
				bPublico.setPadding(10, 10, 10, 10);
				bParticular.setBackgroundResource(R.drawable.gray_button);
				bParticular.setPadding(10, 10, 10, 10);
				privado = false;	
			}
		});
		
		bCriarEvento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// verificar se dados estao completos
				// criar evento com esses dados
			}
		});
	
		for (int i = 0; i < 3; i++) {
			TabSpec tabSpec = tabhost.newTabSpec("Tab"+i).setIndicator("").setContent(new TabFactory(getActivity()));
			tabhost.addTab(tabSpec);
			//tamanho.setMinimumWidth(Math.max(fragments.size()*60,tamanho.getWidth()));
			//tabhost.getTabWidget().getChildAt(tabhost.getTabWidget().getChildCount()-1).setBackgroundResource(i);//(getResources().getDrawable(R.drawable.seletc_tab));;
		}
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
		
	}
}

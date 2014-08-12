package br.com.JoinAndPlay;

import java.util.Vector;

import com.facebook.Session;

import br.com.tabActive.TabFactory;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog.Builder;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class CriarEventosCompFragment extends Fragment implements OnItemClickListener, OnTabChangeListener {
	
	private Button bCriarEvento;
	private Button bParticular;
	private Button bPublico;
	
	private TabHost tabhost;
	private Vector<Usuario> amigos;
	
	private EditText eNomeEvento;
	
	private boolean privado;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		
		Server.get_friends(Session.getActiveSession().getAccessToken(), new Connecter<Vector<Usuario>>(){

			@Override
			public void onTerminado(Vector<Usuario> in) {
				// TODO Auto-generated method stub
				amigos = (Vector<Usuario>) in;
			}
		});	
		
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
				
				String nomeDoEvento = (String) eNomeEvento.getText().toString();
				
				/**
				if(nomeDoEvento == null || nomeDoEvento.trim().equals("")){
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Ops");
					error.setMessage("Escolha um nome para o evento!");
					error.setPositiveButton("OK", null);
					error.show();
					return;
				}*/
				
				if(getArguments()!=null){
					Bundle args = getArguments();
					
					String esporte = (String) args.get("esporte");
					String dia = (String) args.get("data");
					String termino = (String) args.get("horaTermino");
					String inicio = (String) args.get("horaInicio");
					Double preco = (Double) args.get("preco");
					String end = (String) args.get("endereco");
					String localNome = (String) args.get("nomeLocal");
					String bairro = (String) args.get("bairro");
					String cidade = (String) args.get("cidade");
					
					Server.create_event(Session.getActiveSession().getAccessToken(), localNome, end, 
							cidade, bairro, esporte, dia, inicio, termino, 
							"", nomeDoEvento, preco, privado, new Connecter<Evento>(){

								@Override
								public void onTerminado(Evento in) {
									// TODO Auto-generated method stub
									Evento e = (Evento) in;
									Log.v("retorno evento", ""+e);
																
								}
					});
					ListEventosFragment list = new ListEventosFragment();
					((MainActivity)getActivity()).mudarAbaAtual(list);
					
				} else {
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Ops");
					error.setMessage("Erro na criação do evento!");
					error.setPositiveButton("OK", null);
					error.show();
					return;
				}				
			}
		});
		/**
		TabHost.TabSpec spec;
		
		Intent intent = new Intent().setClass(this.getActivity(), tabConvite.class);
        spec = tabhost.newTabSpec("First").setIndicator("")
                      .setContent(intent);
        
        intent = new Intent().setClass(this.getActivity(), tabConvite.class);
        spec = tabhost.newTabSpec("Second").setIndicator("")
                      .setContent(intent);
        
        intent = new Intent().setClass(this.getActivity(), tabConvite.class);
        spec = tabhost.newTabSpec("Third").setIndicator("")
                      .setContent(intent);
        
        tabhost.getTabWidget().setCurrentTab(0);
        */
		
		for (int i = 0; i < 3; i++) {
			TabSpec tabSpec =tabhost.newTabSpec("Tab"+i).setIndicator("").setContent(new TabFactory(getActivity()));
			tabhost.addTab(tabSpec);
			
			
		}
		tabhost.setCurrentTab(0);
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
		
		/**
		for(in i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            if(i==0)
                tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_convite);
            else if(i==1)
                tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_convite);
            else if(i==2)
                tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_convite);
        }
		
		if(tabhost.getCurrentTab()==0)
	        tabhost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_convite);
	    else if(tabhost.getCurrentTab()==1)
	        tabhost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_convite);
	    else if(tabhost.getCurrentTab()==2)
	        tabhost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_convite);	
	        */
	}
}
package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Vector;

import com.facebook.Session;

import br.com.tabActive.TabFactory;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
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
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListFriend.AdapterGridViewFriend;
import br.com.JoinAndPlay.ListFriend.ItemFriend;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class CriarEventosCompFragment extends Fragment implements OnItemClickListener, OnTabChangeListener {
	
	private Button bCriarEvento;
	private Button bParticular;
	private Button bPublico;
	
	private ExpandableHeightGridView grid;
	
	private Vector<Usuario> aux;
	private ArrayList<ItemFriend> amigos;
	private Vector<String> convidados;
		
	private EditText eNomeEvento;
	
	private boolean privado;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		
		amigos = new ArrayList<ItemFriend>();
		convidados = new Vector<String>();
		
		Server.get_friends(Session.getActiveSession().getAccessToken(), new Connecter<Vector<Usuario>>(){

			@Override
			public void onTerminado(Vector<Usuario> in) {
				// TODO Auto-generated method stub
				aux = (Vector<Usuario>) in;
				if(aux!=null){
					for(int i = 0; i < aux.size(); i++){
						amigos.add(new ItemFriend(aux.elementAt(i)));
					}
					
					
					grid.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							grid.setAdapter(new AdapterGridViewFriend(getActivity(), amigos));

						}
					});
				}	
			}
		});	
		
		privado = true;
		
		View view = inflater.inflate(R.layout.criar_evento_comp, container,false);
		
		//tabhost = (TabHost) view.findViewById(R.id.tabhost);
		//tabhost.setup();
		//tabhost.setOnTabChangedListener(this);
		
		eNomeEvento = (EditText) view.findViewById(R.id.escolha_nome_evento);
		
		grid = (ExpandableHeightGridView) view.findViewById(R.id.gridView1);
		grid.setExpanded(true);
		grid.setOnItemClickListener(this);
		
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
		bPublico.setText("P�blico");
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
				
				String nomeDoEvento = (String) eNomeEvento.getText().toString();
				
				if(nomeDoEvento == null || nomeDoEvento.trim().equals("")){
					AlertDialog.Builder builder1 = new AlertDialog.Builder(bCriarEvento.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setCancelable(true);
					builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
					}});
					
					builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_nome, null));
					AlertDialog alert11 = builder1.create();
					
					OnShowListener onshow = new OnShowListener() {
						@Override
						@SuppressWarnings( "deprecation" )
						public void onShow(DialogInterface dialog) {
							Button positiveButton = ((AlertDialog) dialog)
			                        .getButton(AlertDialog.BUTTON_POSITIVE);
							
			                positiveButton.setBackgroundDrawable(getResources()
			                        .getDrawable(R.drawable.alert_button));
			                
			                positiveButton.setText("OK");
			                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
							
						}
					};
					alert11.setOnShowListener(onshow);
					alert11.show();
					return;
				}
				
				if(getArguments()!=null){
					Bundle args = getArguments();
					
					String esporte = (String) args.getString("esporte");
					String dia = (String) args.getString("data");
					String termino = (String) args.getString("horaTermino");
					String inicio = (String) args.getString("horaInicio");
					Double preco = (Double) args.getDouble("preco");
					String end = (String) args.getString("rua");
					String localNome = (String) args.getString("nomeLocal");
					String bairro = (String) args.getString("bairro");
					String cidade = (String) args.getString("cidade");
					
					Server.create_event(getActivity(), localNome, end, 
							cidade, bairro, esporte, dia, inicio, termino, 
							"", nomeDoEvento, preco, privado, new Connecter<Evento>(){

								@Override
								public void onTerminado(Evento in) {
									// TODO Auto-generated method stub
									Evento e = (Evento) in;
									Log.v("retorno evento", ""+e);
									
									if(!convidados.isEmpty()){
										Server.invite(Session.getActiveSession().getAccessToken(),
												e.getId(), convidados, new Connecter<Boolean>(){

													@Override
													public void onTerminado(
															Boolean in) {
														// TODO Auto-generated method stub
														boolean a = (boolean) in;
														if(!a){
															AlertDialog.Builder builder1 = new AlertDialog.Builder(bCriarEvento.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
															builder1.setCancelable(true);
															builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int id) {
																	dialog.cancel();
															}});
															
															builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_convite, null));
															AlertDialog alert11 = builder1.create();
															
															OnShowListener onshow = new OnShowListener() {
																@Override
																@SuppressWarnings( "deprecation" )
																public void onShow(DialogInterface dialog) {
																	Button positiveButton = ((AlertDialog) dialog)
													                        .getButton(AlertDialog.BUTTON_POSITIVE);
																	
													                positiveButton.setBackgroundDrawable(getResources()
													                        .getDrawable(R.drawable.alert_button));
													                
													                positiveButton.setText("OK");
													                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
																	
																}
															};
															alert11.setOnShowListener(onshow);
															alert11.show();
														}
													}
										});
									}		
														
								}
					});
					
					ListEventosFragment list = new ListEventosFragment();
					((MainActivity)getActivity()).mudarAbaAtual(list);
					
				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(bCriarEvento.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setCancelable(true);
					builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
					}});
					
					builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_erro, null));
					AlertDialog alert11 = builder1.create();
					
					OnShowListener onshow = new OnShowListener() {
						@Override
						@SuppressWarnings( "deprecation" )
						public void onShow(DialogInterface dialog) {
							Button positiveButton = ((AlertDialog) dialog)
			                        .getButton(AlertDialog.BUTTON_POSITIVE);
							
			                positiveButton.setBackgroundDrawable(getResources()
			                        .getDrawable(R.drawable.alert_button));
			                
			                positiveButton.setText("OK");
			                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
							
						}
					};
					alert11.setOnShowListener(onshow);
					alert11.show();
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
        
		
		for (int i = 0; i < 3; i++) {
			TabSpec tabSpec =tabhost.newTabSpec("Tab"+i).setIndicator("").setContent(new TabFactory(getActivity()));
			tabhost.addTab(tabSpec);
			
			
		}
		tabhost.setCurrentTab(0);*/
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ItemFriend f = amigos.get(position);
		if(f.getSelected()){
			amigos.get(position).deselected();
			convidados.remove(f.id);
		} else {
			amigos.get(position).selected();
			if(!convidados.contains(f.id)){
				convidados.add(f.id);
			}
		}
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
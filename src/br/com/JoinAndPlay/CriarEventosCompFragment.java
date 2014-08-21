package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Vector;
import com.facebook.Session;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import br.com.JoinAndPlay.Event.AdapterAmigo;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.widget.EditText;
import android.widget.TabHost.OnTabChangeListener;

public class CriarEventosCompFragment extends Fragment implements OnItemClickListener, OnTabChangeListener {

	private Button bCriarEvento;
	private Button bParticular;
	private Button bPublico;
	private AdapterAmigo adapter;

	private ListView grid;

	private Vector<Usuario> aux;
	private ArrayList<Usuario> amigos;
	private Vector<String> convidados;

	private EditText eNomeEvento;
	private EditText eDescricao;

	private boolean privado;

	private boolean selector[] = null;

	private int ID=0;

	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){

		if(container==null) return null;
		final Bundle arguments = getArguments()!=null ? new Bundle():null;

		if(getArguments() != null){
			ID = arguments.getInt("idTab");
			MainActivity.self.loadTela(ID);
			arguments.putAll(getArguments());
		}
		amigos = new ArrayList<Usuario>();
		convidados = new Vector<String>();


		privado = true;

		View view = inflater.inflate(R.layout.criar_evento_comp, container,false);

		//tabhost = (TabHost) view.findViewById(R.id.tabhost);
		//tabhost.setup();
		//tabhost.setOnTabChangedListener(this);

		eNomeEvento = (EditText) view.findViewById(R.id.escolha_nome_evento);
		eDescricao = (EditText) view.findViewById(R.id.descrever_evento);

		grid = (ListView) view.findViewById(R.id.gridView1);
		//grid.setExpanded(true);
		grid.setOnItemClickListener(this);
		grid.addHeaderView(new View(MainActivity.self));
	
grid.setOnScrollListener(new OnScrollListener() {
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(OnScrollListener.SCROLL_STATE_IDLE == (scrollState&1)){
        	grid.getParent().requestDisallowInterceptTouchEvent(false);
			
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
	
		
	}
})		;
		
		
		
		grid.setOnTouchListener(new ListView.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                grid.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	            	grid.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
	        }

		
	    });
		

		
		Server.get_friends(MainActivity.self, new Connecter<Vector<Usuario>>(){

			@Override
			public void onTerminado(final Vector<Usuario> in) {
				// TODO Auto-generated method stub
				aux = (Vector<Usuario>) in;
				if(aux!=null){
					for(int i = 0; i < aux.size(); i++){
						amigos.add((aux.elementAt(i)));
					}

					grid.post(new Runnable() {
						@Override
						public void run() {
							if(amigos!=null && amigos.size()>0){
								selector = new boolean[amigos.size()];
								adapter = new AdapterAmigo(amigos, inflater,selector);
								grid.setAdapter(adapter);

							}
							MainActivity.self.popLoadTela(ID);
						}
					});
				}else
					MainActivity.self.popLoadTela(ID);
			}
		});


		bCriarEvento = (Button) view.findViewById(R.id.criar_evento_button);
		bCriarEvento.setText("Criar Evento");

		final Drawable red = getResources().getDrawable(R.drawable.red_button);
		final Drawable gray = getResources().getDrawable(R.drawable.gray_button);

		bParticular = (Button) view.findViewById(R.id.particular);
		bParticular.setPadding(10, 10, 10, 10);
		bParticular.setText("Particular");
		bParticular.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bParticular.setBackgroundDrawable(red);
				bParticular.setPadding(10, 10, 10, 10);
				bPublico.setBackgroundDrawable(gray);
				bPublico.setPadding(10, 10, 10, 10);
				privado = true;	
			}
		});
		bPublico = (Button) view.findViewById(R.id.publico);
		bPublico.setText("Público");
		bPublico.setPadding(10, 10, 10, 10);
		bPublico.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bParticular.setBackgroundDrawable(gray);
				bParticular.setPadding(10, 10, 10, 10);
				bPublico.setBackgroundDrawable(red);
				bPublico.setPadding(10, 10, 10, 10);
				privado = false;	
			}
		});

		bCriarEvento.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String descricaoEvento = eDescricao.getText().toString();

				String nomeDoEvento = (String) eNomeEvento.getText().toString();

				if(nomeDoEvento == null || nomeDoEvento.trim().equals("")){
					AlertDialog.Builder builder1 = new AlertDialog.Builder(bCriarEvento.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setCancelable(true);
					builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}});

					builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert_create_nome, null));
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
							positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

						}
					};
					alert11.setOnShowListener(onshow);
					alert11.show();
					return;
				}

				if(arguments!=null){
					Bundle args = arguments;

					String esporte = (String) args.getString("esporte");
					String dia = (String) args.getString("data");
					String termino = (String) args.getString("horaTermino");
					String inicio = (String) args.getString("horaInicio");
					Double preco = (Double) args.getDouble("preco");
					String end = (String) args.getString("rua");
					String localNome = (String) args.getString("nomeLocal");
					String bairro = (String) args.getString("bairro");
					String cidade = (String) args.getString("cidade");

					Server.create_event(MainActivity.self, localNome, end, 
							cidade, bairro, esporte, dia, inicio, termino, 
							descricaoEvento, nomeDoEvento, preco, privado, new Connecter<Evento>(){

						@Override
						public void onTerminado(Evento in) {
							// TODO Auto-generated method stub

							Evento e = (Evento) in;									
							if(e!= null && !convidados.isEmpty()){
								Server.invite(Session.getActiveSession().getAccessToken(),
										e.getId(), convidados, new Connecter<Boolean>(){

									@Override
									public void onTerminado(
											Boolean in) {
										// TODO Auto-generated method stub
										boolean a = (boolean) in;

									}
								});
							}		

						}
					});

					((MainActivity)MainActivity.self).retirarAbaAtual();
					((MainActivity)MainActivity.self).retirarAbaAtual();

				} else {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(bCriarEvento.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
					builder1.setCancelable(true);
					builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}});

					builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert_create_erro, null));
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
							positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

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

		Intent intent = new Intent().setClass(this.MainActivity.self, tabConvite.class);
        spec = tabhost.newTabSpec("First").setIndicator("")
                      .setContent(intent);

        intent = new Intent().setClass(this.MainActivity.self, tabConvite.class);
        spec = tabhost.newTabSpec("Second").setIndicator("")
                      .setContent(intent);

        intent = new Intent().setClass(this.MainActivity.self, tabConvite.class);
        spec = tabhost.newTabSpec("Third").setIndicator("")
                      .setContent(intent);

        tabhost.getTabWidget().setCurrentTab(0);


		for (int i = 0; i < 3; i++) {
			TabSpec tabSpec =tabhost.newTabSpec("Tab"+i).setIndicator("").setContent(new TabFactory(MainActivity.self));
			tabhost.addTab(tabSpec);


		}
		tabhost.setCurrentTab(0);*/
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int i,
			long id) {
		if(i-1<0)return;
		selector[i-1]=!selector[i-1];

		if(adapter!=null){
			adapter.draw(view, i-1);
		}
	}

	public void onBackPressed(){

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
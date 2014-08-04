package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;


public class ListEventosFragment extends Fragment implements OnClickListener, OnTouchListener,OnItemClickListener {
	static ArrayList<ItemEvent> lista = new ArrayList<ItemEvent>();
	protected AdapterListView adapter ;
	protected Button Button_criar;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
lista.add(new ItemEvent());
		adapter = new AdapterListView(getActivity(),lista);


	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		View tela=inflater.inflate(R.layout.fragment_list_event,container,false) ;
		ListView listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);

		Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setText("Criar Evento");
		Button_criar.setOnClickListener(this);
		Button_criar.setTextColor(0xffffffff);
		Button_criar.setOnTouchListener(this);
		listV.setAdapter(adapter);
	


		return tela;
	}
	boolean login = true;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		List<String> PERMISSIONS = new ArrayList<String>();
		PERMISSIONS.add("user_friends");
		PERMISSIONS.add("public_profile");

		PERMISSIONS.add("email");

		if(login){
			Session session = Session.openActiveSession(getActivity(), true,PERMISSIONS, new Session.StatusCallback() {

				// callback when session changes state
				@Override
				public void call(Session session, SessionState state, Exception exception) {

					if (session.isOpened()) {
						// make request to the /me API
						Request.newMeRequest(session, new GraphUserCallback() {

							// callback after Graph API response with user object

							@Override
							public void onCompleted(GraphUser user, Response response) {
								// TODO Auto-generated method stub
								Log.v("uuou","dasasd"+		user);

							}
						}
								).executeAsync();
					}
				}
			});

			Log.v("token","dasasd"+		session.getPermissions());
			Log.v("token","dasasd"+session.isOpened());

			Log.v("token"," "+ session.getAccessToken());
			Log.v("token","dasasd");

			if (session != null && session.isOpened()) {
				Toast.makeText(getActivity(), session.getAccessToken(), Toast.LENGTH_LONG).show();

			}

			login=false;
		}else{
			//MyThread t = new MyThread();
			//t.start();
			Server.user_profile(Session.getActiveSession().getAccessToken(),new Connecter<Usuario>() {
				
				@Override
				public void onTerminado(Usuario in) {
					// TODO Auto-generated method stub
					
				}
			});
			/**/
			Server.get_matched_events(Session.getActiveSession().getAccessToken(), null, null, null, null, null,new Connecter<Vector<Evento>>() {			
				@Override
				public void onTerminado(Vector<Evento> vector) {
					
					Log.v("uhu", "oi"+vector);
					for (int i = 0; i <vector.size(); i++) {
					final	ItemEvent item=new ItemEvent();
						Log.v("uhu2", ""+vector.get(i).getName());
						item.titulo=vector.get(i).getName();
						item.quadra=vector.get(i).getLocalizationName();
						item.local=vector.get(i).getLocalizationAddress();
						item.qtd_participantes=vector.get(i).getUsers().size();
						item.amigos_qtd=vector.get(i).getNumFriends();
						item.esporte=vector.get(i).getSport();
						//item.cidade=
						item.hora=vector.get(i).getStartTime();
						item.data=vector.get(i).getDate();
					    item.preco_centavos=vector.get(i).getPrice();
					    //item.distancia=
						item.privado=vector.get(i).getPrivacy();
						item.evento=vector.get(i);
						item.amigos= new String[vector.get(i).getUsers().size()];
						for (int j = 0; j <vector.get(i).getUsers().size(); j++) {
							item.amigos[j]=vector.get(i).getUsers().get(j).getPhoto();
							
							//DownloadImagemAsyncTask
							Log.v("photo", ""+item.amigos[j]);
						}
						if(getView()!=null)
							getView().post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									lista.add(item);
									adapter.notifyDataSetChanged();

								}
							});

					}
					

				}
			});
		}
		//	try {t.join();}catch(Exception _) {}
	}

	
	@Override
	public void onResume(){
		super.onResume();


	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public    void  onSaveInstanceState( Bundle outState){




	}
	@Override
	public boolean onTouch(View arg0,MotionEvent  arg1) {
		// TODO Auto-generated method stub
		switch (arg1.getAction()&255) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			Button_criar.setTextColor(getResources().getColor(R.color.red));


			break;

		default:
			Button_criar.setTextColor(getResources().getColor(R.color.white));

			break;
		}


		return false;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		arg2--;
		ItemEvent item = lista.get(arg2);

		Bundle arg= new Bundle();
  		arg.putParcelable("evento",item );
  		Fragment fragment = new EventFragment();
  		fragment.setArguments(arg);
		((MainActivity)getActivity()).mudarAbaAtual(fragment);
		AgendaEventosFragment.lista.add(item);
		AgendaEventosFragment.adapter.notifyDataSetChanged();



	}


}

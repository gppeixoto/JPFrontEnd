package br.com.JoinAndPlay;

import java.util.ArrayList;
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
		lista.add(new ItemEvent());
		lista.add(new ItemEvent());
		adapter.notifyDataSetChanged();


		return tela;
	}
	boolean login = true;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lista.add(new ItemEvent());
		adapter.notifyDataSetChanged();
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
			Server.login(Session.getActiveSession().getAccessToken(),new Connecter() {
				
				@Override
				public void onTerminado(Object in) {
					// TODO Auto-generated method stub
					
				}
			});
			/**/
			Server.get_matched_events(Session.getActiveSession().getAccessToken(), null, null, null, null, null,new Connecter() {			
				@Override
				public void onTerminado(Object in) {
					Vector<Evento> vector = (Vector<Evento>) in;
					Server.enter_event(Session.getActiveSession().getAccessToken(), vector.get(0).getId(), new Connecter() {
						
						@Override
						public void onTerminado(Object in) {
							Evento a = (Evento) in;
							for (int i = 0; i < a.getUsers().size(); ++i) Log.v("uhu", a.getUsers().get(i).getName());

						}
					});
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

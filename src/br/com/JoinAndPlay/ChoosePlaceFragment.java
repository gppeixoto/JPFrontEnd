package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.com.JoinAndPlay.ListPlace.AdapterListViewPlace;
import br.com.JoinAndPlay.ListPlace.ItemPlace;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Local;
import br.com.JoinAndPlay.Server.Server;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChoosePlaceFragment extends Fragment implements OnItemClickListener, OnClickListener {
	
	SupportMapFragment supportMap;
	static ArrayList<ItemPlace> lista = new ArrayList<ItemPlace>();
	protected AdapterListViewPlace adapter ;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
lista.add(new ItemPlace());
		adapter = new AdapterListViewPlace(getActivity(),lista);

	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		MapsInitializer.initialize(getActivity());
		supportMap= new SupportMapFragment();
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_fragPlaces, supportMap).commit();
		
		View view = inflater.inflate(R.layout.choose_place, container,false);
		
		ListView listV=(ListView) view.findViewById(R.id.views_place);
		listV.setOnItemClickListener(this);
		listV.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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
			Server.login(Session.getActiveSession().getAccessToken(),new Connecter() {
				
				@Override
				public void onTerminado(Object in) {
					// TODO Auto-generated method stub
					
				}
			});
			/**/
		
		}	
	}
}
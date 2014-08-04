package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import br.com.JoinAndPlay.Server.Connecter;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;



public final class ConfigJP {

	private static Map<String,Integer> mapafutebol = new HashMap<String, Integer>();
	private static boolean init=false;
	public static String ip="172.22.67.244";
	private static void init(){
		if(!init){
			mapafutebol.put("futebol",ESPORTE_FUTEBOL);

		}
		init=true;
	}
	public static final int ESPORTE_FUTEBOL=0;

	public static final int[] ESPORTE_BITMAP ={
		R.drawable.futebol
	};
	public static final int[] ESPORTE_COR ={
		R.color.verde_futebol

	};
	public static final int[] ESPORTE_BARRA ={
		R.drawable.campo_esporte_futebol

	};


	public static final int getID(String esporte){
		init();
		esporte=esporte.toLowerCase();
		return 0;// mapafutebol.get(esporte);
	}

	public static void loguin(final Activity act,final Connecter<String> get){

		List<String> PERMISSIONS = new ArrayList<String>();
		PERMISSIONS.add("user_friends");
		PERMISSIONS.add("public_profile");

		PERMISSIONS.add("email");
		Session session = Session.openActiveSession(act, true,PERMISSIONS, new Session.StatusCallback() {

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
							getToken(act, get);

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
			Toast.makeText(act, session.getAccessToken(), Toast.LENGTH_LONG).show();

		}

	}
	public static void getToken(Activity act,Connecter<String> get){
			
		if(Session.getActiveSession()!=null && Session.getActiveSession().isOpened() ){
			if(get!=null)
			get.onTerminado(Session.getActiveSession().getAccessToken());
			return;
			
		}
		loguin(act,get);
		
 	}

}

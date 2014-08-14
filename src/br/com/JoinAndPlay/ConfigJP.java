package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;

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
	public static String UserId=null;
	private static void init(){
		if(!init){
			mapafutebol.put("",ESPORTE_DEFAULT);
			mapafutebol.put("futebol",ESPORTE_FUTEBOL);
			mapafutebol.put("corrida",ESPORTE_CORRIDA);
			mapafutebol.put("caminhada",ESPORTE_CAMINHADA);
			mapafutebol.put("boliche",ESPORTE_BOLICHE);
			mapafutebol.put("basquete",ESPORTE_BASQUETE);
			mapafutebol.put("ciclismo",ESPORTE_CICLISMO);
			mapafutebol.put("ping pong",ESPORTE_PING_PONG);
			mapafutebol.put("tênis",ESPORTE_TENIS);
			mapafutebol.put("baseball",ESPORTE_BASEBALL);
			mapafutebol.put("boxe",ESPORTE_BOXE);
			mapafutebol.put("cartas",ESPORTE_CARTAS);
			mapafutebol.put("dominó",ESPORTE_DOMINO);
			mapafutebol.put("futebol americano",ESPORTE_FUTEBOL_AMERICANO);
			mapafutebol.put("golfe",ESPORTE_GOLFE);
			mapafutebol.put("patinação",ESPORTE_PATINACAO);
			mapafutebol.put("sinuca",ESPORTE_SINUCA);
			mapafutebol.put("skate",ESPORTE_SKATE);
			mapafutebol.put("tênis de mesa",ESPORTE_TENIS_DE_MESA);
			mapafutebol.put("video-game",ESPORTE_VIDEO_GAME);
			mapafutebol.put("vôlei",ESPORTE_VOLEI);
			mapafutebol.put("vôlei de praia",ESPORTE_VOLEI_DE_PRAIA);
			mapafutebol.put("xadrez",ESPORTE_XADREZ);
			mapafutebol.put("jogos de tabuleiro",ESPORTE_JOGOS_DE_TABULEIRO);


		}
		init=true;
	}
	/*cadastrados*/
	public static final int ESPORTE_DEFAULT=0;
	public static final int ESPORTE_FUTEBOL=1;
	public static final int ESPORTE_CORRIDA=2;
	public static final int ESPORTE_BOLICHE=3;
	public static final int ESPORTE_VOLEI=4;
	public static final int ESPORTE_BASQUETE=5;
	public static final int ESPORTE_CICLISMO=6;
	public static final int ESPORTE_PING_PONG=7;
	public static final int ESPORTE_TENIS=8;
	public static final int ESPORTE_VIDEO_GAME=9;
	public static final int ESPORTE_SINUCA=10;
	public static final int ESPORTE_FUTEBOL_AMERICANO=11;


	/*repetidos*/
	public static final int ESPORTE_CAMINHADA=2;
	public static final int ESPORTE_TENIS_DE_MESA=7;
	public static final int ESPORTE_VOLEI_DE_PRAIA=4;


	/*faltam icones*/
	public static final int ESPORTE_BASEBALL=0;
	public static final int ESPORTE_BOXE=0;
	public static final int ESPORTE_CARTAS=0;
	public static final int ESPORTE_DOMINO=0;
	public static final int ESPORTE_GOLFE=0;
	public static final int ESPORTE_PATINACAO=0;
	public static final int ESPORTE_SKATE=0;
	public static final int ESPORTE_XADREZ=0;
	public static final int ESPORTE_JOGOS_DE_TABULEIRO=0;


	public static final int[] ESPORTE_BITMAP ={
		R.drawable.listfutebol,
		R.drawable.listfutebol,
		R.drawable.listcaminhar,
		R.drawable.listboliche,
		R.drawable.listvolei,
		R.drawable.listbasq,
		R.drawable.listbike,
		R.drawable.listpingpong,
		R.drawable.listtennis,
		R.drawable.listgame,
		R.drawable.listbilhar,
		R.drawable.listfuame

	};
	public static final int[] ESPORTE_COR ={
		R.color.futebol_verde,
		R.color.futebol_verde,
		R.color.Caminhada_verde,
		R.color.Boliche_lilas,
		R.color.volei_verde,
		R.color.basquete_laranja,
		R.color.ciclismo_marrom,
		R.color.ping_pong_azul,
		R.color.tennis_amarelo,
		R.color.Game_lilas,
		R.color.bilhar_preto,
		R.color.futebol_americano_laranja

	};
	public static final int[] ESPORTE_BARRA ={
		R.drawable.campo_esporte_futebol,
		R.drawable.campo_esporte_futebol,
		R.drawable.campo_esporte_caminhada,
		R.drawable.campo_esporte_boliche,
		R.drawable.campo_esporte_volei,
		R.drawable.campo_esporte_basquete,
		R.drawable.campo_esporte_ciclismo,
		R.drawable.campo_esporte_ping_pong,
		R.drawable.campo_esporte_tennis,
		R.drawable.campo_esporte_game,
		R.drawable.campo_esporte_bilhar,
		R.drawable.campo_esporte_futebol_americano


	};


	public static final int getEsporteID(String esporte){
		init();
		esporte=esporte.toLowerCase();
		if(mapafutebol.containsKey(esporte)){

			return mapafutebol.get(esporte);
		}
		return 1;
	}

	public static void login(final Activity act,final Connecter<String> get){

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
							if(user!=null){

								TelaInicialFragment tela = new TelaInicialFragment();
								//act.getFragmentManager().beginTransaction().replace(R.id.tela, tela).commit();

								UserId =user.getId();
								getToken(act, get);

							}
						}
					}
							).executeAsync();
				}
			}
		});

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
		login(act,get);

	}

	public static void getUserID(final Activity act,final Connecter<String> connecter) {
		// TODO Auto-generated method stub
		if(UserId!=null){
			connecter.onTerminado(UserId);
		}else{

			getToken(act, new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					// TODO Auto-generated method stub
					getUserID(act, connecter);
				}
			});
		}


	}

}

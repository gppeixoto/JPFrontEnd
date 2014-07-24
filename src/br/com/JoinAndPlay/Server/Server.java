package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Server implements Serializable {
	private static final long serialVersionUID = 8092668778830657391L;

	/**
	 * @param access_token acess_token do usuario que se quer logar.
	 * @return true se conseguiu logar, false caso contrário.
	 */
	public static void login(String access_token, Connecter con) {
		ServiceHandler sh = new ServiceHandler();
		JSONObject obj = new JSONObject();
		try {
			obj.put("access_token", access_token);
		} catch (Exception _) {}
		sh.makePOST(ServiceHandler.URL_BASE + "/login/", obj.toString(), con);
	}

	/**
	 * @param access_token acess_token do usuario que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public Usuario user_profile(String access_token) {
		return null;
	}

	/**
	 * @param access_token access_token do usuario que se deseja saber a agenda.
	 * @return eventos que esse usuario ja participou ou disse que ira participar. 
	 */
	public Vector<Evento> user_agenda(String access_token) {
		return null;
	}

	/**
	 * Caso algum dos parametros nao seja fornecido, passar null.
	 * @param access_token access_token do usuario que esta logado.
	 * @param address endereco origem de onde a pesquisa sera.
	 * @param date dia da pesquisa (no formato "dia mes", por ex: 03/07 seria "3 7")
	 * @param start_time hora inicial (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param end_time hora final (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param sports array de strings cada uma contendo o nome de um esporte.
	 * @return todos os eventos que satisfazem os parametros da pesquisa.
	 */
	public static void get_matched_events(final String access_token, final String address, final String date, final String start_time,
			final String end_time, final String[] sports, final Connecter fin) {

		JSONObject obj = new JSONObject();
		JSONArray arr_sports = new JSONArray();

		if (sports != null) {
			for (int i = 0; i < sports.length; ++i) arr_sports.put(sports[i]);
		}

		try {
			obj.put("access_token", access_token);
			obj.put("address", address == null ? "" : address);
			obj.put("date", date == null ? "" : date);
			obj.put("start_time", start_time == null ? "" : start_time);
			obj.put("end_time", end_time == null ? "" : end_time);
			obj.put("sports", arr_sports);
		}catch(JSONException _) {

		}

		Log.v("xuxu", ""+obj);
		ServiceHandler sh = new ServiceHandler();
		sh.makePOST(ServiceHandler.URL_BASE + "/getmatchedevents/", obj.toString(), new Connecter() {
			
			@Override    
			public void onTerminado(Object in) {
				Vector<Evento> ret = new Vector<Evento>();

				JSONObject json_ret;
				try {
					json_ret = new JSONObject((String) in);

					JSONArray json_array = json_ret.getJSONArray("events");

					for (int i = 0; i < json_array.length(); ++i) {
						JSONObject act_event = json_array.getJSONObject(i);
						String name = act_event.getString("name");
						JSONArray arr_users = act_event.getJSONArray("participants");
						Vector<Usuario> users = new Vector<Usuario>();
						for (int j = 0; j < arr_users.length(); ++j) {
							JSONArray act_user = arr_users.getJSONArray(j);
							Usuario to_add = new Usuario(act_user.getString(0), act_user.getString(1), "", ServiceHandler.URL_BASE + "/" + act_user.getString(2), null, null);
							users.add(to_add);
						}
						String localization_name = act_event.getString("localizationName");
						String localization_address = act_event.getString("localizationAddress");
						String sport = act_event.getString("sport");
						int num_friends = act_event.getInt("friendsCount");
						String date_evt = act_event.getString("date");
						String begin_time = act_event.getString("timeBegin");
						String id = act_event.getString("id");
						int price = act_event.getInt("price");
						boolean is_private = act_event.getBoolean("private");

						Evento to_add = new Evento(name, users, localization_name, localization_address, sport, num_friends, date_evt, begin_time, null, null, null, id, is_private, price);
						ret.add(to_add);
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}
				if (fin != null) fin.onTerminado(ret);
			}
		});

	}

	/**
	 * @param access_token access_token do usuario que ira entrar no evento.
	 * @param id id do evento que o usuario ira entrar.
	 * @return o evento que possui o id passado com o usuario dentro.
	 */
	public static void enter_event(final String access_token, final String id, final Connecter fin) {
		JSONObject obj = new JSONObject();

		try {
			obj.put("access_token", access_token);
			obj.put("id", id);
		}catch(JSONException _) {

		}

		ServiceHandler sh = new ServiceHandler();
		sh.makePOST(ServiceHandler.URL_BASE + "/enterevent/", obj.toString(), new Connecter() {
			
			@Override    
			public void onTerminado(Object in) {
				Evento ret = null;

				try {
					JSONObject act_event = new JSONObject((String) in);
					
					String name = act_event.getString("name");
					JSONArray arr_users = act_event.getJSONArray("participants");
					Vector<Usuario> users = new Vector<Usuario>();
					for (int j = 0; j < arr_users.length(); ++j) {
						JSONArray act_user = arr_users.getJSONArray(j);
						Usuario to_add = new Usuario(act_user.getString(0), act_user.getString(1), "", ServiceHandler.URL_BASE + "/" + act_user.getString(2), null, null);
						users.add(to_add);
					}
					String localization_name = act_event.getString("localizationName");
					String localization_address = act_event.getString("localizationAddress");
					String sport = act_event.getString("sport");
					int num_friends = act_event.getInt("friendsCount");
					String date_evt = act_event.getString("date");
					String begin_time = act_event.getString("timeBegin");
					String id = act_event.getString("id");
					int price = act_event.getInt("price");
					boolean is_private = act_event.getBoolean("private");

					ret = new Evento(name, users, localization_name, localization_address, sport, num_friends, date_evt, begin_time, null, null, null, id, is_private, price);
				}catch (JSONException e) {
					e.printStackTrace();
				}
				if (fin != null) fin.onTerminado(ret);
			}
		});
	} 
}

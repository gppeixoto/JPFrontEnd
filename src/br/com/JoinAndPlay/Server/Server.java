package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.JoinAndPlay.ConfigJP;
import android.app.Activity;
import android.util.Log;

public class Server implements Serializable {
	private static final long serialVersionUID = 8092668778830657391L;

	/**
	 * @param access_token acess_token do usuario que se quer logar.
	 * @return true se conseguiu logar, false caso contrario.
	 */
	public static void login(String access_token, Connecter<String> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/login/", obj.toString(), connecter);
		} catch (JSONException _) {}
	}

	/**
	 * @param access_token acess_token do usuario que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public static void user_profile(Activity act, final Connecter<Usuario> connecter) {
		
		ConfigJP.getToken(act, new Connecter<String>() {
			
			@Override
			public void onTerminado(String access_token) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject();
					obj.put("access_token", access_token);

					ServiceHandler sh = new ServiceHandler();
					sh.makePOST(ServiceHandler.URL_BASE + "/userprofile/", obj.toString(), new Connecter<String>() {

						@Override
						public void onTerminado(String in) {
							try {
								Usuario user = processUsuario(new JSONObject((String) in));
								if (connecter != null) connecter.onTerminado(user);
							} catch (JSONException _) {}
						}
					});
				} catch (JSONException _) {}
			}
		});
		
	}

	/**
	 * @param id id do usuario que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public static void user_profile_id(String id, final Connecter<Usuario> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", id);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/userprofileid/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						Usuario user = processUsuario(new JSONObject((String) in));
						if (connecter != null) connecter.onTerminado(user);
					} catch (JSONException _) {}
				}
			});
		} catch (JSONException _) {}
	}

	/**
	 * @param access_token acess_token do criador do evento.
	 * @param localization_name o nome do local.
	 * @param localization_address o endereco do local.
	 * @param city nome da cidade.
	 * @param neighbourhood nome do bairro.
	 * @param sport_name nome do esporte.
	 * @param date dia do evento. (no formato "ano-mes-dia")
	 * @param begin_time hora inicial do evento. (no formato "hora:minuto")
	 * @param end_time hora final do evento. (no formato "hora:minuto")
	 * @param description descricao do evento.
	 * @param name nome do evento.
	 * @param price preco do evento.
	 * @param privacy true se for privado, false se nao for.
	 * @return o evento criado.
	 * */
	public static void create_event(String access_token, String localization_name, String localization_address, String city,
			String neighbourhood, String sport_name, String date, String begin_time, String end_time, String description,
			String name, double price, boolean privacy, final Connecter<Evento> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);
			obj.put("localizationName", localization_name);
			obj.put("localizationAddress", localization_address);
			obj.put("city", city);
			obj.put("neighbourhood", neighbourhood);
			obj.put("eventSport", sport_name);
			obj.put("eventDay", date);
			obj.put("eventTimeBegin", begin_time);
			obj.put("eventTimeEnd", end_time);
			obj.put("eventDescription", description);
			obj.put("eventName", name);
			obj.put("eventPrice", price);
			obj.put("private", privacy);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/createevent/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						Evento evt = processEvent(new JSONObject((String) in));
						if (connecter != null) connecter.onTerminado(evt);
					} catch (JSONException _) {}
				}
			});

		} catch (JSONException _) {}
	}

	/**
	 * @param user_id id do usuario.
	 * @param event_id id do evento a ser removido.
	 * @return true caso tenha removido, false caso o usuario nao seja o criador do evento.
	 * */
	public static void delete_event(String user_id, String event_id, final Connecter<Boolean> connecter) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("user_id", user_id);
			obj.put("event_id", event_id);
		} catch (JSONException _) {}
		
		ServiceHandler sh = new ServiceHandler();
		sh.makePOST(ServiceHandler.URL_BASE + "/deleteevent/", obj.toString(), new Connecter<String>() {
			
			@Override
			public void onTerminado(String in) {
				try {
					JSONObject obj = new JSONObject(in);
					if (connecter != null) {
						if (obj.has("error")) connecter.onTerminado(false);
						else connecter.onTerminado(true);
					}
				} catch (JSONException _) {}
			}
		});
	}

	/**
	 * @param access_token acess_token do criador do evento.
	 * @param localization_name o nome do local.
	 * @param localization_address o endereco do local.
	 * @param city nome da cidade.
	 * @param neighbourhood nome do bairro.
	 * @param sport_name nome do esporte.
	 * @param date dia do evento. (no formato "ano-mes-dia")
	 * @param begin_time hora inicial do evento. (no formato "hora:minuto")
	 * @param end_time hora final do evento. (no formato "hora:minuto")
	 * @param description descricao do evento.
	 * @param name nome do evento.
	 * @param price preco do evento.
	 * @param privacy true se for privado, false se nao for.
	 * @param id id do evento.
	 * @return o evento criado.
	 * */
	public static void edit_event(String access_token, String localization_name, String localization_address, String city,
			String neighbourhood, String sport_name, String date, String begin_time, String end_time, String description,
			String name, double price, boolean privacy, String id, final Connecter<Evento> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);
			obj.put("localizationName", localization_name);
			obj.put("localizationAddress", localization_address);
			obj.put("city", city);
			obj.put("neighbourhood", neighbourhood);
			obj.put("eventSport", sport_name);
			obj.put("eventDay", date);
			obj.put("eventTimeBegin", begin_time);
			obj.put("eventTimeEnd", end_time);
			obj.put("eventDescription", description);
			obj.put("eventName", name);
			obj.put("eventPrice", price);
			obj.put("private", privacy);
			obj.put("id", id);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/editevent/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						Evento evt = processEvent(new JSONObject((String) in));
						if (connecter != null) connecter.onTerminado(evt);
					} catch (JSONException _) {}
				}
			});

		} catch (JSONException _) {}
	}

	/**
	 * @param access_token access_token do usuario que se deseja saber a agenda.
	 * @return eventos que esse usuario ja participou ou disse que ira participar. 
	 */
	public static void user_agenda(String access_token, final Connecter<Vector<Evento>> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/useragenda/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						JSONArray arr = new JSONArray((String) in);
						Vector<Evento> ret = new Vector<Evento>();
						for (int i = 0; i < arr.length(); ++i) {
							ret.add(processEvent(arr.getJSONObject(i)));
						}
						if (connecter != null) connecter.onTerminado(ret);
					} catch (JSONException _) {}
				}
			});
		} catch (JSONException _) {}
	}

	/**
	 * @param id id do usuario que sera avaliado.
	 * @param sport_name nome do esporte que o usuario sera avaliado.
	 * @param rate_vaue valor da avaliacao.
	 * @return o perfil do usuario avaliado. 
	 * */
	public static void rate_user(String id, String sport_name, String rate_value, final Connecter<Usuario> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("sport", sport_name);
			obj.put("value", rate_value);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/rateuser/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						Usuario ret = processUsuario(new JSONObject((String) in));
						if (connecter != null) connecter.onTerminado(ret);
					} catch (JSONException _) {}
				}
			});
		} catch (JSONException _) {}
	}

	/**
	 * @param id id do usuario que sera avaliado.
	 * @param tag_name nome da tag que o usuario sera avaliado.
	 * @return o perfil do usuario avaliado. 
	 * */
	public static void vote_in_tag_user(String id, String tag_name, final Connecter<Usuario> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("tag", tag_name);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/voteintaguser/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					try {
						Usuario ret = processUsuario(new JSONObject((String) in));
						if (connecter != null) connecter.onTerminado(ret);
					} catch (JSONException _) {}
				}
			});
		} catch (JSONException _) {}
	}

	/**
	 * Caso algum dos parametros nao seja fornecido, passar null.
	 * @param access_token access_token do usuario que esta logado.
	 * @param address endereco origem de onde a pesquisa sera.
	 * @param date dia da pesquisa (no formato "ano-mes-dia")
	 * @param start_time hora inicial (no formato "hora:minuto")
	 * @param end_time hora final (no formato "hora:minuto")
	 * @param sports array de strings cada uma contendo o nome de um esporte.
	 * @return todos os eventos que satisfazem os parametros da pesquisa.
	 */
	public static void get_matched_events(Activity act, final String address,final String date,final String start_time,
			final String end_time,final String[] sports, final Connecter<Vector<Evento>> connecter) {
		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
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
					Log.v("enviando", ""+obj);
				}catch(JSONException _) {

				}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/getmatchedevents/", obj.toString(), new Connecter<String>() {

					@Override    
					public void onTerminado(String in) {
						try {
							Vector<Evento> ret = new Vector<Evento>();
							JSONObject json_ret = new JSONObject((String) in);

							JSONArray json_array = json_ret.getJSONArray("events");
							for (int i = 0; i < json_array.length(); ++i) {
								ret.add(processEvent(json_array.getJSONObject(i)));
							}

							if (connecter != null) connecter.onTerminado(ret);
						} catch (JSONException _) {}
					}
				});
			}
		});


	}

	/**
	 * @param access_token access_token do usuario que esta logado.
	 * @param id id do evento.
	 * @return o evento.
	 */
	public static void get_detailed_event(Activity ac, final String id, final Connecter<Evento> connecter) {

		ConfigJP.getToken(ac, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();

				try {
					obj.put("access_token", access_token);
					obj.put("id", id);
					Log.v("tesssste", id);
				}catch(JSONException _) {

				}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/getevent/", obj.toString(), new Connecter<String>() {

					@Override    
					public void onTerminado(String in) {
						try {
							Evento ret = null;
							JSONObject json_ret = new JSONObject((String) in);
							Log.v("retorno", json_ret.toString()); 
							JSONObject json_evt = json_ret.getJSONObject("event");
							ret = processEvent(json_evt);

							if (connecter != null) connecter.onTerminado(ret);
						} catch (JSONException _) {}
					}
				});
			}
		});

	}

	/**
	 * @param access_token access_token do usuario que ira entrar no evento.
	 * @param id id do evento que o usuario ira entrar.
	 * @return o evento que possui o id passado com o usuario dentro.
	 */
	public static void enter_event(final Activity ac, final String id, final Connecter<Evento> connecter) {


		ConfigJP.getToken(ac, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();

				try {
					obj.put("access_token", access_token);
					obj.put("id", id);
				} catch(JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/enterevent/", obj.toString(), new Connecter<String>() {

					@Override    
					public void onTerminado(String in) {
						try {
							Evento ret = processEvent(new JSONObject((String) in));
							if (connecter != null) connecter.onTerminado(ret);
						} catch (JSONException _) {}
					}
				});
			}
		});
	}
	
	/**
	 * @param access_token o access_token do usuario que esta saindo.
	 * @param id o id do evento.
	 * @return o evento.
	 * */
	public static void leave_event(String access_token, String id, final Connecter<Evento> connecter) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("access_token", access_token);
			obj.put("id", id);
		} catch (JSONException _) {}
		
		ServiceHandler sh = new ServiceHandler();
		sh.makePOST(ServiceHandler.URL_BASE + "/leaveevent/", obj.toString(), new Connecter<String>() {
			
			@Override
			public void onTerminado(String in) {
				try {
					if (connecter != null) connecter.onTerminado(processEvent(new JSONObject(in)));
				} catch (JSONException _) {}				
			}
		});
	}

	/**
	 * @param user_id id do usuario.
	 * @param event_id id do evento.
	 * @param comment texto do comentario.
	 * @return A foto e o nome do usuario.
	 * */
	public static void comment(Activity ac,final String event_id,final String comment, final Connecter<Usuario> connecter) {
		ConfigJP.getUserID(ac, new Connecter<String>() {
			@Override
			public void onTerminado(String user_id) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("user_id", user_id);
					obj.put("event_id", event_id);
					obj.put("comment", comment);
				} catch (JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/comment/", obj.toString(), new Connecter<String>() {
					@Override
					public void onTerminado(String in) {
						try {
							JSONObject obj = new JSONObject(in);
							String name = obj.getString("name");
							String photo = obj.getString("photo");
							Usuario user = new Usuario(null, name, null, photo, null, 0, null, null, null, false);
							if (connecter != null) connecter.onTerminado(user);
						} catch (JSONException _) {}
					}
				});
			}
		});
	}

	/**
	 * @param user_id id de quem ta convidando.
	 * @param event_id id do evento.
	 * @param invite_ids vector de ids de quem esta sendo convidado.
	 * @return true se tiver dado certo, false se quem convidou nao for o criador do evento.
	 * */	
	public static void invite(String user_id, String event_id, Vector<String> invite_ids, final Connecter<Boolean> connecter) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", user_id);
			obj.put("event_id", event_id);
			JSONArray arr = new JSONArray();
			for (int i = 0; i < invite_ids.size(); ++i) arr.put(invite_ids.get(i));
			obj.put("user_id_list", arr);
		} catch (JSONException _) {}

		ServiceHandler sh = new ServiceHandler();
		sh.makePOST(ServiceHandler.URL_BASE + "/invite/", obj.toString(), new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				try {
					JSONObject obj = new JSONObject(in);
					if (connecter != null) {
						if (obj.has("error")) connecter.onTerminado(false);
						else connecter.onTerminado(true);
					}
				} catch (JSONException _) {}
			}
		});
	}

	private static Usuario processUsuario(JSONObject user) {

		try {
			String id = "0";//user.getString("id");
			String name = user.getString("name");
			String photo = user.getString("url");
			Vector<RatingSport> ratings = new Vector<RatingSport>();
			JSONArray arr = user.getJSONArray("ratings");
			for (int i = 0; i < arr.length(); ++i) {
				JSONArray aux = arr.getJSONArray(i);
				ratings.add(new RatingSport(aux.getString(0), aux.getString(1)));
			}
			Vector<Tag> tags = new Vector<Tag>();
			arr = user.getJSONArray("tags");
			for (int i = 0; i < arr.length(); ++i) {
				JSONArray aux = arr.getJSONArray(i);
				tags.add(new Tag(aux.getString(0), aux.getInt(1)));
			}
			Vector<Esporte> times_sports = new Vector<Esporte>();
			arr = user.getJSONArray("sportsInfo");
			for (int i = 0; i < arr.length(); ++i) {
				JSONArray aux = arr.getJSONArray(i);
				times_sports.add(new Esporte(aux.getString(0), aux.getInt(1)));
			}
			int num_friends = user.getInt("friends");
			boolean has_notification = user.getBoolean("notifications");

			return new Usuario("", name, "", photo, null, num_friends, ratings, tags, times_sports, has_notification);
		} catch (JSONException a) { a.printStackTrace();}
		return null;
	}

	private static Evento processEvent(JSONObject evt) {
		try {
			String name = evt.getString("name");
			JSONArray arr_users = evt.getJSONArray("participants");
			Vector<Usuario> users = new Vector<Usuario>();
			for (int j = 0; j < arr_users.length(); ++j) {
				JSONArray act_user = arr_users.getJSONArray(j);
				Usuario to_add = new Usuario(act_user.getString(0), act_user.getString(1), "", act_user.getString(2), null, 0, null, null, null, false);
				users.add(to_add);
			}
			String localization_name = evt.getString("localizationName");
			String localization_address = evt.getString("localizationAddress");
			String sport = evt.getString("sport");
			int num_friends = evt.getInt("friendsCount");
			String date_evt = evt.getString("date");
			String begin_time = evt.getString("timeBegin");
			String end_time = null;
			if (evt.has("timeEnd")) end_time = evt.getString("timeEnd");
			String description = null;
			if (evt.has("description")) description = evt.getString("description");
			Vector<Comentario> comments = null;
			if (evt.has("comments")) {
				comments = new Vector<Comentario>();
				JSONArray arr_comments = evt.getJSONArray("comments");
				for (int i = 0; i < arr_comments.length(); ++i) {
					JSONArray aux = arr_comments.getJSONArray(i);
					comments.add(new Comentario(aux.getString(0), aux.getString(1), aux.getString(2)));
				}
			}
			String id = evt.getString("id");
			int price = evt.getInt("price");
			boolean is_private = evt.getBoolean("private");
			String city = null; 
			if (evt.has("city")) city = evt.getString("city");
			String neighbourhood = null;
			if (evt.has("neighbourhood")) neighbourhood = evt.getString("neighbourhood");

			return new Evento(name, users, localization_name, localization_address, sport, num_friends, date_evt, begin_time, end_time, description, comments, id, is_private, price, city, neighbourhood);
		} catch (JSONException _) {
			Log.v("retorno2", "ferrou");
		}
		return null;
	}
}

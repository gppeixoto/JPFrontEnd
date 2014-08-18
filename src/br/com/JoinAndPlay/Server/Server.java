package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.JoinAndPlay.ConfigJP;
import android.app.Activity;
import android.util.Log;

import org.joda.*;
import org.joda.time.DateTime;

public class Server implements Serializable {
	private static final long serialVersionUID = 8092668778830657391L;

	/**
	 * @param access_token acess_token do usuario que se quer logar.
	 * @return true se conseguiu logar, false caso contrario.
	 */
	public static void get_version(final Connecter<Vector<Integer>> connecter) {
		(new ServiceHandler()).makeGET(ServiceHandler.URL_BASE + "/getversion/", new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				try {
					if (connecter != null) {
						if (in != null) {
							JSONObject obj = new JSONObject(in);
							String version = obj.getString("version");
							String[] arr = version.split("[.]");
							int a = Integer.parseInt(arr[0]);
							int b = Integer.parseInt(arr[1]);
							Vector<Integer> v = new Vector<Integer>();
							v.add(a); v.add(b);
							connecter.onTerminado(v);
						} else {
							connecter.onTerminado(null);
						}
					}
				} catch (JSONException _) {}
			}
		});
	}

	/**
	 * @param access_token acess_token do usuario que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public static void user_profile(Activity act, final Connecter<Usuario> connecter) {

		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("access_token", access_token);

					ServiceHandler sh = new ServiceHandler();
					sh.makePOST(ServiceHandler.URL_BASE + "/userprofile/", obj.toString(), new Connecter<String>() {

						@Override
						public void onTerminado(String in) {
							if (in == null) {
								if (connecter != null) connecter.onTerminado(null);
								return;
							}
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
	 * @param caller_id id de quem ta chamando o metodo
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public static void user_profile_id(final String id, Activity act, final Connecter<Usuario> connecter) {
		ConfigJP.getUserID(act, new Connecter<String>() {

			@Override
			public void onTerminado(String caller_id) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("id", id);
					obj.put("uId", caller_id);

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
	 * @return o evento criado.
	 * */
	public static boolean create_event(Activity arc,final String localization_name,final String localization_address,final String city,
			final String neighbourhood, final String sport_name,final String date,final String begin_time,final String end_time,final String description,
			final String name,final double price,final Boolean privacy, final Connecter<Evento> connecter) {

		final double[] latlng=ConfigJP.getLatLngFromAddress(arc,localization_address+","+city+","+neighbourhood);
		if(latlng!=null){
			ConfigJP.getToken(arc, new Connecter<String>() {
				@Override
				public void onTerminado(String access_token) {
					Server.create_event(access_token,localization_name, localization_address, city, neighbourhood, sport_name, date, begin_time, end_time, description, name, price, privacy,latlng[0],latlng[1], connecter);
				}
			});
			return true;
		}
		return false;
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
	 * @param latlng2 longitude.
	 * @param latlng latitude.
	 * @return o evento criado.
	 * */
	public static void create_event(String access_token, String localization_name, String localization_address, String city,
			String neighbourhood, String sport_name, String date, String begin_time, String end_time, String description,
			String name, double price, boolean privacy, double latlng, double latlng2, final Connecter<Evento> connecter) {
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
			obj.put("latitude", latlng);
			obj.put("longitude", latlng2);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/createevent/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					if (in == null) {
						if (connecter != null) connecter.onTerminado(null);
						return;
					}
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
				if (in == null) {
					if (connecter != null) connecter.onTerminado(null);
					return;
				}
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
	public static void edit_event(Activity act, final String localization_name, final String localization_address, final String city,
			final String neighbourhood, final String sport_name, final String date, final String begin_time, final String end_time, final String description,
			final String name, final double price, final boolean privacy, final String id, final Connecter<Evento> connecter) {
		final double[] latlng=ConfigJP.getLatLngFromAddress(act,localization_address+","+city+","+neighbourhood);
		if(latlng!=null){
			ConfigJP.getToken(act, new Connecter<String>() {

				@Override
				public void onTerminado(String access_token) {
					// TODO Auto-generated method stub
					edit_event(access_token, localization_name, localization_address, city, neighbourhood, sport_name, date, begin_time, end_time, description, name, price, privacy, id, latlng[0]+"", latlng[1]+"",connecter);

				}
			});
		}
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
			String name, double price, boolean privacy, String id, String latitude, String longitude, final Connecter<Evento> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);
			obj.put("localizationName", localization_name);
			obj.put("localizationAddress", localization_address);
			obj.put("city", city);
			obj.put("neighbourhood", neighbourhood);
			obj.put("eventSport", sport_name);
			String[] aux = date.split("/");
			DateTime now = DateTime.now();
			String year = now.getYear()+"";
			String __date = year + "-" + aux[1] + "-" + aux[0];
			obj.put("eventDay", __date);
			obj.put("eventTimeBegin", begin_time);
			obj.put("eventTimeEnd", end_time);
			obj.put("eventDescription", description);
			obj.put("eventName", name);
			obj.put("eventPrice", price);
			obj.put("private", privacy);
			obj.put("id", id);
			obj.put("latitude", latitude);
			obj.put("longitude", longitude);

			Log.v("enviandooo", obj.toString());

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/editevent/", obj.toString(), new Connecter<String>() {

				@Override
				public void onTerminado(String in) {
					Log.v("retornoooo", in+"");
					if (in == null) {
						if (connecter != null) connecter.onTerminado(null);
						return;
					}
					try {
						JSONObject obj = new JSONObject(in);
						Evento evt = null;
						if (!obj.has("error")) evt = processEvent(obj);
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
	public static void user_agenda(Activity act, final Connecter<Vector<Evento>> connecter) {
		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject();
					obj.put("access_token", access_token);

					ServiceHandler sh = new ServiceHandler();
					sh.makePOST(ServiceHandler.URL_BASE + "/useragenda/", obj.toString(), new Connecter<String>() {

						@Override
						public void onTerminado(String in) {
							if (in == null) {
								if (connecter != null) connecter.onTerminado(null);
								return;
							}
							try {
								JSONObject obj = new JSONObject(in);
								JSONArray arr = obj.getJSONArray("events");
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
		});
	}

	public static void get_past(Activity act, final Connecter<Vector<Evento>> connecter) {
		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject();
					obj.put("access_token", access_token);

					ServiceHandler sh = new ServiceHandler();
					sh.makePOST(ServiceHandler.URL_BASE + "/getpast/", obj.toString(), new Connecter<String>() {

						@Override
						public void onTerminado(String in) {
							if (in == null) {
								if (connecter != null) connecter.onTerminado(null);
								return;
							}
							try {
								JSONObject obj = new JSONObject(in);
								JSONArray arr = obj.getJSONArray("events");
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
		});
	}

	
	/**
	 * @param id id do usuario que sera avaliado.
	 * @param sport_name nome do esporte que o usuario sera avaliado.
	 * @param rate_vaue valor da avaliacao.
	 * @return o perfil do usuario avaliado. 
	 * */
	public static void rate_user(final String id, Activity act, final String sport_name, final String rate_value, final Connecter<Usuario> connecter) {
		ConfigJP.getUserID(act, new Connecter<String>() {
			@Override
			public void onTerminado(String voter_id) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("id", id);
					obj.put("v_id", voter_id);
					obj.put("sport", sport_name);
					obj.put("value", Double.parseDouble(rate_value));
					System.out.println(obj.toString());
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
		});
	}

	/**
	 * @param id id do usuario que sera avaliado.
	 * @param tag_name nome da tag que o usuario sera avaliado.
	 * @return o perfil do usuario avaliado. 
	 * */
	public static void vote_in_tag_user(final String id, Activity act, final String tag_name, final Connecter<Usuario> connecter) {
		ConfigJP.getUserID(act, new Connecter<String>() {

			@Override
			public void onTerminado(String voter_id) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("id", id);
					obj.put("v_id", voter_id);
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
		});
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
					for (int i = 0; i < sports.length && sports[i] != null; ++i) arr_sports.put(sports[i]);
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
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
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
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
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
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
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
	public static void leave_event(Activity act,final String id, final Connecter<Evento> connecter) {

		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("access_token", access_token);
					obj.put("id", id);
				} catch (JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/leaveevent/", obj.toString(), new Connecter<String>() {

					@Override
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
						try {
							if (connecter != null) connecter.onTerminado(processEvent(new JSONObject(in)));
						} catch (JSONException _) {}				
					}
				});

			}
		});
	}

	/**
	 * @param user_id id do usuario.
	 * @param event_id id do evento.
	 * @param comment texto do comentario.
	 * @return A foto e o nome do usuario.
	 * */
	public static void comment(Activity ac,final String event_id,final String comment, final Connecter<Comentario> connecter) {
		ConfigJP.getUserID(ac, new Connecter<String>() {
			@Override
			public void onTerminado(final String user_id) {
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
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
						try {
							JSONObject obj = new JSONObject(in);
							String name = obj.getString("name");
							String photo = obj.getString("photo");
							String hour = obj.getString("time");
							String date = obj.getString("day");
							Comentario c = new Comentario(comment, name, user_id, photo, hour, date);
							if (connecter != null) connecter.onTerminado(c);
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
				if (in == null) {
					if (connecter != null) connecter.onTerminado(null);
					return;
				}
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

	private static String preprocess(String s) {
		if (s == null) return null;
		return s.trim().replaceAll(" ", "+");
	}

	private static boolean ok(String s) {
		return s != null && s.length() > 0;
	}

	/**
	 * Caso nao seja passado, passar null ou string vazia.
	 * @param localName nome do local.
	 * @param streetName nome da rua.
	 * @param neighborhood nome do bairro.
	 * @param city nome da cidade.
	 * @return um Vector de Vector< String > que o primeiro elemento eh o nome do local e o segundo eh o endereco.
	 * */
	public static void getAddresses(String localName, String streetName, String neighborhood, String city, final Connecter<Vector<Endereco>> connecter) {
		localName = preprocess(localName);
		streetName = preprocess(streetName);
		neighborhood = preprocess(neighborhood);
		city = preprocess(city);
		StringBuffer sb = new StringBuffer("https://maps.googleapis.com/maps/api/place/textsearch/json?query=");
		boolean first = true;
		if (ok(localName)) {
			sb.append(localName);
			first = false;
		}
		if (ok(streetName)) {
			if (!first) sb.append("+");
			sb.append(streetName);
			first = false;
		}
		if (ok(neighborhood)) {
			if (!first) sb.append("+");
			sb.append(neighborhood);
			first = false;
		}
		if (ok(city)) {
			if (!first) sb.append("+");
			sb.append(city);
		}
		sb.append("&sensor=true&key=AIzaSyDL-BaFaZBo_evgT2pXRJ1avAb8sWZ3KGE");
		String s = sb.toString();
		System.out.println(s);
		(new ServiceHandler()).makeGET(s, new Connecter<String>() {
			@Override
			public void onTerminado(String in) {
				if (in == null) {
					if (connecter != null) connecter.onTerminado(null);
					return;
				}
				try {
					JSONObject obj = new JSONObject(in);
					JSONArray arr = obj.getJSONArray("results");
					Vector<Endereco> addresses = new Vector<Endereco>();
					for (int i = 0; i < arr.length(); ++i) {
						JSONObject aux = arr.getJSONObject(i);
						String name = aux.getString("name");
						String address = aux.getString("formatted_address");
						addresses.add(new Endereco(name, address));
					}
					if (connecter != null) connecter.onTerminado(addresses);
				} catch (JSONException _) {}
			}
		});
	}

	/**
	 * @param access_token access_token da pessoa que se deseja os amigos.
	 * @return os amigos.
	 * */
	public static void get_friends(Activity act, final Connecter<Vector<Usuario>> connecter) {

		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				// TODO Auto-generated method stub


				JSONObject obj = new JSONObject();
				try {
					obj.put("access_token", access_token);
				} catch (JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/getfriends/", obj.toString(), new Connecter<String>() {
					@Override
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
						try {
							JSONObject obj = new JSONObject(in);
							JSONArray arr = obj.getJSONArray("friends");
							Vector<Usuario> ret = new Vector<Usuario>();
							for (int i = 0; i < arr.length(); ++i) {
								JSONArray a = arr.getJSONArray(i);
								Vector<Tag> tags = new Vector<Tag>();
								JSONArray tags_json = a.getJSONArray(3);
								for (int j = 0; j < tags_json.length(); ++j) {
									String aux = tags_json.getString(j);
									tags.add(new Tag(aux, 0));
								}
								ret.add(new Usuario(a.getString(0), a.getString(1), null, a.getString(2), null, 0, null, tags, null, false,false));
							}
							if (connecter != null) connecter.onTerminado(ret);
						} catch (JSONException _) {}
					}
				});


			}});
	}

	/**
	 * @return todos os eventos futuros.
	 * */
	public static void get_future_events(Activity activity, final Connecter<Vector<Evento>> connecter) {
		ConfigJP.getToken(activity, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("access_token", access_token);
				} catch (JSONException _) {}

				(new ServiceHandler()).makePOST(ServiceHandler.URL_BASE + "/getfutureevents/", obj.toString(), new Connecter<String>() {

					@Override
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
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
	 * @return um map em que cada chave eh o nome de um esporte, e o valor eh um vector com todos os 'invites' desse esporte.
	 * */
	public static void get_invites(String user_id, final Connecter<Map<String, Vector<Notificacao>>> connecter) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", user_id);
		} catch (JSONException _) {}

		(new ServiceHandler()).makePOST(ServiceHandler.URL_BASE + "/getinvites/", obj.toString(), new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				if (in == null) {
					if (connecter != null) connecter.onTerminado(null);
					return;
				}
				try {
					JSONObject obj = new JSONObject(in);
					Map<String, Vector<Notificacao>> map = new HashMap<String, Vector<Notificacao>>();
					if (!obj.has("no invites for you")) {
						obj = obj.getJSONObject("inviteList");
						Iterator<String> it = obj.keys();
						while (it.hasNext()) {
							String key = it.next();
							Vector<Notificacao> vec;
							if (map.containsKey(key)) {
								vec = map.get(key);
							} else {
								vec = new Vector<Notificacao>();
							}
							JSONArray arr = obj.getJSONArray(key);
							for (int i = 0; i < arr.length(); ++i) {
								vec.add(processNotification(arr.getJSONObject(i)));
							}
							map.put(key, vec);
						}
					}
					if (connecter != null) connecter.onTerminado(map);
				} catch (JSONException _) {}
			}
		});
	}

	/**
	 * @param access_token access_token do usuario que se deseja saber a agenda.
	 * @param localization no formato "latitute,longitude", exemplo: "-8.039573000000001,-34.899502"
	 * @return eventos que esse usuario ja participou ou disse que ira participar.
	 */
	public static void user_agenda(String access_token, String localization, final Connecter<Vector<Evento>> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("access_token", access_token);
			obj.put("localization", localization);

			ServiceHandler sh = new ServiceHandler();
			sh.makePOST(ServiceHandler.URL_BASE + "/useragenda/", obj.toString(), new Connecter<String>() {
				@Override
				public void onTerminado(String in) {
					if (in == null) {
						if (connecter != null) connecter.onTerminado(null);
						return;
					}
					try {
						JSONObject obj = new JSONObject(in);
						JSONArray arr = obj.getJSONArray("events");
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
	 * @param access_token access_token do usuario que se deseja saber a agenda.
	 * @param localization no formato "latitute,longitude", exemplo: "-8.039573000000001,-34.899502"
	 * @return todos os eventos futuros.
	 * */
	public static void get_future_events(Activity act, final String localization, final Connecter<Vector<Evento>> connecter) {

		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				// TODO Auto-generated method stub

				JSONObject obj = new JSONObject();
				try {
					obj.put("access_token", access_token);
					obj.put("localization", localization);
				} catch (JSONException _) {}
				Log.v("mandando", obj.toString());
				(new ServiceHandler()).makePOST(ServiceHandler.URL_BASE + "/getfutureevents/", obj.toString(), new Connecter<String>() {

					@Override
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
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
			}});
	}

	/**
	 * @return true se tiver dado certo e false caso contrario.
	 * */
	public static void close_event(String event_id, final Connecter<Boolean> connecter) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", event_id);

			(new ServiceHandler()).makePOST(ServiceHandler.URL_BASE + "/closeevent/", obj.toString(), new Connecter<String>() {
				@Override
				public void onTerminado(String in) {
					if (in == null) {
						if (connecter != null) connecter.onTerminado(null);
						return;
					}
					try {
						JSONObject obj = new JSONObject(in);
						if (connecter != null) {
							if (obj.has("closed")) connecter.onTerminado(true);
							else connecter.onTerminado(false);
						}
					} catch (JSONException _) {}
				}
			});
		} catch (JSONException _) {}
	}

	public static void arrive_event(Activity ac, final String event_id, final Connecter<Evento> connecter) {

		ConfigJP.getToken(ac, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();

				try {
					obj.put("access_token", access_token);
					obj.put("id", event_id);
				} catch(JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/arrive/", obj.toString(), new Connecter<String>() {
					@Override    
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
						try {
							Evento ret = processEvent(new JSONObject((String) in));
							if (connecter != null) connecter.onTerminado(ret);
						} catch (JSONException _) {}
					}
				});
			}
		});
	}

	public static void cancel_arrive(Activity act, final String event_id, final Connecter<Evento> connecter) {

		ConfigJP.getToken(act, new Connecter<String>() {

			@Override
			public void onTerminado(String access_token) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("access_token", access_token);
					obj.put("id", event_id);
				} catch (JSONException _) {}

				ServiceHandler sh = new ServiceHandler();
				sh.makePOST(ServiceHandler.URL_BASE + "/cancelarrive/", obj.toString(), new Connecter<String>() {

					@Override
					public void onTerminado(String in) {
						if (in == null) {
							if (connecter != null) connecter.onTerminado(null);
							return;
						}
						try {
							if (connecter != null) connecter.onTerminado(processEvent(new JSONObject(in)));
						} catch (JSONException _) {}				
					}
				});

			}
		});
	}


	private static Notificacao processNotification(JSONObject notification) {
		try {
			String creator = notification.getString("creator");
			String photo = notification.getString("creatorImage");
			String event_name = notification.getString("eventName");
			String event_id = notification.get("id")+"";
			String begin = notification.getString("timeBegin");
			String date = notification.getString("date");
			boolean privado = notification.getBoolean("private");
			String local_name = notification.getString("localizationName");
			String bairro = notification.getString("neighbourhood");
			return new Notificacao(creator, event_name, event_id, begin, date, privado, photo, local_name, bairro);
		} catch (JSONException _) {}
		return null;
	}


	private static Usuario processUsuario(JSONObject user) {

		try {
			String id = user.get("id")+"";
			String name = user.getString("name");
			String photo = user.getString("url");
			Vector<RatingSport> ratings = new Vector<RatingSport>();
			JSONArray arr = user.getJSONArray("ratings");
			for (int i = 0; i < arr.length(); ++i) {
				JSONArray aux = arr.getJSONArray(i);
				ratings.add(new RatingSport(aux.getString(0), aux.get(2)+"", aux.getInt(1), aux.getInt(3)));
			}
			Vector<Tag> tags = new Vector<Tag>();
			arr = user.getJSONArray("tags");
			for (int i = 0; i < arr.length(); ++i) {
				JSONArray aux = arr.getJSONArray(i);
				tags.add(new Tag(aux.getString(0), aux.getInt(1)));
			}
			Vector<Esporte> times_sports = new Vector<Esporte>();
			if (user.has("sportsInfo")) {
				arr = user.getJSONArray("sportsInfo");
				for (int i = 0; i < arr.length(); ++i) {
					JSONArray aux = arr.getJSONArray(i);
					times_sports.add(new Esporte(aux.getString(0), aux.getInt(1)));
				}
			}
			int num_friends = user.getInt("friends");
			boolean has_notification = user.getBoolean("notifications");

			return new Usuario(id, name, "", photo, null, num_friends, ratings, tags, times_sports, has_notification, false);
		} catch (JSONException a) { a.printStackTrace();}
		return null;
	}

	private static Evento processEvent(JSONObject evt) {
		try {
			String name = evt.getString("name");
			Vector<Usuario> users = new Vector<Usuario>();
			JSONArray arr_users = evt.getJSONArray("participants");
			for (int j = 0; j < arr_users.length(); ++j) {
				JSONArray act_user = arr_users.getJSONArray(j);
				Vector<Tag> tags = new Vector<Tag>();
				boolean votou = false;
				if (act_user.length() > 3) {
					System.out.println(act_user);
					JSONArray arr = act_user.getJSONArray(3);
					for (int i = 0; i < arr.length(); ++i) {
						String aux = arr.getString(i);
						tags.add(new Tag(aux, 0));
					}
					votou = act_user.getBoolean(4);
				}

				Usuario to_add = new Usuario(act_user.get(0)+"", act_user.getString(1), "", act_user.getString(2), null, 0, null, tags, null, false, votou);
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
					comments.add(new Comentario(aux.getString(0), aux.getString(1), aux.get(2)+"", aux.getString(3), aux.getString(4), aux.get(5)+""));
				}
			}
			String id = evt.get("id")+"";
			int price = Integer.parseInt(evt.getString("price"));
			boolean is_private = evt.getBoolean("private");
			String city = null;
			if (evt.has("city")) city = evt.getString("city");
			String neighbourhood = null;
			if (evt.has("neighbourhood")) neighbourhood = evt.getString("neighbourhood");
			String distance = "";
			if (evt.has("localizationDistance")) distance = evt.get("localizationDistance")+"";
			String latitude = "";
			if (evt.has("latitude")) latitude = evt.get("latitude")+"";
			String longitude = "";
			if (evt.has("longitude")) longitude = evt.get("longitude")+""; 
			String creator_id = "";
			if (evt.has("creatorID")) creator_id = evt.get("creatorID")+"";
			boolean participates = false;
			if (evt.has("isParticipating")) participates = evt.getBoolean("isParticipating");
			boolean has_arrived = false;
			if (evt.has("hasArrived")) has_arrived = evt.getBoolean("hasArrived");
			Vector<Usuario> at_event = new Vector<Usuario>();
			if (evt.has("arrived")) {
				arr_users = evt.getJSONArray("arrived");
				for (int j = 0; j < arr_users.length(); ++j) {
					JSONArray act_user = arr_users.getJSONArray(j);
					Usuario to_add = new Usuario(act_user.get(0)+"", act_user.getString(1), "", act_user.getString(2), null, 0, null, null, null, false, false);
					at_event.add(to_add);
				}
			}
			boolean closed = false;
			if (evt.has("startVoting")) closed = evt.getBoolean("startVoting");
			return new Evento(name, users, localization_name, localization_address, sport, num_friends, date_evt, begin_time, end_time, description, comments, id, is_private, price, city, neighbourhood, distance, latitude, longitude, creator_id, participates, has_arrived, at_event, closed);
		} catch (JSONException _) {
			_.printStackTrace();
		}
		return null;
	}
}

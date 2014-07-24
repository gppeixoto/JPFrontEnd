package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

public class Server implements Serializable {
	private static final long serialVersionUID = 8092668778830657391L;

	/**
	 * @param access_token acess_token do usuario que se quer logar.
	 * @return true se conseguiu logar, false caso contrário.
	 */
	public boolean login(String access_token) {
		return false;
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
	public Vector<Evento> get_matched_events(String access_token, String address, String date, String start_time, String end_time, String[] sports) {
		return null;
	}

	/**
	 * @param access_token access_token do usuario que ira entrar no evento.
	 * @param id id do evento que o usuario ira entrar.
	 * @return o evento que possui o id passado com o usuario dentro.
	 */
	public Evento enter_event(String access_token, String id) {
		return null;
	} 
}

package br.com.JoinAndPlay.Server;

import java.util.Vector;

public class Server {

	/**
	 * @param access_token acess_token do usuário que se quer logar.
	 * @return true se conseguiu logar, false caso contrário.
	 */
	public boolean login(String access_token) {
		return false;
	}

	/**
	 * @param access_token acess_token do usuário que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public Usuario user_profile(String access_token) {
		return null;
	}

	/**
	 * @param access_token access_token do usuário que se deseja saber a agenda.
	 * @return eventos que esse usuario já participou ou disse que irá participar. 
	 */
	public Vector<Evento> user_agenda(String access_token) {
		return null;
	}

	/**
	 * Caso algum dos parâmetros não seja fornecido, passar null.
	 * @param access_token access_token do usuario que está logado.
	 * @param address endereço origem de onde a pesquisa será.
	 * @param date dia da pesquisa (no formato "dia mes", por ex: 03/07 seria "3 7")
	 * @param start_time hora inicial (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param end_time hora final (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param sports array de strings cada uma contendo o nome de um esporte.
	 * @return todos os eventos que satisfazem os parâmetros da pesquisa.
	 */
	public Vector<Evento> get_matched_events(String access_token, String address, String date, String start_time, String end_time, String[] sports) {
		return null;
	}

	/**
	 * @param access_token access_token do usuário que irá entrar no evento.
	 * @param id id do evento que o usuário irá entrar.
	 * @return o evento que possui o id passado com o usuário dentro.
	 */
	public Evento enter_event(String access_token, String id) {
		return null;
	}
}

package br.com.JoinAndPlay.Server;

import java.util.Vector;

public class Server {

	/**
	 * @param access_token acess_token do usu�rio que se quer logar.
	 * @return true se conseguiu logar, false caso contr�rio.
	 */
	public boolean login(String access_token) {
		return false;
	}

	/**
	 * @param access_token acess_token do usu�rio que se quer o perfil.
	 * @return o perfil do usuario que possui esse access_token.
	 */
	public Usuario user_profile(String access_token) {
		return null;
	}

	/**
	 * @param access_token access_token do usu�rio que se deseja saber a agenda.
	 * @return eventos que esse usuario j� participou ou disse que ir� participar. 
	 */
	public Vector<Evento> user_agenda(String access_token) {
		return null;
	}

	/**
	 * Caso algum dos par�metros n�o seja fornecido, passar null.
	 * @param access_token access_token do usuario que est� logado.
	 * @param address endere�o origem de onde a pesquisa ser�.
	 * @param date dia da pesquisa (no formato "dia mes", por ex: 03/07 seria "3 7")
	 * @param start_time hora inicial (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param end_time hora final (no formato "hora minuto", por ex: 13:45 seria "13 45")
	 * @param sports array de strings cada uma contendo o nome de um esporte.
	 * @return todos os eventos que satisfazem os par�metros da pesquisa.
	 */
	public Vector<Evento> get_matched_events(String access_token, String address, String date, String start_time, String end_time, String[] sports) {
		return null;
	}

	/**
	 * @param access_token access_token do usu�rio que ir� entrar no evento.
	 * @param id id do evento que o usu�rio ir� entrar.
	 * @return o evento que possui o id passado com o usu�rio dentro.
	 */
	public Evento enter_event(String access_token, String id) {
		return null;
	}
}

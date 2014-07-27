package br.com.JoinAndPlay.Server;

public class Comentario {
	private String text;
	private String user_name;
	private String user_id;
	
	public Comentario(String text, String user_name, String user_id) {
		this.text = text;
		this.user_name = user_name;
		this.user_id = user_id;
	}
	
	/**
	 * @return o conteudo do comentario.
	 * */
	public String getText() { return this.text; }
	
	/**
	 * @return o nome do usuario que comentou.
	 * */
	public String getUserName() { return this.user_name; }
	
	/**
	 * @return o id do usuario que comentou.
	 * */
	public String getId() { return this.user_id; }
}

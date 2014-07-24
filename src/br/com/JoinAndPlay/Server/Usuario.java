package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 8250002358792274323L;
	
	private String id;
	private String name;
	private int rating;
	private String photo;
	private Vector<Evento> events;
	private Vector<Usuario> friends;
	
	public Usuario(String id, String name, int rating, String photo, Vector<Evento> events, Vector<Usuario> friends) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.photo = photo;
		this.events = events;
		this.friends = friends;
	}
	
	/**
	 * @return o id desse usuario.
	 * */
	public String getId() { return this.id; }
	
	/**
	 * @return o nome do usuario.
	 * */
	public String getName() { return this.name; }
	
	/**
	 * @return o rating do usuario.
	 * */
	public int getRating() { return this.rating; }
	
	/**
	 * @return URL que contem a foto do usuario.
	 */
	public String getPhoto() { return this.photo; }
	
	/**
	 * @return eventos que esse usuario participou ou ira participar.
	 * */
	public Vector<Evento> getEvents() { return this.events; }
	
	/**
	 * @return amigos desse usuario.
	 * */
	public Vector<Usuario> getFriends() { return this.friends; } 
}

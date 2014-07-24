package br.com.JoinAndPlay.Server;

import java.util.Vector;

public class Usuario {
	private String name;
	private int rating;
	private String photo;
	private Vector<Evento> events;
	private Vector<Usuario> friends;
	
	public Usuario(String name, int rating, String photo, Vector<Evento> events, Vector<Usuario> friends) {
		this.name = name;
		this.rating = rating;
		this.photo = photo;
		this.events = events;
		this.friends = friends;
	}
	
	/**
	 * @return o nome do usu�rio.
	 * */
	public String getName() { return this.name; }
	
	/**
	 * @return o rating do usu�rio.
	 * */
	public int getRating() { return this.rating; }
	
	/**
	 * @return URL que cont�m a foto do usu�rio.
	 */
	public String getPhoto() { return this.photo; }
	
	/**
	 * @return eventos que esse usu�rio participou ou ir� participar.
	 * */
	public Vector<Evento> getEvents() { return this.events; }
	
	/**
	 * @return amigos desse usu�rio.
	 * */
	public Vector<Usuario> getFriends() { return this.friends; }
}

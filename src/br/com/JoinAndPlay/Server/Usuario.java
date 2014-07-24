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
	 * @return o nome do usuário.
	 * */
	public String getName() { return this.name; }
	
	/**
	 * @return o rating do usuário.
	 * */
	public int getRating() { return this.rating; }
	
	/**
	 * @return URL que contém a foto do usuário.
	 */
	public String getPhoto() { return this.photo; }
	
	/**
	 * @return eventos que esse usuário participou ou irá participar.
	 * */
	public Vector<Evento> getEvents() { return this.events; }
	
	/**
	 * @return amigos desse usuário.
	 * */
	public Vector<Usuario> getFriends() { return this.friends; }
}

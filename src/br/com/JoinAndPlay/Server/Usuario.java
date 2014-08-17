package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Serializable,Parcelable {
	private static final long serialVersionUID = 8250002358792274323L;
	
	private String id;
	private String name;
	private String rating;
	private String photo;
	private Vector<Evento> events;
	private int num_friends;
	private Vector<RatingSport> rating_sport;
	private Vector<Tag> tags;
	private Vector<Esporte> times_sport;
	private boolean has_notification;
	
	public Usuario(String id, String name, String rating, String photo, Vector<Evento> events, int num_friends,
				   Vector<RatingSport> rating_sport, Vector<Tag> tags, Vector<Esporte> times_sport, boolean has_notification) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.photo = photo;
		this.events = events;
		this.num_friends = num_friends;
		this.rating_sport = rating_sport;
		this.tags = tags;
		this.times_sport = times_sport;
		this.has_notification = has_notification;
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
	public String getRating() { return this.rating; }
	
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
	public int getNumFriends() { return this.num_friends; }
	
	/**
	 * @return rating de cada esporte.
	 * */
	public Vector<RatingSport> getRateSport() { return this.rating_sport; }
	
	/**
	 * @return tags com as avaliacoes.
	 * */
	public Vector<Tag> getTags() { return this.tags; }
	
	/**
	 * @return quantas vezes jogou cada esporte.
	 * */
	public Vector<Esporte> getTimesSport() { return this.times_sport; }
	
	/**
	 * @return true se tiver notificacao, false se nao tiver.
	 * */
	public boolean getHasNotification() { return this.has_notification; }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Usuario(Parcel in){
		id=in.readString();
		name=in.readString();
		photo=in.readString();
		
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(photo);
		

		
		
	}
}

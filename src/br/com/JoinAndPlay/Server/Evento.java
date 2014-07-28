package br.com.JoinAndPlay.Server;

import java.io.Serializable;
import java.util.Vector;

public class Evento implements Serializable {
	private static final long serialVersionUID = -1738172920493345010L;
	
	private String name;
	private Vector<Usuario> users;
	private String localization_name;
	private String localization_address;
	private String sport;
	private int num_friends;
	private String date;
	private String start_time;
	private String end_time;
	private String description;
	private Vector<Comentario> comments;
	private String id;
	private boolean is_private;
	private int price;
	private String city;
	private String neighbourhood;

	public Evento(String name, Vector<Usuario> users, String localization_name,
		String localization_address, String sport, int num_friends, String date,
		String start_time, String end_time, String description, Vector<Comentario> comments,
		String id, boolean is_private, int price, String city, String neighbourhood) {
		this.name = name;
		this.users = users;
		this.localization_name = localization_name;
		this.localization_address = localization_address;
		this.sport = sport;
		this.num_friends = num_friends;
		this.date = date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.description = description;
		this.comments = comments;
		this.id = id;
		this.is_private = is_private;
		this.price = price;
		this.city = city;
		this.neighbourhood = neighbourhood;
	}

	/**
	* @return o nome do evento
	*/
	public String getName() { return this.name; }

	/**
	* @return os usuarios que disseram que iriam.
	*/
	public Vector<Usuario> getUsers() { return this.users; }

	/**
	* @return o nome do local que sera o evento.
	*/
	public String getLocalizationName() { return this.localization_name; }

	/**
	* @return o endereço que sera o evento.
	*/
	public String getLocalizationAddress() { return this.localization_address; }

	/**
	* @return o nome do esporte do evento.
	*/
	public String getSport() { return this.sport; }

	/**
	* @return o numero de amigos nesse evento.
	*/
	public int getNumFriends() { return this.num_friends; }

	/**
	* @return a data desse evento
	*/
	public String getDate() { return this.date; }

	/**
	* @return a hora que esse evento começa.
	*/
	public String getStartTime() { return this.start_time; }

	/**
	* @return a hora que esse evento acaba.
	*/
	public String getEndTime() { return this.end_time; }

	/**
	* @return descricao desse evento.
	*/
	public String getDescription() { return this.description; }

	/**
	* @return comentarios desse evento.
	*/
	public Vector<Comentario> getComments() { return this.comments; }

	/**
	* @return id do evento.
	*/
	public String getId() { return this.id; }

	/**
	* @return true se o evento for privado, false se for publico.
	*/
	public boolean getPrivacy() { return this.is_private; }
	
	/**
	 * @return o preco desse evento em centavos.
	 * */
	public int getPrice() { return this.price; }
	
	/**
	 * @return o nome da cidade;
	 * */
	public String getCity() { return this.city; }
	
	/**
	 * @return o nome do bairro.
	 * */
	public String getNeighbourhood() { return this.neighbourhood; }
} 

package br.com.JoinAndPlay.Server;

import java.io.Serializable;

public class Local implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 798175189606793514L;
	private String localization_name;
	private String localization_address;
	private String neighbourhood;
	private String telefone;
	private String city;
	private int price;
	private String id;
	
	public Local(String localization_name, String localization_address, String neighbourhood,
			String city, int price, String id, String telefone){
		this.localization_name = localization_name;
		this.localization_address = localization_address;
		this.city = city;
		this.telefone=telefone;
		this.price = price;
		this.id = id;
		this.neighbourhood = neighbourhood;
	}
	public String getLocalization_name() {
		return localization_name;
	}

	public String getLocalization_address() {
		return localization_address;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}


	public String getCity() {
		return city;
	}

	public int getPrice() {
		return price;
	}


	public String getId() {
		return id;
	}
	public String getTelefone() {
		return telefone;
	}
	
}
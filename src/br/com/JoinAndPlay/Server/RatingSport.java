package br.com.JoinAndPlay.Server;

public class RatingSport {
	private String sport_name;
	private String rating;
	
	public RatingSport(String sport_name, String rating) {
		this.sport_name = sport_name;
		this.rating = rating;
	}
	
	/**
	 * @return o nome desse esporte.
	 * */
	public String getSportName() { return this.sport_name; }
	
	/**
	 * @return o rating do usuario nesse esporte.
	 * */
	public String getRating() { return this.rating; }
}

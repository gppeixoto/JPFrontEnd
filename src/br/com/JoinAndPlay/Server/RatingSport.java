package br.com.JoinAndPlay.Server;

public class RatingSport {
	private String sport_name;
	private String rating;
	private int qnt_games;
	private int num_voters;
	private boolean voted;
	
	public RatingSport(String sport_name, String rating, int qnt_games, int num_voters) {
		this.sport_name = sport_name;
		this.rating = rating;
		this.qnt_games = qnt_games;
		this.num_voters = num_voters;
	}
	
	public RatingSport(String sport_name, String rating, int qnt_games, int num_voters, boolean voted) {
		this(sport_name, rating, qnt_games, num_voters);
		this.voted = voted;
	} 
	
	/**
	 * @return o nome desse esporte.
	 * */
	public String getSportName() { return this.sport_name; }
	
	/**
	 * @return o rating do usuario nesse esporte.
	 * */
	public String getRating() { return this.rating; }
	
	/**
	 * @return a quantidade de vezes que o usuario jogou esse esporte.
	 * */
	public int getQntGames() { return this.qnt_games; }
	
	/**
	 * @return a quantidade de usuarios que votaram nesse esporte.
	 * */
	public int getNumVoters() { return this.num_voters; }
	
	public boolean getVoted() { return this.voted; }
}

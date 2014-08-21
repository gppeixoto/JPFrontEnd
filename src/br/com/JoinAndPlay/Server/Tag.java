package br.com.JoinAndPlay.Server;

public class Tag {
	private String name;
	private int num_votes;
	private boolean voted;
	
	public Tag(String name, int num_votes) {
		this.name = name;
		this.num_votes = num_votes;
	}
	
	public Tag(String name, int num_votes, boolean voted) {
		this(name, num_votes);
		this.voted = voted;
	}
	
	/**
	 * @return o nome dessa tag.
	 * */
	public String getName() { return this.name; }
	
	/**
	 * @return o numero de votos que o usuario recebeu nessa tag.
	 * */
	public int getNumVotes() { return this.num_votes; }
	
	public boolean getVoted() { return this.voted; }
}

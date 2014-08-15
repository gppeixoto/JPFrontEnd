package br.com.JoinAndPlay.Server;

public class Esporte {
	private String name;
	private int num_times;
	
	public Esporte(String name, int num_times) {
		this.name = name;
		this.num_times = num_times;
	}
	
	/**
	 * @return o nome do esporte.
	 * */
	public String getName() { return this.name; }
	
	/**
	 * @return a quantidade de vezes que o usuario jogou esse esporte.
	 * */
	public int getNumTimes() { return this.num_times; }
}

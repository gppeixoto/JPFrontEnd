package br.com.JoinAndPlay.Server;

public class Notificacao {
	private String creator;
	private String event_name;
	private String event_id;
	private String begin;
	private String date;
	public String esporte;
	private boolean privado;
	
	public Notificacao(String creator, String event_name, String event_id, String begin, String date, boolean privado) {
		this.creator = creator;
		this.event_name = event_name;
		this.event_id = event_id;
		this.begin = begin;
		this.date = date;
		this.privado = privado;
	}

	public String getCreatorName() { return this.creator; }
	public String getEventName() { return this.event_name; }
	public String getEventId() { return this.event_id; }
	public String getHourBegin() { return this.begin; }
	public String getDate() { return this.date; }
	public boolean getPrivacy() { return this.privado; }
}

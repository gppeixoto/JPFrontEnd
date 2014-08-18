package br.com.JoinAndPlay.Server;

public class Notificacao {
	private String creator;
	private String event_name;
	private String event_id;
	private String begin;
	private String date;
	private boolean privado;
	private String creator_photo;
	private String localization_name;
	private String neighborhood;
	
	public Notificacao(String creator, String event_name, String event_id, String begin, String date, boolean privado,
			String creator_photo, String local_name, String neighborhood) {
		this.creator = creator;
		this.event_name = event_name;
		this.event_id = event_id;
		this.begin = begin;
		this.date = date;
		this.privado = privado;
		this.creator_photo = creator_photo;
		this.localization_name = local_name;
		this.neighborhood = neighborhood;
	}

	public String getCreatorName() { return this.creator; }
	public String getEventName() { return this.event_name; }
	public String getEventId() { return this.event_id; }
	public String getHourBegin() { return this.begin; }
	public String getDate() { return this.date; }
	public boolean getPrivacy() { return this.privado; }
	public String getCreatorPhoto() { return this.creator_photo; }
	public String getLocalizationName() { return this.localization_name; }
	public String getNeighborhood() { return this.neighborhood; }
}

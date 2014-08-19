package br.com.JoinAndPlay.Server;

import android.os.Parcel;
import android.os.Parcelable;

public class Endereco implements Parcelable{
	
	private String name;
	private String endereco;
	
	public Endereco(Parcel in){
		name =in.readString();
		endereco =in.readString();
	}
	
	public Endereco(String nome, String endereco) {
		this.name = nome; this.endereco = endereco;
	}
	
	public String getName() { return this.name; }
	public String getAddress() { return this.endereco; }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeString(name);
		arg0.writeString(endereco);
			
	}
}
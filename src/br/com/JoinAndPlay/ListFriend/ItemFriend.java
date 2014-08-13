package br.com.JoinAndPlay.ListFriend;

import br.com.JoinAndPlay.Server.Usuario;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemFriend implements Parcelable {
	
	public String nome;
	public String photo;
	public String id;
	public Usuario usuario;
	
	public ItemFriend(Parcel in){
		nome = in.readString();
		photo=in.readString();
		id=in.readString();
		usuario=(Usuario)in.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(nome);
		arg0.writeString(id);
		arg0.writeString(photo);
		arg0.writeSerializable(usuario);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
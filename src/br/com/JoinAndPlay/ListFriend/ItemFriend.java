package br.com.JoinAndPlay.ListFriend;

import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemFriend implements Parcelable {
	
	public String nome;
	public String photo;
	public String id;
	public Usuario usuario;
	public boolean select;
	private ImageView fotoView;
	private TextView nameView;
	private LinearLayout layout;
	
	public ItemFriend(){
		select=false;
	}
	
	public ItemFriend(Usuario u){
		select=false;
		id=u.getId();
		nome=u.getName();
		photo=u.getPhoto();
	}
	
	
	public ItemFriend(Parcel in){
		nome = in.readString();
		photo=in.readString();
		select = false;
		id=in.readString();
		usuario=(Usuario)in.readSerializable();
	}
	
	public boolean getSelected(){
		return select;
	}
	
	public void selected(){
		select=true;
		layout.setBackgroundResource(R.drawable.campo_cinza);
	}
	
	public void deselected(){
		select=false;
		layout.setBackgroundResource(R.drawable.campo_branco);
	}
	
	public void drawerView(View view){
		if(view==null) return;
		
		fotoView = (ImageView) view.findViewById(R.id.photo_friend);
		nameView = (TextView) view.findViewById(R.id.name_friend);
		layout = (LinearLayout) view.findViewById(R.id.layout_friend);
	
		if(nome!=null){
			nameView.setText(nome);
		}
		
		if(photo!=null){
			new DownloadImagemAsyncTask(view.getContext(),fotoView).execute(photo);
		}
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
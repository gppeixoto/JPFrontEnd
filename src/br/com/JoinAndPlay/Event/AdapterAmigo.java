package br.com.JoinAndPlay.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListFriend.AdapterGridViewFriend;
import br.com.JoinAndPlay.ListFriend.ItemFriend;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;

import com.facebook.Session;

public class AdapterAmigo extends BaseAdapter{
	private ArrayList<Usuario> vetor;
	private LayoutInflater inflater;
	private boolean[] selector;
	public AdapterAmigo(ArrayList<Usuario> vetor,LayoutInflater inflater, boolean[] selector){
		this.vetor=vetor;
		this.inflater=inflater;
		this.selector=selector;

	}
	public  void draw(View v,int i){
		if(selector!=null){
			if (!selector[i]){
				v.setBackgroundResource(R.drawable.campo_branco);


			}else{

				v.setBackgroundResource(R.drawable.campo_red);

			}
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vetor.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return vetor.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return vetor.get(arg0).hashCode();
	}

	@Override
	public View getView(int i, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(view==null)
			view = inflater.inflate(R.layout.item_amigos, arg2, false);
		Usuario user = vetor.get(i);

		ImageView imag = (ImageView)view.findViewById(R.id.item_amigos_foto);
		TextView texto = (TextView)view.findViewById(R.id.item_amigos_texto);
		ViewGroup bad = (ViewGroup)view.findViewById(R.id.item_amigos_badges);
		DownloadImagem.postLoad(imag, user.getPhoto());
		texto.setText(user.getName());
		boolean[] b = new boolean[4];
		for (int j = 0; j < b.length; j++) {
			b[j]=false;
		}
		if(user.getTags()!=null)
			for (Iterator<Tag> iterator = user.getTags().iterator(); iterator.hasNext();) {
				Tag type = (Tag) iterator.next();
				String NOME = type.getName();
				if (NOME.equals("Gente Boa")) b[0]=true;
				else if (NOME.equals("Fair Play")) b[3]=true;							
				else if (NOME.equals("Esforcado")) b[1]=true;
				else if (NOME.equals("Joga Pro Time")) b[2]=true;
			}

		for (int j = 0; j < b.length; j++) {
			if(b[j]==false){
				bad.getChildAt(j).setVisibility(View.INVISIBLE);

			}
		}
		draw(view, i);
		return view;
	}
}



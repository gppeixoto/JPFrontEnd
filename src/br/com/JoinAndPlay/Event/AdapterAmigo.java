package br.com.JoinAndPlay.Event;

import java.util.ArrayList;
import java.util.Iterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;

public class AdapterAmigo extends BaseAdapter{
	private ArrayList<Usuario> vetor;
	private LayoutInflater inflater;
	private boolean[] selector;

	public AdapterAmigo(ArrayList<Usuario> vetor,LayoutInflater inflater, boolean[] selector){
		this.vetor=vetor;
		int[] vet ={0,0,0,0,0};
		int[] ordenado;
		Usuario[] vetor2;
		if(vetor!=null && vetor.size()>0){
			ordenado= new int[vetor.size()];
			vetor2 = new Usuario[vetor.size()];
			for (int i = 0; i < ordenado.length; i++) {
				ordenado[i]=0;

				Usuario user = (Usuario) vetor.get(i);
				vetor2[i]=user;

				if(user.getTags()==null){
					vet[0]++;}else
						if(user.getTags().size()<5)
							vet[user.getTags().size()]++;
						else
							vet[4]++;
			}
			for (int i = 1; i < vet.length; i++) {
				vet[i]+=vet[i-1];
			}
			for (int i = 0; i < ordenado.length; i++) {
				Usuario user = (Usuario) vetor.get(i);
				int id=user.getTags()==null?0:( user.getTags().size()<5?user.getTags().size():4);
				ordenado[i]= ordenado.length-vet[id];
				vet[id]--;
			}
			for (int i = 0; i < ordenado.length; i++) {
				vetor.set(ordenado[i], vetor2[i]);

			}
		}
		this.inflater=inflater;
		this.selector=selector;
	}
	public  void draw(View v,int i){

		if(selector!=null){
			if (!selector[i]){			
				v.setBackgroundResource(R.drawable.campo_cinza_vote);
			}else{
				v.setBackgroundResource(R.drawable.campo_branco);
			}
		}
	}
	@Override
	public int getCount() {
		return vetor.size();
	}

	@Override
	public Object getItem(int arg0) {
		return vetor.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return vetor.get(arg0).hashCode();
	}

	@Override
	public View getView(int i, View view, ViewGroup arg2) {


		if(view==null)
			view = inflater.inflate(R.layout.item_amigos, arg2, false);
		Usuario user = vetor.get(i);

		ImageView imag = (ImageView)view.findViewById(R.id.item_amigos_foto);
		TextView texto = (TextView)view.findViewById(R.id.item_amigos_texto);
		ViewGroup bad = (ViewGroup)view.findViewById(R.id.item_amigos_badges);
		imag.setVisibility(View.INVISIBLE);		
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

			}else{
				bad.getChildAt(j).setVisibility(View.VISIBLE);

			}
		}
		draw(view, i);
		return view;
	}
}



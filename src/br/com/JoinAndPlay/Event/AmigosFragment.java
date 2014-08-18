package br.com.JoinAndPlay.Event;

import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.chrono.BuddhistChronology;

import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.PerfilUserFragment;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

class AdapterAmigo extends BaseAdapter{
	private ArrayList<Usuario> vetor;
	private LayoutInflater inflater;
	public AdapterAmigo(ArrayList<Usuario> vetor,LayoutInflater inflater){
		this.vetor=vetor;
		this.inflater=inflater;

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
		return view;
	}

}
public class AmigosFragment extends Fragment implements OnItemClickListener {
	LayoutInflater inflater;
	ListView listV;
	private ArrayList<Usuario> vetor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

		this.inflater=inflater;
		ViewGroup tela=(ViewGroup)inflater.inflate(R.layout.fragment_list_event,container,false) ;

		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		vetor=null;
		if(getArguments()!=null){
			vetor= getArguments().getParcelableArrayList("users");

		}
		Log.v("usuario", ""+vetor);
		if(vetor!=null)
			listV.setAdapter(new AdapterAmigo(vetor, inflater));

		Button Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setVisibility(View.INVISIBLE);
		tela.removeView(Button_criar);

		return  tela;


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
		PerfilUserFragment fm = new  PerfilUserFragment();
		Bundle arg = new Bundle();
		arg.putString("idUser",vetor.get(arg2-1).getId());
		fm.setArguments(arg);
		((MainActivity)getActivity()).mudarAbaAtual(fm);
	}

}

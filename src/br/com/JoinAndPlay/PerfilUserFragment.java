package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.ItemEsportePerfil.MyGridView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.RatingSport;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PerfilUserFragment extends Fragment implements Connecter<Usuario>{
	RelativeLayout ret;

	//ola
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		ret=(RelativeLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);
		
		

		Server.user_profile(getActivity(), this);

		// Inflamos o layout tab_layout_a
		return ret;
	}
	@Override
	public void onTerminado(Usuario in) {
		// TODO Auto-generated method stub
		View ret=getView();
		ImageView imag;
		TextView nome;
		TextView idade;
		TextView votos_esforcado;
		GridView gridView;

		
		
	
		if(in!=null && ret !=null){
			ArrayList<ItemEsporte> lista = new ArrayList<ItemEsporte>();

			imag=(ImageView)ret.findViewById(R.id.profilePictureView1);
			nome=(TextView)ret.findViewById(R.id.perfil_idade_usuario);
			idade=(TextView)ret.findViewById(R.id.perfil_nome_usuario);
			gridView=((GridView)ret.findViewById(R.id.myGridView1));

			nome.setText(in.getName());
			idade.setText("");
			
			new DownloadImagemAsyncTask(getActivity(),imag).execute(in.getPhoto());
			
			for (Iterator<RatingSport> iterator = in.getRateSport().iterator(); iterator.hasNext();) {
				RatingSport rating = (RatingSport) iterator.next();
				ItemEsporte itemEsport = new ItemEsporte();
				//itemEsport.avaliacaoJogador=rating.getRating();
				itemEsport.esporte=rating.getSportName();
			//	itemEsport.partidasJogadas=rating.
				lista.add(itemEsport);
				
			}
			gridView.setAdapter(new AdapterGridView(getActivity(),lista));

		}

	}


}

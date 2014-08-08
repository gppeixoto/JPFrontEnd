package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.ItemEsportePerfil.MyGridView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Esporte;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		ret=(RelativeLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);		
		/*Requisita o perfil do usu�rio do servidor*/
		Server.user_profile(getActivity(), this);
		return ret;
	}
	
	@Override
	public void onTerminado(Usuario in) {
		// TODO Auto-generated method stub
		View ret=getView();
		ImageView perfil_Foto;
		TextView perfil_Nome;
		TextView perfil_Idade;
		TextView votos_esforcado;
		GridView perfil_gridEsportes;

		if(in!=null && ret !=null){
			ArrayList<ItemEsporte> gridEsportes = new ArrayList<ItemEsporte>();

			perfil_Foto=(ImageView)ret.findViewById(R.id.profilePictureView1);
			perfil_Nome=(TextView)ret.findViewById(R.id.perfil_nome_usuario);
			//perfil_Idade=(TextView)ret.findViewById(R.id.perfil_nome_usuario);
			//perfil_gridEsportes=((GridView)ret.findViewById(R.id.myGridView1));
			
			/*Pega o nome do perfil do servidor*/
			perfil_Nome.setText(in.getName());
			/*Pega a foto do perfil do servidor*/
			new DownloadImagemAsyncTask(getActivity(),perfil_Foto).execute(in.getPhoto());
			
			/*Pegar as informa��es relativas a cada esporte do usu�rio*/
			/*for (Iterator<RatingSport> iterator = in.getRateSport().iterator(); iterator.hasNext();) {
				RatingSport rating = (RatingSport) iterator.next();
				ItemEsporte itemEsport = new ItemEsporte();
				//Pega o numero de estrelas do esporte
				itemEsport.avaliacaoJogador = Double.parseDouble(rating.getRating());
				//Pega o nome do esporte
				itemEsport.esporte = rating.getSportName();
				int partidasJogadas = 0;
				//Pega o numero de partidas do esporte (em O(n�), mas pega)
				for (Iterator<Esporte> it = in.getTimesSport().iterator(); it.hasNext(); ){
					Esporte num = (Esporte) it.next();
					if (num.getName().equals(itemEsport.esporte)){partidasJogadas = num.getNumTimes(); break;}
				}
				itemEsport.partidasJogadas = partidasJogadas;
				gridEsportes.add(itemEsport);
				
			}
			perfil_gridEsportes.setAdapter(new AdapterGridView(getActivity(),gridEsportes));
			*/
		}
	}
}

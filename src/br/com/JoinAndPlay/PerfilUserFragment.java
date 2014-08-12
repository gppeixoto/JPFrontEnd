package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.ItemEsportePerfil.MyGridView;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Esporte;
import br.com.JoinAndPlay.Server.RatingSport;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import android.content.res.Configuration;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PerfilUserFragment extends Fragment implements Connecter<Usuario>{
	private LinearLayout v;
	private ImageView img_genteBoa;
	private ImageView img_fairPlay;
	private ImageView img_jogaTime;
	private ImageView img_esforcado;
	private Configuration config;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = (LinearLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);		
		/*Requisita o perfil do usuário do servidor*/
		Server.user_profile(getActivity(), this);
		config = getActivity().getResources().getConfiguration();
		
		/*Celular com resoluções mais baixas
		 * muda a resolução para mdpi*/
		if (!((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) > 1)){
			img_genteBoa = (ImageView) v.findViewById(R.id.perfil_imageview_genteboa);
			img_fairPlay = (ImageView) v.findViewById(R.id.perfil_imageview_fairplay);
			img_jogaTime = (ImageView) v.findViewById(R.id.perfil_imageview_jogatime);
			img_esforcado = (ImageView) v.findViewById(R.id.perfil_imageview_esforcado);
			img_genteBoa.setImageResource(R.drawable.perfil_imageview_genteboa_mdpi);
			img_esforcado.setImageResource(R.drawable.perfil_imageview_esforcado_mdpi);
			img_jogaTime.setImageResource(R.drawable.perfil_imageview_jogatime_mdpi);
			img_fairPlay.setImageResource(R.drawable.perfil_imageview_fairplay_mdpi);
		}
		return v;
	}
	
	@Override
	public void onTerminado(final Usuario in) {
		final View ret=getView();
		ret.post(new Runnable() {
			
			@Override
			public void run() {
				ImageView perfil_Foto;
				TextView perfil_Nome;
				TextView votos_esforcado;
				TextView votos_genteBoa;
				TextView votos_fairPlay;
				TextView votos_jogaTime;
				Button button_amigos;
				GridView perfil_gridEsportes;

				if(in!=null && ret !=null){
					//ArrayList<ItemEsporte> gridEsportes = new ArrayList<ItemEsporte>();

					perfil_Foto=(ImageView)ret.findViewById(R.id.profilePictureView1);
					perfil_Nome=(TextView)ret.findViewById(R.id.perfil_nome_usuario);
					
					perfil_gridEsportes=((GridView)ret.findViewById(R.id.perfil_gridview_esportes));
					
					/*Pega o nome do perfil do servidor*/
					perfil_Nome.setText(in.getName());
					/*Pega a foto do perfil do servidor*/
					new DownloadImagemAsyncTask(getActivity(),perfil_Foto).execute(in.getPhoto());
					
					/*Pega número de amigos*/
					button_amigos = (Button) ret.findViewById(R.id.perfil_button_amigos);
					button_amigos.setText(in.getNumFriends() + " Amigos");
					
					/*Pegar o número de votos de cada tag do servidor*/
					votos_esforcado = (TextView) ret.findViewById(R.id.perfil_textview_votos_esforcado);
					votos_jogaTime = (TextView) ret.findViewById(R.id.perfil_textview_jogatime);
					votos_fairPlay = (TextView) ret.findViewById(R.id.perfil_textview_votos_fairplay);
					votos_genteBoa = (TextView) ret.findViewById(R.id.perfil_textview_votos_gente_boa);
					
					Vector<Tag> comendacoes = in.getTags();
					//DEBUG
					//Log.v("VECSIZE", in.getTags().size()+"");
					for (Tag tag : comendacoes){
						String NOME = tag.getName();
						if (NOME.equals("Gente Boa")){
							votos_genteBoa.setText(tag.getNumVotes());
						} else if (NOME.equals("Fair Play")){
							votos_fairPlay.setText(tag.getNumVotes());							
						} else if (NOME.equals("Esforçado")){
							votos_esforcado.setText(tag.getNumVotes());
						} else {
							votos_jogaTime.setText(tag.getNumVotes());
						}
					}
					
					/*Pegar as informações relativas a cada esporte do usuário*/
					ArrayList<ItemEsporte> listaEsportes = new ArrayList<ItemEsporte>();
					for (Iterator<RatingSport> iterator = in.getRateSport().iterator(); iterator.hasNext();) {
						ItemEsporte itemEsporte = new ItemEsporte();
						RatingSport rating = (RatingSport) iterator.next();
						//Pega o numero de estrelas do esporte
						itemEsporte.avaliacaoJogador = Double.parseDouble(rating.getRating());
						//Pega o nome do esporte
						itemEsporte.esporte = rating.getSportName();
						int partidasJogadas = 0;
						//Pega o numero de partidas do esporte (em O(n²), mas pega)
						for (Iterator<Esporte> it = in.getTimesSport().iterator(); it.hasNext(); ){
							Esporte num = (Esporte) it.next();
							if (num.getName().equals(itemEsporte.esporte)){partidasJogadas = num.getNumTimes(); break;}
						}
						itemEsporte.partidasJogadas = partidasJogadas;
						listaEsportes.add(itemEsporte);
					}
					perfil_gridEsportes.setAdapter(new AdapterGridView(getActivity(),listaEsportes));
					
				}
			}
		});
	}
}

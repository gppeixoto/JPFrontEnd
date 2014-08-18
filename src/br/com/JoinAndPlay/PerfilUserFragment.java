package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import br.com.JoinAndPlay.Event.AmigosFragment;
import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Esporte;
import br.com.JoinAndPlay.Server.RatingSport;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
		/*Requisita o perfil do usu�rio do servidor*/
		if(null!=getArguments() && getArguments().containsKey("idUser")){
			Server.user_profile_id(getArguments().getString("idUser"),getActivity(), this);
		}else{
			Server.user_profile(getActivity(), this);
		}
		config = getActivity().getResources().getConfiguration();

		/*Celular com resolu��es mais baixas
		 * muda a resolu��o para mdpi*/
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
		final PerfilUserFragment self = this;
		if (ret == null ) return;
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
				ExpandableHeightGridView perfil_gridEsportes_Expandable;

				if(in!=null && ret !=null){
					//ArrayList<ItemEsporte> gridEsportes = new ArrayList<ItemEsporte>();

					perfil_Foto=(ImageView)ret.findViewById(R.id.profilePictureView1);
					perfil_Nome=(TextView)ret.findViewById(R.id.perfil_nome_usuario);

					//perfil_gridEsportes=((GridView)ret.findViewById(R.id.perfil_gridview_esportes));
					perfil_gridEsportes_Expandable = (ExpandableHeightGridView) ret.findViewById(R.id.perfil_gridview_esportes_expandable);

					/*Pega o nome do perfil do servidor*/
					perfil_Nome.setText(in.getName());
					/*Pega a foto do perfil do servidor*/
					DownloadImagem.postLoad(perfil_Foto, in.getPhoto());

					/*Pega n�mero de amigos*/
					button_amigos = (Button) ret.findViewById(R.id.perfil_button_amigos);
					button_amigos.setText(in.getNumFriends() + " Amigos");
					button_amigos.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Server.get_friends(self.getActivity(),new Connecter<Vector<Usuario>>() {

								@Override
								public void onTerminado(Vector<Usuario> in) {
									if(in==null) return;
									AmigosFragment fm = new AmigosFragment();
									Bundle arg = new Bundle();
									ArrayList<Usuario> array=new ArrayList<Usuario>();
									for (Iterator<Usuario> iterator = in.iterator(); iterator
											.hasNext();) {
										Usuario usuario = (Usuario) iterator
												.next();
										array.add(usuario);

									}
									arg.putParcelableArrayList("users",array);
									fm.setArguments(arg);
									((MainActivity)self.getActivity()).mudarAbaAtual(fm);

								}
							});
						}
					});
					
					Button button = (Button) ret.findViewById(R.id.perfil_button_eventosAnteriores);
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							AgendaEventosFragment agenda = new AgendaEventosFragment();
							Bundle arg = new Bundle();
							arg.putString("idUser",in.getId() );
							agenda.setArguments(arg);
							((MainActivity)self.getActivity()).mudarAbaAtual(agenda);
							
						}
					});

					/*Pegar o n�mero de votos de cada tag do servidor*/
					votos_esforcado = (TextView) ret.findViewById(R.id.perfil_textview_votos_esforcado);
					votos_jogaTime = (TextView) ret.findViewById(R.id.perfil_textview_jogatime);
					votos_fairPlay = (TextView) ret.findViewById(R.id.perfil_textview_votos_fairplay);
					votos_genteBoa = (TextView) ret.findViewById(R.id.perfil_textview_votos_gente_boa);

					Vector<Tag> comendacoes = in.getTags();
					for (Tag tag : comendacoes){
						String NOME = tag.getName();
						if (NOME.equals("Gente Boa")){
							votos_genteBoa.setText(tag.getNumVotes());
						} else if (NOME.equals("Fair Play")){
							votos_fairPlay.setText(tag.getNumVotes());							
						} else if (NOME.equals("Esfor�ado")){
							votos_esforcado.setText(tag.getNumVotes());
						} else {
							votos_jogaTime.setText(tag.getNumVotes());
						}
					}

					/*Pegar as informa��es relativas a cada esporte do usu�rio*/
					ArrayList<ItemEsporte> listaEsportes = new ArrayList<ItemEsporte>();
					for (Iterator<RatingSport> iterator = in.getRateSport().iterator(); iterator.hasNext();) {
						ItemEsporte itemEsporte = new ItemEsporte();
						RatingSport rating = (RatingSport) iterator.next();
						//Pega o numero de estrelas do esporte
						itemEsporte.avaliacaoJogador = Double.parseDouble(rating.getRating());
						//Pega o nome do esporte
						itemEsporte.esporte = rating.getSportName();
						int partidasJogadas = 0;
						//Pega o numero de partidas do esporte (em O(n�), mas pega)
						for (Iterator<Esporte> it = in.getTimesSport().iterator(); it.hasNext(); ){
							Esporte num = (Esporte) it.next();
							if (num.getName().equals(itemEsporte.esporte)){partidasJogadas = num.getNumTimes(); break;}
						}
						itemEsporte.partidasJogadas = partidasJogadas;
						listaEsportes.add(itemEsporte);
					}
					perfil_gridEsportes_Expandable.setAdapter(new AdapterGridView(getActivity(),listaEsportes));
					perfil_gridEsportes_Expandable.setNumColumns(2);
					perfil_gridEsportes_Expandable.setExpanded(true);
				}
			}
		});
	}
}

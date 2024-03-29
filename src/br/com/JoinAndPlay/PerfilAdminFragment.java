package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import br.com.JoinAndPlay.Event.AmigosFragment;
import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.RatingSport;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PerfilAdminFragment extends Fragment implements Connecter<Usuario>{
	protected LinearLayout v;
	protected ImageView img_genteBoa;
	protected ImageView img_fairPlay;
	protected ImageView img_jogaTime;
	protected ImageView img_esforcado;
	protected Configuration config;
	protected int ID=4;
	String idUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		v = (LinearLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);		
		/*Requisita o perfil do usu�rio do servidor*/


		Server.user_profile(MainActivity.self, this);
		((MainActivity) MainActivity.self).loadTela(ID);

		config = MainActivity.self.getResources().getConfiguration();

		/*Celular com resolu��es mais baixas
		 * muda a resolu��o para mdpi*/
		img_genteBoa = (ImageView) v.findViewById(R.id.perfil_imageview_genteboa);
		img_fairPlay = (ImageView) v.findViewById(R.id.perfil_imageview_fairplay);
		img_jogaTime = (ImageView) v.findViewById(R.id.perfil_imageview_jogatime);
		img_esforcado = (ImageView) v.findViewById(R.id.perfil_imageview_esforcado);
	

		return v;
	}

	@Override
	public void onTerminado(final Usuario in) {
		final View ret=getView();
		final PerfilAdminFragment self = this;
		if (ret == null ){ 
			((MainActivity) MainActivity.self).popLoadTela(ID);
			return;
		}
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

							Server.get_friends(MainActivity.self,new Connecter<Vector<Usuario>>() {

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
									arg.putInt("idTab",ID);

									fm.setArguments(arg);
									((MainActivity)MainActivity.self).mudarAbaAtual(fm);

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
							arg.putInt("idTab",ID);
							arg.putBoolean("anteriores",true);

							agenda.setArguments(arg);
							((MainActivity)MainActivity.self).mudarAbaAtual(agenda);

						}
					});

					/*Pegar o n�mero de votos de cada tag do servidor*/
					votos_esforcado = (TextView) ret.findViewById(R.id.perfil_textview_votos_esforcado);
					votos_jogaTime = (TextView) ret.findViewById(R.id.perfil_textview_votos_jogatime);
					votos_fairPlay = (TextView) ret.findViewById(R.id.perfil_textview_votos_fairplay);
					votos_genteBoa = (TextView) ret.findViewById(R.id.perfil_textview_votos_gente_boa);
					Vector<Tag> comendacoes = in.getTags();
					for (Tag tag : comendacoes){
						String NOME = tag.getName();
						if (NOME.equals("Gente Boa")){
							votos_genteBoa.setText(tag.getNumVotes()+"");
						} else if (NOME.equals("Fair Play")){
							votos_fairPlay.setText(tag.getNumVotes()+"");							
						} else if (NOME.equals("Esforcado")){
							votos_esforcado.setText(tag.getNumVotes()+"");
						} else if (NOME.equals("Joga Pro Time")){
							votos_jogaTime.setText(tag.getNumVotes()+"");
						}
					}

					/*Pegar as informa��es relativas a cada esporte do usu�rio*/
					ArrayList<ItemEsporte> listaEsportes = new ArrayList<ItemEsporte>();
					for (Iterator<RatingSport> iterator = in.getRateSport().iterator(); iterator.hasNext();) {
						ItemEsporte itemEsporte = new ItemEsporte();
						RatingSport rating = (RatingSport) iterator.next();
						//Pega o numero de estrelas do esporte
						//Pega o nome do esporte
						itemEsporte.esporte = rating.getSportName();
						itemEsporte.partidasJogadas = rating.getQntGames();
						itemEsporte.numeroVotos= rating.getNumVoters();
						itemEsporte.avaliacaoJogador=Double.parseDouble(rating.getRating());
						listaEsportes.add(itemEsporte);
					}
					perfil_gridEsportes_Expandable.setAdapter(new AdapterGridView(MainActivity.self,listaEsportes));
					perfil_gridEsportes_Expandable.setNumColumns(2);
					perfil_gridEsportes_Expandable.setExpanded(true);
					((MainActivity) MainActivity.self).popLoadTela(ID);
				}else{
					Bundle args = new Bundle();
					MainActivity.self.popLoadTela(ID);
					args.putBoolean("internet",false);
					BolaForaFragment bfm = new BolaForaFragment();
					bfm.setArguments(args);
					MainActivity.self.mudarAba(ID,bfm);
				}
			}
		});
	}
}

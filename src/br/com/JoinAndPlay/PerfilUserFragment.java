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

public class PerfilUserFragment extends PerfilAdminFragment {
	private LinearLayout v;
	private ImageView img_genteBoa;
	private ImageView img_fairPlay;
	private ImageView img_jogaTime;
	private ImageView img_esforcado;
	private Configuration config;
	String idUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		v = (LinearLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);		
		/*Requisita o perfil do usu�rio do servidor*/


		if(null!=getArguments() && getArguments().containsKey("idUser")){
			ID=getArguments().getInt("idTab");
			idUser=getArguments().getString("idUser");
			Server.user_profile_id(idUser,MainActivity.self, this);
			((MainActivity) MainActivity.self).loadTela(ID);
		}
		config = MainActivity.self.getResources().getConfiguration();

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


		Button button_amigos = (Button) v.findViewById(R.id.perfil_button_amigos);
		Button button = (Button) v.findViewById(R.id.perfil_button_eventosAnteriores);
		button.setVisibility(View.INVISIBLE);
		button_amigos.setVisibility(View.INVISIBLE);

		return v;
	}

	@Override
	public void onTerminado(final Usuario in) {
		super.onTerminado(in);
	}
}

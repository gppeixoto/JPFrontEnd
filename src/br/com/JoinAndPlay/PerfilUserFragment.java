package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.doomonafireball.betterpickers.recurrencepicker.LinearLayoutWithMaxWidth;

import br.com.JoinAndPlay.Event.AmigosFragment;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ItemEsportePerfil.AdapterGridView;
import br.com.JoinAndPlay.ItemEsportePerfil.ItemEsporte;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.RatingSport;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
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
	private Button button_amigos;
	private Configuration config;
	private boolean award[] = {false,false,false,false};
	private String badges[]=null;
	private boolean cantVote[] = {false,false,false,false};
	private String namebadges[];
	String idUser;
	
	public static void alerta(Button button_amigos2,int value){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(button_amigos2.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder1.setCancelable(true);
		builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}});

		builder1.setView(MainActivity.self.getLayoutInflater().inflate(value, null));
		AlertDialog alert11 = builder1.create();

		OnShowListener onshow = new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog)
						.getButton(AlertDialog.BUTTON_POSITIVE);
				positiveButton.setBackgroundResource(R.drawable.alert_button);
				positiveButton.setText("OK");
				positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);
			}
		};
		alert11.setOnShowListener(onshow);
		alert11.show();
	}

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
			badges=getArguments().getStringArray("badges");
		}
		config = MainActivity.self.getResources().getConfiguration();

		img_genteBoa = (ImageView) v.findViewById(R.id.perfil_imageview_genteboa);
		img_fairPlay = (ImageView) v.findViewById(R.id.perfil_imageview_fairplay);
		img_jogaTime = (ImageView) v.findViewById(R.id.perfil_imageview_jogatime);
		img_esforcado = (ImageView) v.findViewById(R.id.perfil_imageview_esforcado);
		/*Celular com resolu��es mais baixas
		 * muda a resolu��o para mdpi*/
		if (!((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) > 1)){
			img_genteBoa.setImageResource(R.drawable.perfil_imageview_genteboa_mdpi);
			img_esforcado.setImageResource(R.drawable.perfil_imageview_esforcado_mdpi);
			img_jogaTime.setImageResource(R.drawable.perfil_imageview_jogatime_mdpi);
			img_fairPlay.setImageResource(R.drawable.perfil_imageview_fairplay_mdpi);
		}
		img_genteBoa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cantVote[0]==false){
					award[0]=!award[0];
					
				}
			}
		});
		img_fairPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cantVote[3]==false){
					award[3]=!award[3];
					
				}
			}
		});
		img_jogaTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cantVote[2]==false){
					award[2]=!award[2];
					
				}
			}
		});
		img_esforcado.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cantVote[1]==false){
					award[1]=!award[1];
					
				}
			}
		});
		button_amigos = (Button) v.findViewById(R.id.perfil_button_amigos);
		namebadges = new String[4];
		namebadges[0] = "Gente Boa";
		namebadges[3] = "Fair Play";
		namebadges[1] = "Esforcado";
		namebadges[2] = "Joga Pro Time";
		initBadges();
		return v;
	}

	public void initBadges(){
		for(int i=0;i<4;i++) award[i] = false;
		if(badges != null){
			for (int j=0;j<4;j++){
				String NOME = badges[j];
				if (NOME.equals("Gente Boa")) award[0]=true;
				else if (NOME.equals("Fair Play")) award[3]=true;							
				else if (NOME.equals("Esforcado")) award[1]=true;
				else if (NOME.equals("Joga Pro Time")) award[2]=true;
			}
		}
		if(award[0]==false) img_genteBoa.setImageResource(R.drawable.cingenteboa);
		if(award[1]==false) img_esforcado.setImageResource(R.drawable.cintesfor);
		if(award[2]==false) img_jogaTime.setImageResource(R.drawable.cinjogatime);
		if(award[3]==false) img_fairPlay.setImageResource(R.drawable.cinfairplay);
		for(int i=0;i<4;i++) cantVote[i]=award[i];
	}

	@Override
	public void onTerminado(final Usuario in) {
		super.onTerminado(in);
		if(getView()!=null){
			getView().post(new Runnable() {
				@Override
				public void run() {
					LinearLayout dad_perfil = (LinearLayout) v.findViewById(R.id.dad_perfil);
					dad_perfil.removeView(dad_perfil.getChildAt(2));
					dad_perfil.removeView(dad_perfil.getChildAt(1));
					button_amigos.setText("Votar");
					button_amigos.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							boolean votou = false;
							for(int i=0;i<4;i++){
								if(award[i] && cantVote[i]==false){
									Server.vote_in_tag_user(idUser, MainActivity.self, namebadges[i], null);
									//atualizar a pagina
									votou = true;
								}
							}
							if(!votou){
								alerta(button_amigos,R.layout.alert_badges);
							}
						}
					});
				}
			});

		}
	}
}

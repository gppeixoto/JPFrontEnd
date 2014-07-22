package br.com.JoinAndPlay.ListEvent;

import java.util.Random;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ItemEvent {

	private static Random	gerador = new Random();
	public static final int  MAX_AMIGOS_QTD=7;
	String esporte;
	int[] amigos;

	public ItemEvent(){
		amigos= new int[MAX_AMIGOS_QTD];
		gerar();

	}

	public void gerar(){
		for (int i = 0; i < amigos.length; i++) {
			amigos[i]=gerador.nextInt(7);
			for (int j = 0; j < i; j++) {
				if(amigos[j]==amigos[i]){
					i--;
					break;
				}
			}
		}

	}
	public void drawerView(final View view,final Bitmap[] imagens) {
		// TODO Auto-generated method stub
		int idEsport=0;
		if(esporte!= null){
			idEsport=ConfigJP.getID(esporte);
		}
		ImageView imagem_bola=(ImageView) view.findViewById(R.id.item_list_icone);
		imagem_bola.setImageDrawable(view.getContext().getResources().getDrawable(ConfigJP.ESPORTE_BITMAP[idEsport]));

		View barra=(View) view.findViewById(R.id.item_list_barra);
		barra.setBackgroundResource(ConfigJP.ESPORTE_BARRA[idEsport]);
		LinearLayout content_image=(LinearLayout) view.findViewById(R.id.item_list_content_image);
		
	
		for (int i = 0; i < amigos.length; i++) {
			if(content_image.getChildCount()>i){
			ImageView imagem = (ImageView) content_image.getChildAt(i);

			imagem.setImageBitmap(imagens[amigos[i]]);
			}else break;
		}		



	}


}
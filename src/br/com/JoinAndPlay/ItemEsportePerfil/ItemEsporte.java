package br.com.JoinAndPlay.ItemEsportePerfil;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/*
import android.widget.LinearLayout;
import android.widget.GridView;
*/

public class ItemEsporte implements Parcelable {

	String esporte;
	int partidasJogadas;
	int avaliacaoJogador; //numero de estrelas
	
	public ItemEsporte(){

	}
	
	public ItemEsporte(Parcel in){
		esporte =in.readString();
		partidasJogadas = in.readInt();
		avaliacaoJogador = in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(esporte);
		dest.writeInt(partidasJogadas);
		dest.writeInt(avaliacaoJogador);
	}
	
	public void drawerView(View view) {
		// TODO Auto-generated method stub
		int idEsport = 0;
		if(esporte!= null){
			idEsport=ConfigJP.getID(esporte);
		}
		idEsport=0;
		ImageView imagem_bola=(ImageView) view.findViewById(R.id.item_list_icone);
		//imagem_bola.setImageDrawable(view.getContext().getResources().getDrawable(ConfigJP.ESPORTE_BITMAP[idEsport]));
		
		View barra=(View) view.findViewById(R.id.item_list_barra);
		//barra.setBackgroundResource(ConfigJP.ESPORTE_BARRA[idEsport]);

		TextView esporteView = (TextView) view.findViewById(R.id.item_list_esporte);
		if(esporte!=null){
			esporteView.setText(esporte);
		}
		
		TextView numPartidasView = (TextView) view.findViewById(R.id.perfil_num_partidas);
		numPartidasView.setText(esporte);
		/**
		 * Falta as estrelas
		 */
	}
}
package br.com.JoinAndPlay.ItemEsportePerfil;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/*
import android.widget.LinearLayout;
import android.widget.GridView;
*/

public class ItemEsporte implements Parcelable {

	public String esporte;
	public int partidasJogadas;
	public double avaliacaoJogador; //numero de estrelas
	
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
		dest.writeDouble(avaliacaoJogador);
	}
	
	public void drawerView(View view) {
		int idEsporte = 0;
		if(esporte!= null){
			idEsporte=ConfigJP.getEsporteID(esporte);
		}
		ImageView imagem_esporte=(ImageView) view.findViewById(R.id.perfil_imageview_barraseparar);
		imagem_esporte.setImageDrawable(view.getContext().getResources().getDrawable(ConfigJP.ESPORTE_BITMAP[idEsporte]));
		
		View barra=(View) view.findViewById(R.id.barra_esporte);
		barra.setBackgroundResource(ConfigJP.ESPORTE_BARRA[idEsporte]);
		TextView esporteView = (TextView) view.findViewById(R.id.textView2);
		
		if(esporte!=null){
			//esporteView.setText(idEsporte+"");
			esporteView.setText(esporte);
			//esporteView.setText("Teste estatico");
		}
		
		Log.v("PARTIDAS JOGADAS", partidasJogadas+"");
		
		TextView numPartidasView = (TextView) view.findViewById(R.id.perfil_qtd_partidas);
		TextView qtdPartidas = (TextView) view.findViewById(R.id.perfil_partidas_jogadas);
		if (partidasJogadas == 1) numPartidasView.setText("1 partida jogada"); else {
			qtdPartidas.setText(partidasJogadas);
			numPartidasView.setText(" partidas jogadas");
		}
		
		
		ImageView star1 = (ImageView) view.findViewById(R.id.perfil_imageview_esporte_star1);
		ImageView star2 = (ImageView) view.findViewById(R.id.perfil_imageview_esporte_star2);
		ImageView star3 = (ImageView) view.findViewById(R.id.perfil_imageview_esporte_star3);
		ImageView star4 = (ImageView) view.findViewById(R.id.perfil_imageview_esporte_star4);
		ImageView star5 = (ImageView) view.findViewById(R.id.perfil_imageview_esporte_star5);
		
		if (partidasJogadas >= 1){
			star1.setImageResource(R.drawable.star1);
		} else if (partidasJogadas >= 2){
			star2.setImageResource(R.drawable.star1);
		} else if (partidasJogadas >= 3){
			star3.setImageResource(R.drawable.star1);
		} else if (partidasJogadas >= 4) {
			star4.setImageResource(R.drawable.star1);
		} else if (partidasJogadas >= 5){
			star5.setImageResource(R.drawable.star1);
		}
	}
}
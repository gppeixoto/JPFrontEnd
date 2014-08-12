package br.com.JoinAndPlay.ListEvent;

import java.util.Random;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Evento;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemEvent implements Parcelable {

	private static Random	gerador = new Random();
	public static final int  MAX_AMIGOS_QTD=7;
	public String esporte;
	public String titulo;
	public String quadra;
	public String local;
	public String cidade;
	public String hora;
	public String data;
	public int amigos_qtd;
	public int qtd_participantes;
	public int preco_centavos;
	public int distancia;
	public boolean privado;
	public Evento evento;

	public String[] amigos;

	public ItemEvent(){
		amigos= new String[MAX_AMIGOS_QTD];

	}
	public ItemEvent(Parcel in){
		esporte =in.readString();
		titulo =in.readString();
		quadra =in.readString();
		local =in.readString();
		cidade =in.readString();
		hora=in.readString();
		data =in.readString();
		amigos_qtd =in.readInt();
		qtd_participantes =in.readInt();
		preco_centavos =in.readInt();
		distancia =in.readInt();
		amigos = new String[in.readInt()];
		in.readStringArray(amigos);   
		privado= in.readInt()==1;
		evento=(Evento)in.readSerializable();

	}




	public void drawerView(View view,Bitmap[] imagens) {
		// TODO Auto-generated method stub
		int idEsport=0;
		if(esporte!= null){
			idEsport=ConfigJP.getEsporteID(esporte);
		}
		ImageView imagem_bola=(ImageView) view.findViewById(R.id.item_list_icone);
		imagem_bola.setImageDrawable(view.getContext().getResources().getDrawable(ConfigJP.ESPORTE_BITMAP[idEsport]));

		View barra=(View) view.findViewById(R.id.item_list_barra);
		barra.setBackgroundResource(ConfigJP.ESPORTE_BARRA[idEsport]);

		LinearLayout content_image=(LinearLayout) view.findViewById(R.id.item_list_content_image);

		TextView esporteView = (TextView) view.findViewById(R.id.item_list_esporte);
		if(esporte!=null){

			esporteView.setText(esporte);
		}
		TextView amigosQtdView = (TextView) view.findViewById(R.id.item_list_qtd_amg);
		amigosQtdView.setText(""+amigos_qtd);

		TextView tituloView = (TextView) view.findViewById(R.id.item_list_titulo);
		if(titulo!=null){
			tituloView.setText(titulo);
		}

		TextView quadraView = (TextView) view.findViewById(R.id.item_list_quadra);
		if(quadra!=null){
			quadraView.setText(quadra);
		}
		TextView localView = (TextView) view.findViewById(R.id.item_list_local);
		if(local!=null){
			localView.setText(local);
		}
		TextView cidadeView = (TextView) view.findViewById(R.id.item_list_cidade);
		if(cidade!=null){
			cidadeView.setText(cidade);
		}
		TextView participantesView = (TextView) view.findViewById(R.id.item_list_participantes);
		switch (qtd_participantes) {
		case 0:
			participantesView.setText("");

			break;
		case 1:
			participantesView.setText(qtd_participantes+" participante");

			break;

		default:
			participantesView.setText(qtd_participantes+" participantes");

			break;
		}

		TextView horaView = (TextView) view.findViewById(R.id.item_list_hora);
		if(hora!=null){
			horaView.setText(hora);
		}
		TextView dataView = (TextView) view.findViewById(R.id.item_list_dia);
		if(data!=null){
			dataView.setText(data);
		}

		TextView distanciaView = (TextView) view.findViewById(R.id.item_list_distancia);
		if(distancia==0){
			distanciaView.setText(distancia+"m");
		}
		TextView precoView = (TextView) view.findViewById(R.id.item_list_preco);
		if(preco_centavos==0){
			precoView.setText("");
		}else{
			String temp=""+preco_centavos;
			int qtd=temp.length();
			for (; qtd <3; qtd++) {
				temp=0+temp;
			}
			temp= temp.substring(0,qtd-2 )+","+temp.substring(qtd-2);
			precoView.setText("R$ "+temp);

		}
		View privadoView = (View) view.findViewById(R.id.item_list_privado);
		privadoView.setVisibility(privado?View.VISIBLE:View.INVISIBLE);


		for (int i = 0; i < Math.min(amigos.length,MAX_AMIGOS_QTD); i++) {
			if(content_image.getChildCount()>i){
				ImageView imagem = (ImageView) content_image.getChildAt(i);

				new DownloadImagemAsyncTask(view.getContext(),imagem).execute(amigos[i]);
				//imagem.invalidate();
			}else break;
		}		

		for (int i = amigos.length; i <MAX_AMIGOS_QTD; i++) {
			if(content_image.getChildCount()>i){
				ImageView imagem = (ImageView) content_image.getChildAt(i);

				imagem.setVisibility(View.INVISIBLE);
			}else break;
		}

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(esporte);
		arg0.writeString(titulo);
		arg0.writeString(quadra);
		arg0.writeString(local);
		arg0.writeString(cidade);
		arg0.writeString(hora);
		arg0.writeString(data);
		arg0.writeInt(amigos_qtd);
		arg0.writeInt(qtd_participantes);
		arg0.writeInt(preco_centavos);
		arg0.writeInt(distancia);
		arg0.writeInt(amigos.length);
		arg0.writeStringArray(amigos);
		arg0.writeInt(privado?1:0);
		arg0.writeSerializable(evento);
	}


}
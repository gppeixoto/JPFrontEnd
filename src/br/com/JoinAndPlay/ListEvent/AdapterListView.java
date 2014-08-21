package br.com.JoinAndPlay.ListEvent;

import java.util.ArrayList;
import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterListView extends BaseAdapter
{
	private LayoutInflater mInflater;
	private ArrayList<Evento> itens;
	public static int MAX_AMIGOS_QTD=7;
	public AdapterListView(LayoutInflater inflater, ArrayList<Evento> itens)
	{
		//Itens que preencheram o listview
		this.itens = itens;
		//responsavel por pegar o Layout do item.
		mInflater = inflater;
	}
	public int getCount()
	{
		return itens.size();
	}


	public Evento getItem(int position)
	{
		return itens.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}
	@Override

	public View getView(int position, View view, ViewGroup parent)
	{

		if(view==null)
			view = mInflater.inflate(R.layout.item_list, parent,false);

		if(position<0)return view;

		//Pega o item de acordo com a posção.
		Evento item = itens.get(position);
		//infla o layout para podermos preencher os dados

		drawerView(view,item);

		//atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
		//ao item e definimos as informações.
		// ((TextView) view.findViewById(R.id.)).setText(item.getTexto());
		//  ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());
		return view;
	}


	public void drawerView(final View view,Evento evento) {
		// TODO Auto-generated method stub
		if(view ==null) return;


		int idEsport=0;
		if(evento.getSport()!= null){
			idEsport=ConfigJP.getEsporteID(evento.getSport());
		}
		ImageView imagem_bola=(ImageView) view.findViewById(R.id.item_list_icone);
		imagem_bola.setImageDrawable(view.getContext().getResources().getDrawable(ConfigJP.ESPORTE_BITMAP[idEsport]));

		View barra=(View) view.findViewById(R.id.item_list_barra);
		barra.setBackgroundResource(ConfigJP.ESPORTE_BARRA[idEsport]);

		LinearLayout content_image=(LinearLayout) view.findViewById(R.id.item_list_content_image);

		TextView esporteView = (TextView) view.findViewById(R.id.item_list_esporte);
		if(evento.getSport()!=null){

			esporteView.setText(evento.getSport());
		}
		TextView amigosQtdView = (TextView) view.findViewById(R.id.item_list_qtd_amg);
		amigosQtdView.setText(""+evento.getNumFriends());

		TextView tituloView = (TextView) view.findViewById(R.id.item_list_titulo);
		if(evento.getName()!=null){
			tituloView.setText(evento.getName());
		}

		TextView quadraView = (TextView) view.findViewById(R.id.item_list_quadra);
		if(evento.getLocalizationName()!=null){
			quadraView.setText(evento.getLocalizationName());
		}
		TextView localView = (TextView) view.findViewById(R.id.item_list_local);
		if(evento.getLocalizationAddress()!=null){
			localView.setText(evento.getLocalizationAddress());
		}
		TextView cidadeView = (TextView) view.findViewById(R.id.item_list_cidade);
		if(evento.getCity()!=null){
			cidadeView.setText(evento.getCity());
		}
		TextView participantesView = (TextView) view.findViewById(R.id.item_list_participantes);
		switch (evento.getUsers().size()) {
		case 0:
			participantesView.setText("");

			break;
		case 1:
			participantesView.setText(evento.getUsers().size()+" participante");

			break;

		default:
			participantesView.setText(evento.getUsers().size()+" participantes");

			break;
		}

		TextView horaView = (TextView) view.findViewById(R.id.item_list_hora);
		if(evento.getStartTime()!=null){
			horaView.setText(evento.getStartTime());
		}
		TextView dataView = (TextView) view.findViewById(R.id.item_list_dia);
		if(evento.getDate()!=null){
			dataView.setText(evento.getDate());
		}

		TextView distanciaView = (TextView) view.findViewById(R.id.item_list_distancia);
		try{
			int i = Integer.parseInt(evento.getDistance());

			if(i==0){
				distanciaView.setText("AQUI");

			}else if(i<1000){
				distanciaView.setText(i+"m");

			}else if(i>Math.pow(10, 9)){
				distanciaView.setText("");

			}else{

				distanciaView.setText(Math.round(i/1000.0)+"Km");

			}

		}catch(Exception _){
			distanciaView.setText("");

		}


		TextView precoView = (TextView) view.findViewById(R.id.item_list_preco);
		if(evento.getPrice()==0){
			precoView.setText("");
		}else{
			String temp=""+evento.getPrice();
			int qtd=temp.length();
			for (; qtd <3; qtd++) {
				temp=0+temp;
			}
			temp= temp.substring(0,qtd-2 )+","+temp.substring(qtd-2);
			precoView.setText("R$ "+temp);

		}
		View privadoView = (View) view.findViewById(R.id.item_list_privado);
		privadoView.setVisibility(evento.getPrivacy()?View.VISIBLE:View.INVISIBLE);


		for (int i = 0; i <MAX_AMIGOS_QTD; i++) {
			if(content_image.getChildCount()>i){
				ImageView imagem = (ImageView) content_image.getChildAt(i);

				imagem.setVisibility(View.INVISIBLE);
			}else break;
		}

		for (int i = 0; i < Math.min(MAX_AMIGOS_QTD,evento.getUsers().size()); i++) {
			if(content_image.getChildCount()>i){
				ImageView imagem = (ImageView) content_image.getChildAt(i);
				DownloadImagem.postLoad(imagem, evento.getUsers().get(i).getPhoto());
				//imagem.invalidate();
			}else break;
		}		





	}
}
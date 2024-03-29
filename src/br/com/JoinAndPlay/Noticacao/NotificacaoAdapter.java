package br.com.JoinAndPlay.Noticacao;

import java.util.ArrayList;
import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Notificacao;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificacaoAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	ArrayList<Notificacao> list;
	public NotificacaoAdapter(LayoutInflater inflater,ArrayList<Notificacao> list){
		this.list=list;
		mInflater = inflater;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Math.min(1,list.size());
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).hashCode();
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Log.v("LOG:","antes");

		if(list==null || list.size()==0 ){
			Log.v("LOG:","depois");
			TextView sem_convite = new TextView(MainActivity.self);
			sem_convite.setText("Sem nenhum convite no momento.");
			sem_convite.setPadding(6, 6, 6, 6);
			sem_convite.setGravity(Gravity.CENTER);
			return ((View)sem_convite);
		}
		
		if(view==null){
			view= mInflater.inflate(R.layout.item_notif, arg2,false);
		}
		Notificacao not = list.get(arg0);
		TextView nomeView= (TextView)view.findViewById(R.id.item_notif_nome);
		TextView bairroCidadeView= (TextView)view.findViewById(R.id.item_notif_bairro_cidade);
		TextView dataHoraView= (TextView)view.findViewById(R.id.item_notif_data_hora);
		TextView nomePerfilView= (TextView)view.findViewById(R.id.item_notif_nome_perfil);
		TextView tempoView= (TextView)view.findViewById(R.id.item_notif_temp);

		ImageView esportView= (ImageView)view.findViewById(R.id.item_notif_esport);
		ImageView perfilView= (ImageView)view.findViewById(R.id.item_notif_usuario);

		View cadeado =		view.findViewById(R.id.item_notif_privado);


		nomeView.setText(not.getEventName());
		dataHoraView.setText(not.getDate()+" • "+not.getHourBegin());

		bairroCidadeView.setText(not.getLocalizationName()+" • "+not.getNeighborhood());
		nomePerfilView.setText(not.getCreatorName()+" convidou você");
		tempoView.setText("");
		DownloadImagem.postLoad(perfilView, not.getCreatorPhoto());
		esportView.setImageResource(ConfigJP.ESPORTE_BUTTON[ConfigJP.getEsporteID(not.esporte)]);


		if(!not.getPrivacy()) cadeado.setVisibility(View.INVISIBLE);

		return view;
	}

}

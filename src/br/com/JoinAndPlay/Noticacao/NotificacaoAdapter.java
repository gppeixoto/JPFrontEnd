package br.com.JoinAndPlay.Noticacao;

import java.util.ArrayList;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Notificacao;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
		return list.size();
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
		View confirmarView = view.findViewById(R.id.item_notif_confirmar);
		View recusarView = view.findViewById(R.id.item_notif_recusar);


		nomeView.setText(not.getEventName());
		dataHoraView.setText(not.getDate()+" • "+not.getHourBegin());
		///	bairroCidadeView.setText("");
		nomePerfilView.setText(not.getCreatorName()+" convidou voçê");
		//	tempoView.setText(not.get)
		esportView.setImageResource(ConfigJP.ESPORTE_BUTTON[ConfigJP.getEsporteID(not.esporte)]);


		if(!not.getPrivacy()) cadeado.setVisibility(View.INVISIBLE);
		confirmarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		recusarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		
		return view;
	}

}

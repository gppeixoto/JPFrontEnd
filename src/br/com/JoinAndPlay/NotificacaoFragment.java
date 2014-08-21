package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.Noticacao.NotificacaoAdapter;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Notificacao;
import br.com.JoinAndPlay.Server.Server;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NotificacaoFragment extends Fragment implements Connecter<Map<String,Vector<Notificacao>>>,OnItemClickListener,Runnable {

	LayoutInflater inflater;
	ListView list;
	ArrayList<Notificacao> notifi ;
	static final int ID=2;
	Map<String,String> conjuntID= new HashMap<String,String>();
	
	public void alerta(int value){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
		((MainActivity) MainActivity.self).loadTela(ID);

		if (container == null) {
			return null;
		}
		Configuration config = MainActivity.self.getResources().getConfiguration();
		LinearLayout v = (LinearLayout)inflater.inflate(R.layout.notificacao_fragment, container, false);

		if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){

			v.setOrientation(LinearLayout.HORIZONTAL);
		}
		final NotificacaoFragment self = this;
		this.inflater=inflater;
		maker((ListView)v.findViewById(R.id.lista_view_convites_1),inflater);
		ConfigJP.getUserID(MainActivity.self, new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				// TODO Auto-generated method stub
				if(in==null){
					Bundle args = new Bundle();
					MainActivity.self.popLoadTela(ID);
					args.putBoolean("internet",false);
					BolaForaFragment bfm = new BolaForaFragment();
					bfm.setArguments(args);
					MainActivity.self.mudarAba(ID,bfm);
				}else
					Server.get_invites(ConfigJP.UserId,self);
			}
		});


		return  v;
	}

	private void maker(ListView list,LayoutInflater inflater){
		this.list=list;
		list.setDivider(getResources().getDrawable(R.drawable.linha));
		list.setDividerHeight(1);
		list.setVisibility(View.VISIBLE);
	}

	@Override
	public void onTerminado(Map<String, Vector<Notificacao>> in) {
		if(in==null){
			list.post(new Runnable() {
				@Override
				public void run() {
					Bundle args = new Bundle();
					MainActivity.self.popLoadTela(ID);
					args.putBoolean("internet",false);
					BolaForaFragment bfm = new BolaForaFragment();
					bfm.setArguments(args);
					MainActivity.self.mudarAba(ID,bfm);
				}
			});

			return;
		}
		if(in.size()==0){
			list.post(new Runnable() {
				@Override
				public void run() {
					((MainActivity) MainActivity.self).popLoadTela(ID);
					//alerta(R.layout.alert_sem_notificacoes);
				}
			});return;
		}

		notifi = new ArrayList<Notificacao>();
		for (Iterator<Entry<String, Vector<Notificacao>>> iterator = in.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Vector<Notificacao>> type = (Entry<String, Vector<Notificacao>>) iterator.next();
			for (Iterator<Notificacao> iterator2 = type.getValue().iterator(); iterator2
					.hasNext();) {
				Notificacao not = (Notificacao) iterator2.next();
				not.esporte=type.getKey();
				conjuntID.put(not.getEventId(), not.getEventId());
				notifi.add(not);
			}

		}
		list.setOnItemClickListener(this);
		list.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				list.setAdapter(new NotificacaoAdapter(inflater,notifi));
				((MainActivity) MainActivity.self).popLoadTela(ID);

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
		// TODO Auto-generated method stub
		if(notifi!=null && notifi.size()>(i) ){

			Bundle arg= new Bundle();
			arg.putString("evento",notifi.get(i).getEventId() );
			arg.putInt("idTab", ID);
			Fragment fragment = new EventFragment();
			fragment.setArguments(arg);
			((MainActivity)MainActivity.self).mudarAbaAtual(fragment);
		}	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.v("aba",notifi+" "+getView());
		if(getView()!=null){
			if(notifi!=null && list.getAdapter()!=null){
				Log.v("aba",""+list.getAdapter());
				Server.get_invites(ConfigJP.UserId,new Connecter<Map<String,Vector<Notificacao>>>() {

					@Override
					public void onTerminado(Map<String, Vector<Notificacao>> in) 
					{
						// TODO Auto-generated method stub
						if(in==null)return;

						for (Iterator<Entry<String, Vector<Notificacao>>> iterator = in.entrySet().iterator(); iterator.hasNext();) {
							Entry<String, Vector<Notificacao>> type = (Entry<String, Vector<Notificacao>>) iterator.next();
							for (Iterator<Notificacao> iterator2 = type.getValue().iterator(); iterator2
									.hasNext();) {
								Notificacao not = (Notificacao) iterator2.next();
								not.esporte=type.getKey();
								if(!conjuntID.containsKey(not.getEventId())){
									notifi.add(not);

								}

							}
							list.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
								}
							});

						}
					}
				});



			}

		}
	}

}

package br.com.JoinAndPlay.Noticacao;

import java.util.ArrayList;

import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Notificacao;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1==null){
			arg1= mInflater.inflate(R.layout.item_notif, arg2,false);
		}
		
		return arg1;
	}

}

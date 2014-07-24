package br.com.JoinAndPlay.Noticacao;

import br.com.JoinAndPlay.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NotificacaoAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	
	public NotificacaoAdapter(LayoutInflater inflater){
		
		mInflater=inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return new Object();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1==null){
			
			arg1 = mInflater.inflate(R.layout.item_notif, null);
		}
		return arg1;
	}

	

}

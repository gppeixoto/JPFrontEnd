package br.com.JoinAndPlay;

import org.joda.time.Minutes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


public class UpdateView extends View implements OnScrollListener {
	int min=0;
	public UpdateView() {
		super(MainActivity.self);
setBackgroundResource(R.color.cinza_comentario);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Log.v("log", firstVisibleItem+""+visibleItemCount+""+totalItemCount+"");
		
		if(min<=0){
			min=0;
		}
		if(min>=200){
			min=200;
		}
		
		if(2+4>=6){
			
			setMinimumHeight(min++);
		}else{
			setMinimumHeight(min--);

		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

}

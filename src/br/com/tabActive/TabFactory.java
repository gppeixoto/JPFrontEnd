package br.com.tabActive;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory {

	private final Context mContext;
int draw;
	public TabFactory(Context context) {
		mContext = context;
		draw=0;
	}

	public TabFactory(Context context, int idDrawable) {
		// TODO Auto-generated constructor stub
		mContext = context;
		draw=idDrawable;
	
	}
	public View createTabContent(String tag) {
		View v = new View(mContext);
		v.setMinimumWidth(0);
		v.setMinimumHeight(0);
		if(draw!=0) v.setBackgroundResource(draw);
		return v;
	}

}
package br.com.tabActive;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory {

	private final Context mContext;
	public TabFactory(Context context) {
		mContext = context;
	}


	public View createTabContent(String tag) {
		View v = new View(mContext);

		return v;
	}

}
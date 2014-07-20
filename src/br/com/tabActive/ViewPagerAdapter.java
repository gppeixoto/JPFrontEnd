package br.com.tabActive;


import java.util.List;

import br.com.JoinAndPlay.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;

public class ViewPagerAdapter extends PagerAdapter  {
	
	private Fragment fm;

	public ViewPagerAdapter(Fragment fm) {
		this.fm=fm;
		
		
	}
	public void mudar(Fragment dest, int position,boolean voltar){
		int id=0;
		switch (position) {
		case 0:
			id=R.id.page_1;
			break;
		case 1:
			id=R.id.page_2;
			break;
		case 2:
			id=R.id.page_3;
			break;
		case 3:
			id=R.id.page_4;
			break;

		default:
			break;
		}
		
		
		FragmentTransaction temp = fm.getChildFragmentManager().beginTransaction().replace(id, dest);
		if(voltar){
			
			temp.addToBackStack(null);
		}
		
		
		temp.commit();

		
		
	}
    public Object instantiateItem(View collection, int position) {
	int id=0;
	switch (position) {
	case 0:
		id=R.id.page_1;
		break;
	case 1:
		id=R.id.page_2;
		break;
	case 2:
		id=R.id.page_3;
		break;
	case 3:
		id=R.id.page_4;
		break;

	default:
		break;
	}
	Log.v("lol", " "+position+","+fm.getView().findViewById(id));
    return fm.getView().findViewById(id);
}
    @Override
	public
    void destroyItem(ViewGroup container,int  position,Object object){
    	Log.v("des", " "+position+","+object);

    }
    
	@Override
	public int getCount() {
	

		return 4;
	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
        return arg0 == ((View) arg1);
	}

}

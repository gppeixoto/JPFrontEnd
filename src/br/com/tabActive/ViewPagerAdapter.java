package br.com.tabActive;


import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	
	private List<FragmentBase> mFragments;
    private 		List<Fragment> frag;

	public ViewPagerAdapter(FragmentManager fm, List<FragmentBase> fragments,List<Fragment> frag) {
		super(fm);
		this.frag=frag;
		FragmentBase.newFragment=frag;
		mFragments = fragments;
		
		
	}
	
	
	@Override
	public Fragment getItem(int i) {
		if(mFragments.size()!=frag.size()){
				for (int j = mFragments.size(); j <= frag.size(); j++) {
					Bundle arg= new Bundle();
					arg.putInt("id", j);
					FragmentBase fg = new FragmentBase();
					fg.setArguments(arg);
					mFragments.add(fg );
				}
				
		}
	//	mFragments.remove(i);
		//mFragments.add(i,new FragmentBase());

		return mFragments.get(i);// ;
	}

	@Override
	public int getCount() {

		return frag.size();
	}

}

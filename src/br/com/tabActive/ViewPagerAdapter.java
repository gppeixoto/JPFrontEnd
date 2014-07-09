package br.com.tabActive;


import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	
	private List<FragmentBase> mFragments;
    private 		List<Fragment> frag;

	public ViewPagerAdapter(FragmentManager fm, List<FragmentBase> fragments,List<Fragment> frag) {
		super(fm);
		this.frag=frag;
		mFragments = fragments;
		
	}
	
	
	@Override
	public Fragment getItem(int i) {
		mFragments.add(i, new FragmentBase().set(frag.get(i)));
		return mFragments.get(i);// ;
	}

	@Override
	public int getCount() {
		return frag.size();
	}

}

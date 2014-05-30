package br.com.tabActive;

import java.util.List;
import java.util.Vector;

import br.com.JoinAndPlay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabFragment extends Fragment implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private List<Fragment> fragments;
	private long id;
	private HorizontalScrollView scroll;
	private View tamanho;
	
	public TabSpec addFragments(Fragment a,String s){
		fragments.add(a);

		final TabSpec tabSpec =mTabHost.newTabSpec("Tab"+id).setIndicator(s);
		tabSpec.setContent(new TabFactory(getActivity(),R.drawable.seletc_tab));
		mTabHost.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mTabHost.addTab(tabSpec);
				tamanho.setMinimumWidth(Math.max(fragments.size()*60,tamanho.getWidth()));
				mTabHost.getTabWidget().getChildAt(mTabHost.getTabWidget().getChildCount()-1).setBackgroundResource(R.drawable.seletc_tab);//(getResources().getDrawable(R.drawable.seletc_tab));;

			}
		});
		//
		
	/*	
		*/
	return null;
	//	return null;
	}	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   	  	fragments = new Vector<Fragment>();

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

        mTabHost= (TabHost) inflater.inflate(R.layout.activity_tab_tela, container, false);

        mTabHost.setup();

         mTabHost.setOnTabChangedListener(this);
   	 if (savedInstanceState != null) {
			// Define a Tab de acordo com o estado salvo
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
   	 
		mPagerAdapter = new ViewPagerAdapter(
					getActivity().getSupportFragmentManager(), fragments);
return mTabHost;
	
			

}

	   @Override
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
            scroll= (HorizontalScrollView) mTabHost.findViewById(R.id.H_scroll);
       		tamanho=(View) mTabHost.findViewById(android.R.id.tabs);
     		mTabHost.getTabWidget().setBackgroundResource(R.drawable.seletc_wid_tab);
   			mViewPager = (ViewPager) mTabHost.findViewById(R.id.pager);
   			mViewPager.setAdapter(this.mPagerAdapter);
   			mViewPager.setOnPageChangeListener(this);
   			
       }

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// salva a Tab selecionada
		if(outState!=null)
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}


	public void onTabChanged(String tag) {
		// Avisa para o mViewPager qual a Tab que est� ativa
		int pos = this.mTabHost.getCurrentTab();
		scroll.scrollTo((tamanho.getWidth()*(pos-1))/fragments.size(),scroll.getScrollY());

		this.mViewPager.setCurrentItem(pos);
	}
	

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
		scroll.scrollTo((tamanho.getWidth()*(position-1))/fragments.size(),scroll.getScrollY());

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
	
}

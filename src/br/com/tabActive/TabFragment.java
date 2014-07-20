package br.com.tabActive;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.com.JoinAndPlay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
	private List<Integer> ids;
	private HorizontalScrollView scroll;
	private View tamanho;



	public void tabChange(int idtab,Fragment arg1, boolean pilha){
		if(mPagerAdapter!=null)
			((ViewPagerAdapter)mPagerAdapter).mudar(arg1, idtab,pilha);
		if(fragments.size()>idtab){
			fragments.remove(idtab);
			fragments.add(idtab,arg1);

					}
	
	}
	public TabSpec addFragments(FragmentActivity context,Fragment a, int id){
		if(fragments==null){
			fragments= new ArrayList<Fragment>();
			ids=new ArrayList<Integer>();
			FragmentBase.newFragment=fragments;
		}

		//getFragmentManager().beginTransaction().replace(R.id.tela_aba_2, base).commit();
		fragments.add(a);
		a.setRetainInstance(true);

		ids.add(id);
		//	final 
		//new postTabpec(tabSpec, id).run();
		if(mPagerAdapter!=null){
			Log.v("lol", ""+fragments);

			TabSpec tabSpec =mTabHost.newTabSpec("Tab"+id).setIndicator("").setContent(new TabFactory(context));
			mTabHost.addTab(tabSpec);
			tamanho.setMinimumWidth(Math.max(fragments.size()*60,tamanho.getWidth()));
			mTabHost.getTabWidget().getChildAt(mTabHost.getTabWidget().getChildCount()-1).setBackgroundResource(id);//(getResources().getDrawable(R.drawable.seletc_tab));;
			mPagerAdapter.notifyDataSetChanged();

			return tabSpec;
		}
		return null;
	}	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(fragments==null){
			fragments = new Vector<Fragment>();
			ids = new Vector<Integer>();
		}



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

		return mTabHost;



	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		scroll= (HorizontalScrollView) mTabHost.findViewById(R.id.H_scroll);
		tamanho=(View) mTabHost.findViewById(android.R.id.tabs);
		mTabHost.getTabWidget().setBackgroundResource(R.drawable.seletc_wid_tab);
		mViewPager = (ViewPager) mTabHost.findViewById(R.id.pager);
		for (int i = 0; i < ids.size(); i++) {
			//	Fragment f=fragments.remove(0);

			//	Log.v("lol", ""+fragments);


			TabSpec tabSpec =mTabHost.newTabSpec("Tab"+ids.get(i)).setIndicator("").setContent(new TabFactory(getActivity()));

			mTabHost.addTab(tabSpec);
			tamanho.setMinimumWidth(Math.max(fragments.size()*60,tamanho.getWidth()));
			mTabHost.getTabWidget().getChildAt(mTabHost.getTabWidget().getChildCount()-1).setBackgroundResource(ids.get(i));//(getResources().getDrawable(R.drawable.seletc_tab));;



		}

		mPagerAdapter = new ViewPagerAdapter(
				this);

		mViewPager.setAdapter(this.mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		//getFragmentManager().beginTransaction().replace(R.id.tela_aba_2, base).commit()
		Log.v("lista", ""+fragments);
		for (int i = 0; i < fragments.size(); i++) {
			((ViewPagerAdapter)mPagerAdapter).mudar(fragments.get(i),i, false);
			Log.v("lista",""+i);
			if(i>=3) return;
		}

	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// salva a Tab selecionada
		if(outState!=null)
			outState.putString("tab", mTabHost.getCurrentTabTag());
	}


	public void onTabChanged(String tag) {
		// Avisa para o mViewPager qual a Tab que est� ativa
		final int pos = this.mTabHost.getCurrentTab();
		scroll.scrollTo((tamanho.getWidth()*(pos-1))/4,scroll.getScrollY());
		this.mViewPager.setCurrentItem(pos);
		Log.v("tab", ""+pos);
		getView().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(fragments.size()>pos)

					tabChange(pos,fragments.get(pos),false);

			}
		});

	}


	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
		scroll.scrollTo((tamanho.getWidth()*(position-1))/4,scroll.getScrollY());
	
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}

package br.com.tabActive;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
	private Runnable[] acoes = new Runnable[SIZE]; 
	public final static int SIZE=5;
	public void onloader(final int idtab){
		Log.v("open", ""+idtab);

		BaseFragment base=((BaseFragment)MainActivity.self.getSupportFragmentManager().findFragmentByTag("tab"+(idtab+1)));
		if(base!=null)
			base.open();

	}
	public void onfinish(int idtab){
		Log.v("close", ""+idtab);
		BaseFragment base=((BaseFragment)MainActivity.self.getSupportFragmentManager().findFragmentByTag("tab"+(idtab+1)));
		if(base!=null)
			base.close();

	}
	public void tabChange(int idtab,Fragment arg1,boolean voltar){

		if(!voltar){
			getFragmentManagerAba(idtab).popBackStack();
		}
		FragmentTransaction ft = getFragmentManagerAba(idtab).beginTransaction();

		ft=ft.replace(R.id.tela_aba,arg1).setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);

		onfinish(idtab);

		ft.commit();


	}
	public void tabPop(int idtab){
		onfinish(idtab);

		getFragmentManagerAba(idtab).popBackStack();

	}
	public void tabPop(){
		tabPop(mTabHost.getCurrentTab());
	}
	public void tabChange(Fragment arg1,boolean voltar){

		tabChange(mTabHost.getCurrentTab(),arg1, voltar);
	}
	public TabSpec addFragments(FragmentActivity context,Fragment a,Runnable ac ,int id){
		if(fragments==null){
			fragments= new ArrayList<Fragment>();
			ids=new ArrayList<Integer>();
		}
		if(fragments.size()<SIZE)
			acoes[fragments.size()]=ac;
		fragments.add(a);
		a.setRetainInstance(true);
		ids.add(id);

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
		mPagerAdapter=new ViewPagerStaticAdapter(inflater);

		return mTabHost;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		scroll= (HorizontalScrollView) mTabHost.findViewById(R.id.H_scroll);
		tamanho=(View) mTabHost.findViewById(android.R.id.tabs);
		mTabHost.getTabWidget().setBackgroundResource(R.drawable.seletc_wid_tab);
		mViewPager = (ViewPager) mTabHost.findViewById(R.id.pager);

		for (int i = 0; i < Math.min(ids.size(),SIZE); i++) {
			TabSpec tabSpec =mTabHost.newTabSpec("Tab"+ids.get(i)).setIndicator("").setContent(new TabFactory(getActivity()));
			mTabHost.addTab(tabSpec);
			tamanho.setMinimumWidth(Math.max(fragments.size()*60,tamanho.getWidth()));
			mTabHost.getTabWidget().getChildAt(mTabHost.getTabWidget().getChildCount()-1).setBackgroundResource(ids.get(i));//(getResources().getDrawable(R.drawable.seletc_tab));;
		}
		mViewPager.setAdapter(this.mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		for (int i = 0; i < fragments.size(); i++) {
			FragmentTransaction ft=getFragmentManagerAba(i).beginTransaction();
			ft.replace(R.id.tela_aba, fragments.get(i)).commit();
		}
		if(savedInstanceState!=null){
			mTabHost.setCurrentTab((savedInstanceState.getInt("tab")));
		}else{

			mTabHost.setCurrentTab(0);
		}


	}

	public FragmentManager getFragmentManagerAba( int i){

		return getFragmentManager().findFragmentByTag("tab"+(i+1)).getChildFragmentManager();
	}
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// salva a Tab selecionada
		if(outState!=null){
			outState.putInt("tab", mTabHost.getCurrentTab());
		}
	}


	public void onTabChanged(String tag) {
		// Avisa para o mViewPager qual a Tab que est� ativa
		int pos = this.mTabHost.getCurrentTab();
		scroll.scrollTo((tamanho.getWidth()*(pos-1))/SIZE,scroll.getScrollY());
		this.mViewPager.setCurrentItem(pos);
		if(acoes[pos]!=null) scroll.post(acoes[pos]);

	}
	public boolean onBackPressed() {
		FragmentManager childFragmentManager =getFragmentManagerAba(mTabHost.getCurrentTab());
		if(childFragmentManager.getBackStackEntryCount()==0){
			return false;

		} else {
			childFragmentManager.popBackStack();
		}
		return true;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
		scroll.scrollTo((tamanho.getWidth()*(position-1))/SIZE,scroll.getScrollY());
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}

package br.com.JoinAndPlay;

import com.facebook.Session;
import com.google.android.gms.maps.MapsInitializer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.tabActive.TabFragment;

public class MainActivity extends FragmentActivity implements LocationListener  {
	protected TabFragment tabs;
	private boolean login=false;
	public static int VERSION=0;
	public static int SUB_VERSION=0;
	public static MainActivity self;
	LocationManager lManager;
	Location location ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self=this;
		setContentView(R.layout.main_active);
		MapsInitializer.initialize(this);
		//onAlertEvent();
		for (int i = 0; i <TabFragment.SIZE; i++) {
			Fragment fragment = getSupportFragmentManager().findFragmentByTag("tab"+(i+1));
			if (fragment != null)
				getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}



		tabs=(TabFragment) TabFragment.instantiate(this, TabFragment.class.getName());
		Fragment inicial=tabs;
		if(Session.getActiveSession()==null || Session.getActiveSession().isOpened()){
			inicial= new TelaInicialFragment();

		}
		getSupportFragmentManager().beginTransaction().replace(R.id.tela, inicial).commit();
		Fragment param = ListEventosFragment.instantiate(this, ListEventosFragment.class.getName(),savedInstanceState);
		tabs.addFragments(this,param,null,R.drawable.tab_lista);
		param=PesquisarEventosFragment.instantiate(this, PesquisarEventosFragment.class.getName(),savedInstanceState);
		tabs.addFragments(this,param,null,R.drawable.tab_pesq);
		param=NotificacaoFragment.instantiate(this, NotificacaoFragment.class.getName(),savedInstanceState);
		tabs.addFragments(this,param,(Runnable)param,R.drawable.tab_notif);
		param=AgendaEventosFragment.instantiate(this, AgendaEventosFragment.class.getName(),savedInstanceState);
		tabs.addFragments(this,param,null,R.drawable.tab_cal);
		param=PerfilAdminFragment.instantiate(this, PerfilAdminFragment.class.getName(),savedInstanceState);
		tabs.addFragments(this,param,null,R.drawable.tab_perfil);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession()
		.onActivityResult(this, requestCode, resultCode, data);

	}
	@Override
	public void onResume(){
		super.onResume();
		lManager = (LocationManager)getSystemService(FragmentActivity.LOCATION_SERVICE);
		location=lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location==null){
			location=lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 50, this);
		lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000, 50, this);
	}

	public void loadTela (int i){

		tabs.onloader(i);
	}
	public void popLoadTela(int i){

		tabs.onfinish(i);

	}
	void login(){

		getSupportFragmentManager().beginTransaction().replace(R.id.tela, tabs).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
		login=true;
	}
	public void retirarAbaAtual(){
		tabs.tabPop();
	}
	void mudarAba(int i){
		//apartir de uma aba chamar ((MainActivity)getActivity()).mudarAba(n)
		tabs.onPageSelected(i);
	}
	void mudarFragment(Fragment fm){
		// getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.tabmain)).add(R.id.tela, fm).commit();

	}
	public void mudarAbaAtual(Fragment fm){
		tabs.tabChange(fm,true);

	}
	public void mudarAba(int id,Fragment fm){
		tabs.tabChange(id,fm,true);
	}

	@Override
	public void onBackPressed() {
		if(!login || !tabs.onBackPressed()){

			super.onBackPressed();
		}
	}

	public void replaceTab(Fragment fm) {
		tabs.tabChange(fm,false);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	//manda um alerta para o usuario
	public void onAlertEvent (){
		Intent intent = new Intent(MainActivity.this,MainActivity.class);
		NewMessageNotification notification = new NewMessageNotification();
		notification.notify(this, "Nível de óleo baixo", "Toque para detalhes", R.drawable.basquete, "Alerta de risco veicular", intent, 0);
	}

}
package br.com.JoinAndPlay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;
import com.google.android.gms.maps.MapsInitializer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Transformation;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.ServiceHandler;
import br.com.tabActive.TabFragment;




public class MainActivity extends FragmentActivity {
	protected TabFragment tabs;
	public static int VERSION=0;
	public static int SUB_VERSION=0;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_active);
		MapsInitializer.initialize(this);
		for (int i = 0; i <TabFragment.SIZE; i++) {
			Fragment fragment = getSupportFragmentManager().findFragmentByTag("tab"+(i+1));
			if (fragment != null)
				getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}

		
		
		tabs=(TabFragment) TabFragment.instantiate(this, TabFragment.class.getName());
		Fragment inicial=tabs;
		Log.v("session", "");
		if(Session.getActiveSession()==null || Session.getActiveSession().isOpened()){
			inicial= new TelaInicialFragment();

		}
		getSupportFragmentManager().beginTransaction().replace(R.id.tela, inicial).commit();

	//	tabs.addFragments(this,NotificacaoFragment.instantiate(this, NotificacaoFragment.class.getName(),savedInstanceState),R.drawable.tab_notif);
		tabs.addFragments(this,ListEventosFragment.instantiate(this, ListEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_lista);
		tabs.addFragments(this,PesquisarEventosFragment.instantiate(this, PesquisarEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_pesq);
		tabs.addFragments(this,AgendaEventosFragment.instantiate(this, AgendaEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_cal);
		tabs.addFragments(this,PerfilUserFragment.instantiate(this, PerfilUserFragment.class.getName(),savedInstanceState),R.drawable.tab_perfil);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession()
		.onActivityResult(this, requestCode, resultCode, data);
		Log.v("tokenscc"," "+ 	    Session.getActiveSession()
				.getAccessToken());

		//MyThread t = new MyThread();
		//t.start();



		//	try {t.join();}catch(Exception _) {}
	}

	void login(){

		getSupportFragmentManager().beginTransaction().replace(R.id.tela, tabs).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

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
	void mudarAba(int id,Fragment fm){
		tabs.tabChange(id,fm,true);
	}

	@Override
	public void onBackPressed() {
		if(!tabs.onBackPressed()){

			super.onBackPressed();
		}
	}

	public void replaceTab(Fragment fm) {
		tabs.tabChange(fm,false);
	}



}
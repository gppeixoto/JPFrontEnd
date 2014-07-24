package br.com.JoinAndPlay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;

import android.support.v4.app.Fragment;
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
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.ServiceHandler;
import br.com.tabActive.TabFragment;




public class MainActivity extends FragmentActivity {
	protected TabFragment tabs;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_active);
		 for (int i = 0; i <TabFragment.SIZE; i++) {
	    	   Fragment fragment = getSupportFragmentManager().findFragmentByTag("tab"+(i+1));
	   	    if (fragment != null)
	   	    	getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}
		
		 
		tabs=(TabFragment) TabFragment.instantiate(this, TabFragment.class.getName());
		getSupportFragmentManager().beginTransaction().replace(R.id.tela, tabs).commit();

		tabs.addFragments(this,NotificacaoFragment.instantiate(this, NotificacaoFragment.class.getName(),savedInstanceState),R.drawable.tab_notif);
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
    	Log.v("token"," "+ 	    Session.getActiveSession()
.getAccessToken());
    	
    	//MyThread t = new MyThread();
    	//t.start();
    	Server.login(Session.getActiveSession().getAccessToken());
    //	try {t.join();}catch(Exception _) {}
	}
	
	class MyThread extends Thread {
		public void run() {
			ServiceHandler sh = new ServiceHandler();
			JSONObject obj = new JSONObject();
			try {
				obj.put("access_token", Session.getActiveSession().getAccessToken());
			} catch (Exception _) {}
			sh.makePOST(ServiceHandler.URL_BASE + "login/", obj.toString());
		}
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

	
	
}
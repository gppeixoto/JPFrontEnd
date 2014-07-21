package br.com.JoinAndPlay;

import com.facebook.Session;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import br.com.tabActive.TabFragment;




public class MainActivity extends FragmentActivity {
	protected TabFragment tabs;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_active);
		tabs=(TabFragment) TabFragment.instantiate(this, TabFragment.class.getName());
		getSupportFragmentManager().beginTransaction().replace(R.id.tela, tabs).commit();

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

	}

	void mudarAba(int i){
		//apartir de uma aba chamar ((MainActivity)getActivity()).mudarAba(n)
		tabs.onPageSelected(i);
	}
	void mudarFragment(Fragment fm){
		// getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.tabmain)).add(R.id.tela, fm).commit();
		
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
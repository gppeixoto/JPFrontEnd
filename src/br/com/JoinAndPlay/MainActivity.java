package br.com.JoinAndPlay;

import br.com.tabActive.TabFragment;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost.TabSpec;



public class MainActivity extends FragmentActivity {
	//pegou porra
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_active);
		TabFragment tabs =(TabFragment) getSupportFragmentManager().findFragmentById(R.id.tabmain);
		tabs.addFragments(ListEventosFragment.instantiate(this, ListEventosFragment.class.getName(),savedInstanceState), "jogue");
		tabs.addFragments(PesquisarEventosFragment.instantiate(this, PesquisarEventosFragment.class.getName(),savedInstanceState),"encontre");
		tabs.addFragments(AgendaEventosFragment.instantiate(this, AgendaEventosFragment.class.getName(),savedInstanceState),"agenda");
		tabs.addFragments(PerfilUserFragment.instantiate(this, PerfilUserFragment.class.getName(),savedInstanceState),"perfil");
		
	
	}
	
	
}

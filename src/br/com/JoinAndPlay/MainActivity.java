package br.com.JoinAndPlay;

import br.com.tabActive.TabFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost.TabSpec;
import com.facebook.*;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;



public class MainActivity extends FragmentActivity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_active);
		TabFragment tabs =(TabFragment) getSupportFragmentManager().findFragmentById(R.id.tabmain);
		tabs.addFragments(ListEventosFragment.instantiate(this, ListEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_lista);
		tabs.addFragments(PesquisarEventosFragment.instantiate(this, PesquisarEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_pesq);
		tabs.addFragments(AgendaEventosFragment.instantiate(this, AgendaEventosFragment.class.getName(),savedInstanceState),R.drawable.tab_cal);
		tabs.addFragments(PerfilUserFragment.instantiate(this, PerfilUserFragment.class.getName(),savedInstanceState),R.drawable.tab_perfil);
	}
	
	void mudarFragment(Fragment fm){
		 getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.tabmain)).add(R.id.tela, fm).commit();
		
	}
	
}
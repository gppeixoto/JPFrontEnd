package br.com.JoinAndPlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PerfilUserFragment extends Fragment {
	 RelativeLayout ret;
	 //ola
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		 ret=(RelativeLayout) inflater.inflate(R.layout.tab_layout_perfil, container, false);
		
		// Inflamos o layout tab_layout_a
		return ret;
	}


}

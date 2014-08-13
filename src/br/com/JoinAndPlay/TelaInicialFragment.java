package br.com.JoinAndPlay;

import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TelaInicialFragment extends Fragment implements Connecter<Usuario>, OnClickListener{
	private Button inicial_button_facebookLogin;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_inicial, container,false);
		inicial_button_facebookLogin = (Button) view.findViewById(R.id.inicial_button_facebook_login);
		inicial_button_facebookLogin.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onTerminado(Usuario in) {
		((MainActivity)getActivity()).login();
	}

	@Override
	public void onClick(View v) {
		Server.user_profile(getActivity(), this);
	}

}

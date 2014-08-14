package br.com.JoinAndPlay;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;

import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

		if(getView()!=null){
			((MainActivity)getActivity()).login();

		}



	}

	@Override
	public void onClick(View v) {
				Server.user_profile(getActivity(), this);
	}

}

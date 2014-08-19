package br.com.JoinAndPlay;


import com.facebook.Session;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.tabActive.loadFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TelaInicialFragment extends Fragment implements Connecter<Usuario>, OnClickListener,Runnable{
	private Button inicial_button_facebookLogin;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_inicial, container,false);
		inicial_button_facebookLogin = (Button) view.findViewById(R.id.inicial_button_facebook_login);
		inicial_button_facebookLogin.setOnClickListener(this);
		view.post(this);
		return view;
	}


	@Override
	public void onTerminado(Usuario in) {

		if(getView()!=null){
			((MainActivity)getActivity()).login();
		}



	}
	public void load (){
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft=ft.add(R.id.tela,new loadFragment());
		ft.addToBackStack(null);
		ft.commit();

	}

	@Override
	public void onClick(View v) {
		final	TelaInicialFragment self=this;
	//	load();
		ConfigJP.login(getActivity(), new Connecter<String>() {

			@Override
			public void onTerminado(String in) {
				// TODO Auto-generated method stub
				Server.user_profile(self.getActivity(), self);
			}

		});
	}
	int i=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(getView()!=null){
			if(Session.getActiveSession()!=null ){
				if(Session.getActiveSession().isOpened()){
			//		load();
					onTerminado(null);
				}else{
					onClick(null);
				}

				return;
			}
			getView().postDelayed(this, i*10);
			if(i<1000)i++;
		}

	}

}

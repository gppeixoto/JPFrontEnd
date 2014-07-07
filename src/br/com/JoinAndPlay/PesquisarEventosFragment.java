package br.com.JoinAndPlay;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PesquisarEventosFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		return v;
	}
}
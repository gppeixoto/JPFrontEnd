package br.com.JoinAndPlay;

import android.widget.Button;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CriarEventosFragment extends Fragment implements OnClickListener {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(container==null) return null;
		View ret = inflater.inflate(R.layout.criar_evento, container,false);
			
		Button bg = (Button) ret.findViewById(R.id.bigButton);
		bg.setText("Pr�ximo");
		
		Button bg2 = (Button) ret.findViewById(R.id.botao_escolha_lugar);
		bg2.setText("N�o sabe onde jogar?\nClique aqui e pesquise locais pr�ximos");
		
		Button b2 = (Button) ret.findViewById(R.id.buttonDataInicio);
		b2.setText("In�cio");
		
		Button b3 = (Button) ret.findViewById(R.id.buttonDataFim);
		b3.setText("T�rmino");
		
		bg.setOnClickListener(this);
		bg2.setOnClickListener(this);
		
		return ret;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ChoosePlaceFragment choose = new ChoosePlaceFragment();
		((MainActivity)getActivity()).mudarAbaAtual(choose);
	}
}
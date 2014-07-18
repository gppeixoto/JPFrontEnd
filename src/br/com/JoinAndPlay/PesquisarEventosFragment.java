package br.com.JoinAndPlay;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class PesquisarEventosFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		ImageButton btn = (ImageButton) v.findViewById(R.id.pesquisar);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).mudarAba(0);
			}
		});

		Button bg = (Button) v.findViewById(R.id.bigButton);
		bg.setText("Pesquisar");
		
		bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button b = (Button) v.findViewById(R.id.bigButton);
				//b.setTextColor(getResources().getColor(R.color.red));
				//b.setTextColor(getResources().getColor(R.color.white));
			}
		});
		
		return v;
	}
	
}
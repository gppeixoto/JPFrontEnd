package br.com.JoinAndPlay;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PesquisarEventosFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		Button bg = (Button) v.findViewById(R.id.bigButton);
		bg.setText("Pesquisar");
		
		Button b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		b2.setText("Início");
		
		Button b3 = (Button) v.findViewById(R.id.buttonDataFim);
		b3.setText("Fim");
		
		bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).mudarAba(0);
				//Necessário obter especificação da designer sobre animações ao clicar nos botões.
				//Button b = (Button) v.findViewById(R.id.bigButton);
				//b.setTextColor(getResources().getColor(R.color.red));
				//b.setTextColor(getResources().getColor(R.color.white));
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});
		
		return v;
	}

	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
}
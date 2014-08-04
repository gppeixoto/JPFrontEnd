package br.com.JoinAndPlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CriarEventosCompFragment extends Fragment implements OnItemClickListener {
	
	private Button bCriarEvento;
	
	private EditText eNomeEvento;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		
		View view = inflater.inflate(R.layout.criar_evento_comp, container,false);
		
		bCriarEvento = (Button) view.findViewById(R.id.criar_evento_button);
		bCriarEvento.setText("Criar Evento");
		bCriarEvento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// criar evento
			}
		});
		
		eNomeEvento = (EditText) view.findViewById(R.id.escolha_nome_evento);
		
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}

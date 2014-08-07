package br.com.JoinAndPlay;

import android.graphics.drawable.Drawable;
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
	private Button bParticular;
	private Button bPublico;
	
	private Drawable red;
	private Drawable gray;
	
	private EditText eNomeEvento;
	
	private boolean privado;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		if(container==null) return null;
		
		privado = true;
		
		View view = inflater.inflate(R.layout.criar_evento_comp, container,false);
		
		gray = getResources().getDrawable(R.drawable.gray_button);
		red = getResources().getDrawable(R.drawable.red_button);
		
		eNomeEvento = (EditText) view.findViewById(R.id.escolha_nome_evento);
		
		bCriarEvento = (Button) view.findViewById(R.id.criar_evento_button);
		bCriarEvento.setText("Criar Evento");
		
		bParticular = (Button) view.findViewById(R.id.particular);
		bParticular.setText("Particular");
		bParticular.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				privado = true;	
			}
		});
		
		bPublico = (Button) view.findViewById(R.id.publico);
		bPublico.setText("Público");
		bPublico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				privado = false;	
			}
		});
		
		bCriarEvento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// verificar se dados estao completos
				// criar evento com esses dados
			}
		});
		
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}

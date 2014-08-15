package br.com.JoinAndPlay.Event;

import java.util.Iterator;
import java.util.zip.Inflater;

import org.joda.time.DateTime;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.CriarEventosFragment;
import br.com.JoinAndPlay.ListEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

public class EditEvent extends Fragment {
	private Evento myEvent=null;
	SupportMapFragment suportMap;
	public LinearLayout list;
	public LayoutInflater inf;
	private boolean privado;
	private Button privadoE;
	private Button publicoE;
	private Configuration config;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
	
	public void paintButton(Button red,Button gray){
		red.setBackgroundResource(R.drawable.red_button);
		red.setPadding(10, 10, 10, 10);
		gray.setBackgroundResource(R.drawable.gray_button);
		gray.setPadding(10, 10, 10, 10);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapsInitializer.initialize(getActivity());

		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.editar_evento, container, false);
		inf = inflater;
		if(getArguments()!=null && v!=null){
			final Bundle args = getArguments();
			String nome_evento = args.getString("nome_evento");
			String descricao_evento = args.getString("descricao_evento");
			String bairro = args.getString("bairro");
			String cidade = args.getString("cidade");
			privado = args.getBoolean("privacidade");
			final Double valor = args.getDouble("preco");
			String dia = args.getString("dia");
			String hora_begin = args.getString("hora_begin");
			String hora_end = args.getString("hora_end");
			String rua = args.getString("rua");
			final String esporte = args.getString("esporte");
			final String id_evento = args.getString("id_evento");
			final String local_name = args.getString("local_name");
			
			final EditText nome_eventoE = (EditText) v.findViewById(R.id.edit_nome_evento);
			nome_eventoE.setText(nome_evento);
			final EditText descricao_eventoE = (EditText) v.findViewById(R.id.edit_descricao_evento);
			descricao_eventoE.setText(descricao_evento);
			final EditText bairroE = (EditText) v.findViewById(R.id.editar_bairro);
			bairroE.setText(bairro);
			final EditText cidadeE = (EditText) v.findViewById(R.id.editar_cidade);
			cidadeE.setText(cidade);
			final EditText ruaE = (EditText) v.findViewById(R.id.editar_rua);
			ruaE.setText(rua);
			final EditText preco = (EditText) v.findViewById(R.id.editar_preco);
			preco.setText(valor.toString());
			
			//Setar esporte (?)		
			
			privadoE = (Button) v.findViewById(R.id.botao_privado);
			publicoE = (Button) v.findViewById(R.id.botao_publico);
			
			if(privado == true) paintButton(privadoE, publicoE);
			else paintButton(publicoE,privadoE);
			
			privadoE.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					paintButton(privadoE, publicoE);
					privado = true;
				}
			});
			publicoE.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					paintButton(publicoE,privadoE);
					privado = false;
				}
			});
			final Button diaE = (Button) v.findViewById(R.id.editar_dia);
			config = getActivity().getResources().getConfiguration();
			diaE.setText(dia);
			
			final Button horabE = (Button) v.findViewById(R.id.editar_hora_inicial);
			horabE.setText(hora_begin);
			final Button horaeE = (Button) v.findViewById(R.id.editar_hora_final);
			horaeE.setText(hora_end);
			
			Button confirmar_edicao = (Button) v.findViewById(R.id.botao_confirmarEdicao);
			confirmar_edicao.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.v("EDITAAAAAAA", "=/");
					Server.edit_event(getActivity(), local_name, ruaE.getText().toString(), cidadeE.getText().toString(), bairroE.getText().toString(), esporte, diaE.getText().toString(), horabE.getText().toString(), horaeE.getText().toString(), descricao_eventoE.getText().toString(), nome_eventoE.getText().toString(), valor, privado, id_evento, null);
					EventFragment next = new EventFragment();
					Bundle args = new Bundle();
					args.putString("evento", id_evento);
					next.setArguments(args);
					((MainActivity)getActivity()).mudarAbaAtual(next);
				}
			});
		}
		return  v;
	}
}

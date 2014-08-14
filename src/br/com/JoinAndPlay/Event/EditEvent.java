package br.com.JoinAndPlay.Event;

import java.util.Iterator;
import java.util.zip.Inflater;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.ListEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapsInitializer.initialize(getActivity());

		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.editar_evento, container, false);
		inf = inflater;
		//Button b = (Button)v.findViewById(R.id.button1);
		//EditText edit = (EditText)v.findViewById(R.id.criar_comentario);
		//b.setOnClickListener(this);
		//edit.setOnKeyListener(this);
		if(getArguments()!=null && v!=null){
			Bundle args = getArguments();
			String nome_evento = args.getString("nome_evento");
			String descricao_evento = args.getString("descricao_evento");
			String bairro = args.getString("bairro");
			String cidade = args.getString("cidade");
			Boolean privado = args.getBoolean("privacidade");
			Double valor = args.getDouble("preco");
			
			EditText nome_eventoE = (EditText) v.findViewById(R.id.edit_nome_evento);
			nome_eventoE.setText(nome_evento);
			EditText descricao_eventoE = (EditText) v.findViewById(R.id.edit_descricao_evento);
			descricao_eventoE.setText(descricao_evento);
			EditText bairroE = (EditText) v.findViewById(R.id.editar_bairro);
			bairroE.setText(bairro);
			EditText cidadeE = (EditText) v.findViewById(R.id.editar_cidade);
			cidadeE.setText(cidade);
			EditText preco = (EditText) v.findViewById(R.id.editar_preco);
			preco.setText(valor.toString());
		}
		return  v;
	}
}

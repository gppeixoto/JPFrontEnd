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

import br.com.JoinAndPlay.ListEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagemAsyncTask;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class EventFragment extends Fragment implements OnClickListener, Connecter<Evento>{
	private Evento myEvent=null;
	SupportMapFragment suportMap;
	public LinearLayout list;
	public LayoutInflater inf;

	public void addComment(String nome,String time,String novo_comentario){
		View novo = inf.inflate(R.layout.add_comentario, (ViewGroup)getView(), false);
		TextView nome_usuario = (TextView)novo.findViewById(R.id.nome_usuario);
		TextView tempo_decorrido  = (TextView)novo.findViewById(R.id.tempo_decorrido);
		TextView comentario_texto = (TextView)novo.findViewById(R.id.comentario_texto);
		comentario_texto.setText(novo_comentario);
		tempo_decorrido.setText(time);
		nome_usuario.setText(nome);
		list.addView(novo);
	}

	/*@Override
	public boolean onKey(View view, int keyCode, KeyEvent event) {
Log.v("Digitou uma tecla!!!!","key ="+event.getKeyCode());
		//TextView responseText = (TextView) findViewById(R.id.responseText);
		EditText myEditText = (EditText) view;

		if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
				keyCode == EditorInfo.IME_ACTION_DONE ||
				event.getAction() == KeyEvent.ACTION_DOWN &&
				event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

			if (!event.isShiftPressed()) {
				if(myEvent!=null){
					final Connecter<Evento> listener = this;
					Log.v("COMEN", myEditText.getText().toString());
					Server.comment(getActivity(),myEvent.getId(),myEditText.getText().toString(), new Connecter<Usuario>() {
						@Override
						public void onTerminado(Usuario in) {
							// TODO Auto-generated method stub
							Server.get_detailed_event(getActivity(),myEvent.getId(),listener);	

						}
					});
				}				
				myEditText.getText().clear();
				myEditText.clearFocus();
                if(getView() != null){
					getView().setFocusable(true);
		            getView().setFocusableInTouchMode(true);
		            getView().requestFocus();
				}
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
				return true;
			}               
		}
		return false; 
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapsInitializer.initialize(getActivity());

		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.event_fragment, container, false);
		list = (LinearLayout)v.findViewById(R.id.lista_comentarios);
		inf = inflater;
		Button b = (Button)v.findViewById(R.id.button1);
		//EditText edit = (EditText)v.findViewById(R.id.criar_comentario);
		b.setOnClickListener(this);
		//edit.setOnKeyListener(this);
		suportMap= new SupportMapFragment();
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_frag, suportMap).commit();
		if(getArguments()!=null){
			Server.get_detailed_event(getActivity(),getArguments().getString("evento"),this);	
		}
		return  v;
	}
	@Override
	public void onActivityCreated( Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getView().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GoogleMap map = suportMap.getMap();
				if(map!=null){
					LatLng target= new LatLng( -7.9900227  , -34.83929520000004);
					CameraPosition cameraPosition = new CameraPosition.Builder().target(target).zoom(12).build();
					map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
					MarkerOptions marker = new MarkerOptions();
					marker.position(target).title("Iateclub");
					map.addMarker(marker);
				}
			}
		});
	}

	public static String parseMonth(int n){
		if(n == 1) return "Janeiro";
		else if (n == 2) return "Fevereiro";
		else if (n == 3) return "Mar�o";
		else if (n == 4) return "Abril";
		else if (n == 5) return "Maio";
		else if (n == 6) return "Junho";
		else if (n == 7) return "Julho";
		else if (n == 8) return "Agosto";
		else if (n == 9) return "Setembro";
		else if (n == 10) return "Outubro";
		else if (n == 11) return "Novembro";
		else if (n == 12) return "Dezembro";
		else return "fail";
	}

	public void setValuesEvent(final View view,Evento evento){
		if(evento == null || view==null) return;
		TextView descricao_horario = (TextView)view.findViewById(R.id.descricao_horario);
		TextView descricao_local = (TextView)view.findViewById(R.id.descricao_local);

		TextView qtd_confirmados = (TextView)view.findViewById(R.id.qtd_confirmados);
		//TextView qtd_no_local = (TextView)view.findViewById(R.id.qtd_no_local);

		LinearLayout pessoas = (LinearLayout)view.findViewById(R.id.imagem_perfil_dad);

		TextView qtd_amigos_amais = (TextView)view.findViewById(R.id.qtd_amigos_amais);

		ImageView imagem_da_partida = (ImageView)view.findViewById(R.id.imagem_da_partida);
		TextView tipo_da_partida = (TextView)view.findViewById(R.id.tipo_da_partida);	

		TextView descricao_do_esporte = (TextView)view.findViewById(R.id.descricao_do_esporte);

		String data = evento.getDate();
		String dia = data.substring(0, 2);
		data = parseMonth(((int)(data.charAt(3)-'0'))*10 + ((int)(data.charAt(4)-'0')));
		descricao_horario.setText(dia + " de " + data + " as " + evento.getStartTime() + " horas");
		descricao_local.setText(evento.getLocalizationName()+"\n"+evento.getLocalizationAddress());

		qtd_confirmados.setText(""+evento.getUsers().size());
		//	qtd_no_local(evento.);
		
		Button butao_enviar = (Button)view.findViewById(R.id.enviar_comentario);
		final Connecter<Evento> listener = this;
		butao_enviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText myEditText = (EditText) view.findViewById(R.id.criar_comentario);
				//Log.v("COMEN", myEditText.getText().toString());
				Server.comment(getActivity(),myEvent.getId(),myEditText.getText().toString(), new Connecter<Comentario>() {
					@Override
					public void onTerminado(Comentario in) {
						// TODO Auto-generated method stub
						Server.get_detailed_event(getActivity(),myEvent.getId(),listener);	

					}
				});
				myEditText.getText().clear();
				myEditText.clearFocus();
                if(getView() != null){
					getView().setFocusable(true);
		            getView().setFocusableInTouchMode(true);
		            getView().requestFocus();
				}
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
			}
		});

		
		
		for (int i = 0; i < Math.min(evento.getUsers().size(),ItemEvent.MAX_AMIGOS_QTD); i++) {
			if(pessoas.getChildCount()-1>i){
				ImageView imagem = (ImageView) pessoas.getChildAt(i);

				new DownloadImagemAsyncTask(view.getContext(),imagem).execute(evento.getUsers().get(i).getPhoto());
			}else break;
		}		

		for (int i = evento.getUsers().size(); i <ItemEvent.MAX_AMIGOS_QTD; i++) {
			if(pessoas.getChildCount()-1>i){
				ImageView imagem = (ImageView) pessoas.getChildAt(i);
				imagem.setVisibility(View.INVISIBLE);
			}else break;
		}
		if(evento.getComments()!=null)
			for (Iterator<Comentario> iterator = evento.getComments().iterator(); iterator.hasNext();) {
				Comentario coment = (Comentario) iterator.next();
				addComment(coment.getUserName(),"0m",coment.getText());

			}

		qtd_amigos_amais.setText("+ " + (evento.getNumFriends() > 6 ? evento.getUsers().size() - 6 : 0) + " amigo" + (evento.getNumFriends() > 7 ? "s" : ""));
		tipo_da_partida.setText(evento.getSport());
		descricao_do_esporte.setText(evento.getDescription());
		view.requestLayout();
		view.postInvalidate();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//((MainActivity)getActivity()).mudarAbaAtual(new ListEventosFragment());
		if(myEvent!=null)
			Server.enter_event(getActivity(), myEvent.getId(),this );
	}

	@Override
	public void onTerminado(Evento in) {
		// TODO Auto-generated method stub
		if(in!=null ){
			myEvent=in;
			if(getView()!=null){
				getView().post(new Runnable() {
					public void run() {
						setValuesEvent(getView(), myEvent);
						getView().requestLayout();
						getView().invalidate();

					}
				});
			}
		}
	}

}

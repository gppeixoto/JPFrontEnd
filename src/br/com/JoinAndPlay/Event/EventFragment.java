package br.com.JoinAndPlay.Event;


import java.util.Iterator;
import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventFragment extends Fragment implements OnClickListener, Connecter<Evento>{
	private Evento myEvent=null;
	public LinearLayout list;
	public LayoutInflater inf;

	public void addComment(String nome,String time,String novo_comentario,String photo){
		View novo = inf.inflate(R.layout.add_comentario, (ViewGroup)getView(), false);
		ImageView foto_usuario = (ImageView)novo.findViewById(R.id.perfil_imagem_usuario);
		TextView nome_usuario = (TextView)novo.findViewById(R.id.nome_usuario);
		TextView tempo_decorrido  = (TextView)novo.findViewById(R.id.tempo_decorrido);
		TextView comentario_texto = (TextView)novo.findViewById(R.id.comentario_texto);
		comentario_texto.setText(novo_comentario);
		tempo_decorrido.setText(time);
		nome_usuario.setText(nome);
		DownloadImagem.postLoad(foto_usuario,photo);
		list.addView(novo,0);
	}

	public void paintRed(Button red){
		red.setBackgroundResource(R.color.red);
		red.setPadding(10, 10, 10, 10);
	}
	public void paintGray(Button gray){
		gray.setBackgroundResource(R.color.cinza_cinza);
		gray.setPadding(10, 10, 10, 10);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {


		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.event_fragment, container, false);
		list = (LinearLayout)v.findViewById(R.id.lista_comentarios);
		inf = inflater;

		if(getArguments()!=null){
			Server.get_detailed_event(getActivity(),getArguments().getString("evento"),this);	
		}
		return  v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

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

	public void setValuesEvent(final View view,final Evento evento){
		if(evento == null || view==null) return;

		Button participar = (Button)view.findViewById(R.id.button1);
		LinearLayout layout_evento = (LinearLayout)view.findViewById(R.id.linear_evento);
		ImageView imagem_evento = (ImageView)view.findViewById(R.id.imagem_evento);
		int id_esporte = ConfigJP.getEsporteID(evento.getSport());
		layout_evento.setBackgroundResource(ConfigJP.ESPORTE_BARRA[id_esporte]);
		imagem_evento.setImageResource(ConfigJP.ESPORTE_BITMAP[id_esporte]);

		TextView descricao_horario = (TextView)view.findViewById(R.id.descricao_horario);
		TextView descricao_local = (TextView)view.findViewById(R.id.descricao_local);

		TextView qtd_confirmados = (TextView)view.findViewById(R.id.qtd_confirmados);
		TextView qtd_no_local = (TextView)view.findViewById(R.id.qtd_no_local);

		LinearLayout pessoas = (LinearLayout)view.findViewById(R.id.nova_foto); 

		TextView tipo_da_partida = (TextView)view.findViewById(R.id.tipo_da_partida);	

		TextView descricao_do_esporte = (TextView)view.findViewById(R.id.descricao_do_esporte);

		String data = evento.getDate();
		String dia = data.substring(0, 2);
		data = parseMonth(((int)(data.charAt(3)-'0'))*10 + ((int)(data.charAt(4)-'0')));
		descricao_horario.setText(dia + " de " + data + " as " + evento.getStartTime() + " horas");
		descricao_local.setText(evento.getLocalizationName()+" - "+evento.getCity()+"\n"+evento.getLocalizationAddress()+", "+evento.getNeighbourhood());

		qtd_confirmados.setText(""+evento.getUsers().size());
		qtd_no_local.setText(evento.getAtEvent().size()+"");

		Button estou_no_local = (Button) view.findViewById(R.id.estou_no_local);
		final EventFragment self = this;
		if(evento.getHasArrived()) estou_no_local.setText("Sai do local");
		else estou_no_local.setText("Estou aqui");
		estou_no_local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.v("HAS ARRIVED",evento.getHasArrived()+"");
				if(evento.getHasArrived()){
					Server.cancel_arrive(self.getActivity(), evento.getId(), self);
				}else{
					Server.arrive_event(self.getActivity(), evento.getId(), self);
				}
			}
		});

		LinearLayout showConfirmed = (LinearLayout) view.findViewById(R.id.visualizar_confirmados);
		LinearLayout showLocal = (LinearLayout) view.findViewById(R.id.visualizar_local);
		showConfirmed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		showLocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		TextView butao_terminar = (TextView)view.findViewById(R.id.botao_finalizar);
		///VERIFICAR AQUI O USERID!
		if(evento.getCreatorId().equals(ConfigJP.UserId)){
			participar.setText("Editar Evento");
			paintRed(participar);
			participar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditEvent next = new EditEvent();
					Bundle args = new Bundle();				

					args.putString("nome_evento", evento.getName());
					args.putString("descricao_evento", evento.getDescription());
					args.putString("bairro", evento.getNeighbourhood());
					args.putString("cidade", evento.getCity());
					args.putBoolean("privacidade", evento.getPrivacy());
					args.putDouble("preco",evento.getPrice());
					args.putString("dia", evento.getDate());
					args.putString("hora_begin", evento.getStartTime());
					args.putString("hora_end",evento.getEndTime());
					args.putString("rua", evento.getLocalizationAddress());
					args.putString("esporte",evento.getSport());
					args.putString("id_evento", evento.getId());
					args.putString("local_name", evento.getLocalizationName());

					next.setArguments(args);
					((MainActivity)getActivity()).mudarAbaAtual(next);	
				}
			});
			butao_terminar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView myText = (TextView) v;
					myText.setText("Jogo Finalizado");
					Server.close_event(evento.getId(), null);
				}
			});
		}else{
			if(evento.getIsParticipating()){
				participar.setText("Deixar evento");
				paintGray(participar);
			}
			else{
				participar.setText("Participar");
				paintRed(participar);
			}
			participar.setOnClickListener(this);
			butao_terminar.setVisibility((View.INVISIBLE));
		}

		Button butao_enviar = (Button)view.findViewById(R.id.enviar_comentario);
		final Connecter<Evento> listener = this;
		butao_enviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText myEditText = (EditText) view.findViewById(R.id.criar_comentario);
				Server.comment(getActivity(),myEvent.getId(),myEditText.getText().toString(), new Connecter<Comentario>() {
					@Override
					public void onTerminado(Comentario in) {
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

		
		LinearLayout.LayoutParams nome_legal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1); 
		for (int i = 0; i < evento.getUsers().size(); i++) {
			ImageView imagem;
			imagem = new ImageView(getActivity());
			DownloadImagem.postLoad(imagem,	evento.getUsers().get(i).getPhoto());
			imagem.setPadding(3, 0, 0, 0);
			imagem.setLayoutParams(nome_legal);
			pessoas.addView(imagem);
		}		
		
		for (int i = evento.getUsers().size(); i <pessoas.getChildCount(); i++) {
			ImageView imagem = (ImageView) pessoas.getChildAt(i);
			imagem.setVisibility(View.INVISIBLE);
		}
		if(evento.getComments()!=null){
			for (Iterator<Comentario> iterator = evento.getComments().iterator(); iterator.hasNext();) {
				Comentario coment = (Comentario) iterator.next();
				addComment(coment.getUserName(),coment.getHour(),coment.getText(),coment.getPhoto());
			}
		}
		tipo_da_partida.setText(evento.getSport());
		descricao_do_esporte.setText(evento.getDescription());
		double[] temp=null;

		Log.v("lat", evento.getLatitude()+" "+evento.getLatitude());
		try{
			temp=new double[2];

			temp[0]	=Double.parseDouble(evento.getLatitude());		
			temp[1]	=Double.parseDouble(evento.getLongitude());	
		}catch(Exception _){
			temp=ConfigJP.getLatLngFromAddress(getActivity(),evento.getLocalizationName()+","+evento.getLocalizationAddress()+","+evento.getNeighbourhood()+","+evento.getCity());
			if(temp==null)
				temp=ConfigJP.getLatLngFromAddress(getActivity(),evento.getLocalizationAddress()+","+evento.getNeighbourhood()+","+evento.getCity());
			if(temp!=null){
				final double[] cordenada= temp;
				ConfigJP.getToken(getActivity(), new Connecter<String>() {

					@Override
					public void onTerminado(String access_token) {
						Server.edit_event(access_token, evento.getLocalizationName(),evento.getLocalizationAddress(), evento.getCity(), evento.getNeighbourhood(), evento.getSport(), evento.getDate(), evento.getStartTime(), evento.getEndTime(), evento.getDescription(), evento.getName(), evento.getPrice(), evento.getPrivacy(), evento.getId(), cordenada[0]+"", cordenada[1]+"",null);
					}
				});
			}

		}

		mapaFragment mapaF = new mapaFragment();
		Bundle arg= new Bundle();
		arg.putString("nome", evento.getName());
		arg.putBoolean("isMax", false);
		arg.putString("local", evento.getLocalizationName());
		arg.putDoubleArray("latlng", temp);
		mapaF.setArguments(arg);
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_frag, mapaF).commit();
		view.requestLayout();
		view.postInvalidate();
	}

	@Override
	public void onClick(View v) {
		if(myEvent!=null){
			if(myEvent.getIsParticipating()==false) Server.enter_event(getActivity(), myEvent.getId(),this );
			else Server.leave_event(getActivity(), myEvent.getId(),this );
			EventFragment next = new EventFragment();
			Bundle args = new Bundle();
			args.putString("evento", myEvent.getId());
			next.setArguments(args);
			((MainActivity)getActivity()).replaceTab(next);
		}
	}

	@Override
	public void onTerminado(final Evento in) {
		if(in!=null ){
			myEvent=in;
			if(getView()!=null){
				final EventFragment self = this;
				getView().post(new Runnable() {
					public void run() {
						if(ConfigJP.UserId == null){
							ConfigJP.getUserID(getActivity(), new Connecter<String>() {
								@Override
								public void onTerminado(String id) {
									ConfigJP.UserId=id;
									self.onTerminado(in);
								}
							});
						}else{
							setValuesEvent(getView(), myEvent);
							getView().requestLayout();
							getView().invalidate();
						}
					}
				});
			}
		}
	}

}
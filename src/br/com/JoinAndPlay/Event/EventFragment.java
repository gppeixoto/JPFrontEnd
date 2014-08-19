package br.com.JoinAndPlay.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.CriarEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.PerfilUserFragment;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Tag;
import br.com.JoinAndPlay.Server.Usuario;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
	private boolean award[];
	private boolean cantVote[];
	private double rating;
	private int ind;
	private String badges[];
	private ImageView award1;
	private ImageView award2;
	private ImageView award3;
	private ImageView award4;
	static int ID=0;
	private HashMap<String, Boolean>  map;
	private boolean hasUser;

	public void reloadPage(){
		EventFragment next = new EventFragment();
		Bundle args = new Bundle();
		args.putString("evento", myEvent.getId());
		args.putInt("idTab",ID);
		next.setArguments(args);
		((MainActivity)getActivity()).replaceTab(next);
	}
	
	public void initBadges(ImageView foto_doidinho,Evento evento,TextView nome_doidinho){
		DownloadImagem.postLoad(foto_doidinho,evento.getUsers().get(ind).getPhoto());
		nome_doidinho.setText(evento.getUsers().get(ind).getName());
		Vector<Tag> tags = evento.getUsers().get(ind).getTags();
		for(int i=0;i<4;i++) award[i] = false;
		for (Tag tag : tags){
			String NOME = tag.getName();
			if (NOME.equals("Gente Boa")) award[0]=true;
			else if (NOME.equals("Fair Play")) award[3]=true;							
			else if (NOME.equals("Esforcado")) award[1]=true;
			else if (NOME.equals("Joga Pro Time")) award[2]=true;
		}
		if(award[0]==false) award1.setImageResource(R.drawable.icongenteboa);
		else award1.setImageResource(R.drawable.okaygenteboa);
		if(award[1]==false) award2.setImageResource(R.drawable.iconesforcado);
		else award2.setImageResource(R.drawable.okayesforcado);
		if(award[2]==false) award3.setImageResource(R.drawable.iconjogaprotime);
		else award3.setImageResource(R.drawable.okayjogaprotime);
		if(award[3]==false) award4.setImageResource(R.drawable.iconfairplay);
		else award4.setImageResource(R.drawable.okayfairplay);
		for(int i=0;i<4;i++) cantVote[i]=award[i];
	}

	public void addGuys(LinearLayout pessoas,final Evento evento){
		for (int i = 0; i < evento.getUsers().size(); i++) {
			ImageView imagem;
			imagem = (ImageView) inf.inflate( R.layout.image_foto,pessoas ,false);
			DownloadImagem.postLoad(imagem,	evento.getUsers().get(i).getPhoto());
			pessoas.addView(imagem);
			final int index=i;
			imagem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					PerfilUserFragment fm = new  PerfilUserFragment();
					Bundle arg = new Bundle();
					arg.putInt("idTab",ID);
					arg.putString("idUser",evento.getUsers().get(index).getId());
					fm.setArguments(arg);
					((MainActivity)getActivity()).mudarAbaAtual(fm);
				}
			});
		}
	}

	public void addComment(String nome,String hora,String data,String novo_comentario,String photo,boolean ups){
		String putTime;
		putTime="há ";
		if(ups==false) putTime = putTime + "0 minutos";
		else{
			if(data.equals("0")){
				int j=0;
				int hour=0;
				while(hora.charAt(j)!=':'){
					hour*=10;
					hour += (hora.charAt(j++) - '0');
				}
				if(hour != 0){
					putTime = putTime + (hour+"") + " ";
					if(hour==1) putTime = putTime + " hora";
					else putTime = putTime + " horas";
				}
				else{
					hour=0;
					j++;
					while(j < hora.length()){
						hour*=10;
						hour += (hora.charAt(j++) - '0');
					}
					putTime = putTime + (hour+"") + " ";
					if(hour==1) putTime = putTime + "minuto";
					else putTime = putTime + "minutos";
				}
			}else putTime = putTime + data + " dias";
		}
		View novo = inf.inflate(R.layout.add_comentario, (ViewGroup)getView(), false);
		ImageView foto_usuario = (ImageView)novo.findViewById(R.id.perfil_imagem_usuario);
		TextView nome_usuario = (TextView)novo.findViewById(R.id.nome_usuario);
		TextView tempo_decorrido  = (TextView)novo.findViewById(R.id.tempo_decorrido);
		TextView comentario_texto = (TextView)novo.findViewById(R.id.comentario_texto);
		comentario_texto.setText(novo_comentario);
		tempo_decorrido.setText(putTime);
		nome_usuario.setText(nome);
		DownloadImagem.postLoad(foto_usuario,photo);
		list.addView(novo,0);
		list.requestLayout();
		list.invalidate();
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
	    // Consultar o AdView como um recurso e carregar uma solicitação.
		AdView adView = (AdView)v.findViewById(R.id.propaganda);
	    AdRequest adRequest = new AdRequest.Builder().build();
	//    adView.loadAd(adRequest);

		if(getArguments()!=null){
			ID=getArguments().getInt("idTab");
			Server.get_detailed_event(getActivity(),getArguments().getString("evento"),this);	
			MainActivity.self.loadTela(ID);
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
		else if (n == 3) return "Marco";
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

		//QUANDO FIZER O SHARE DO FB
		/*Button compartilhar = (Button) view.findViewById(R.id.compartilhar);
		compartilhar.setVisibility(View.INVISIBLE);
		((ViewGroup)view).removeView(compartilhar);*/

		LinearLayout votacao_iniciada = (LinearLayout) view.findViewById(R.id.votacao_iniciada);
		LinearLayout votacao_nao_iniciada = (LinearLayout) view.findViewById(R.id.votacao_nao_iniciada);
		TextView descricao_horario = (TextView)view.findViewById(R.id.descricao_horario);
		TextView descricao_local = (TextView)view.findViewById(R.id.descricao_local);
		String data = evento.getDate();
		String dia = data.substring(0, 2);
		data = parseMonth(((int)(data.charAt(3)-'0'))*10 + ((int)(data.charAt(4)-'0')));
		descricao_horario.setText(dia + " de " + data + " as " + evento.getStartTime() + " horas");
		descricao_local.setText(evento.getLocalizationName()+" - "+evento.getCity()+"\n"+evento.getLocalizationAddress()+", "+evento.getNeighbourhood());

		TextView butao_terminar = (TextView)view.findViewById(R.id.botao_finalizar);
		hasUser = evento.getUsers().size() > 1 ? true : false;
		
		if(evento.getIsClosed()==false){
			votacao_iniciada.setVisibility(View.INVISIBLE);
			votacao_iniciada.removeAllViews();
			Button participar = (Button)view.findViewById(R.id.button1);
			LinearLayout layout_evento = (LinearLayout)view.findViewById(R.id.linear_evento);
			ImageView imagem_evento = (ImageView)view.findViewById(R.id.imagem_evento);
			int id_esporte = ConfigJP.getEsporteID(evento.getSport());
			layout_evento.setBackgroundResource(ConfigJP.ESPORTE_BARRA[id_esporte]);
			imagem_evento.setImageResource(ConfigJP.ESPORTE_BITMAP[id_esporte]);

			Button convidar_amigos = (Button)view.findViewById(R.id.convidar_amigos);
			
			map = new HashMap<String, Boolean>();
			Vector<Usuario> in = evento.getUsers();
			for (Iterator<Usuario> iterator = in.iterator(); iterator
					.hasNext();) {
				Usuario usuario = (Usuario) iterator
						.next();
				map.put(usuario.id,true);
			}
			
			convidar_amigos.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Server.get_friends(getActivity(), new Connecter<Vector<Usuario>>() {
						@Override
						public void onTerminado(Vector<Usuario> in) {
							Bundle args = new Bundle();
							args.putString("id_evento",evento.getId());
							args.putInt("idTab",ID);
							ArrayList<Usuario> array=new ArrayList<Usuario>();
							for (Iterator<Usuario> iterator = in.iterator(); iterator
									.hasNext();) {
								Usuario usuario = (Usuario) iterator
										.next();
								if(map.containsKey(usuario.id)==false) {
									array.add(usuario);
								}
							}
							args.putParcelableArrayList("users",array);
							InviteFriends next = new InviteFriends();
							next.setArguments(args);
							((MainActivity)getActivity()).mudarAbaAtual(next);
						}
					});
				}
			});

			TextView qtd_confirmados = (TextView)view.findViewById(R.id.qtd_confirmados);
			TextView qtd_no_local = (TextView)view.findViewById(R.id.qtd_no_local);

			LinearLayout pessoas = (LinearLayout)view.findViewById(R.id.nova_foto); 

			qtd_confirmados.setText(""+evento.getUsers().size());
			qtd_no_local.setText(evento.getAtEvent().size()+"");

			LinearLayout estou_no_local = (LinearLayout) view.findViewById(R.id.estou_no_local);
			TextView texto_no_local = (TextView) view.findViewById(R.id.texto_no_local);
			final EventFragment self = this;
			if(evento.getHasArrived()) texto_no_local.setText("No local");
			else texto_no_local.setText("Cheguei");
			estou_no_local.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(evento.getIsParticipating()){
						if(!evento.getHasArrived()){
							Server.arrive_event(self.getActivity(), evento.getId(), self);
							reloadPage();
						}
					}
				}
			});

			LinearLayout showConfirmed = (LinearLayout) view.findViewById(R.id.visualizar_confirmados);
			LinearLayout showLocal = (LinearLayout) view.findViewById(R.id.visualizar_local);
			showConfirmed.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Vector<Usuario> in = evento.getUsers();
					AmigosFragment fm = new AmigosFragment();
					Bundle arg = new Bundle();
					arg.putInt("idTab",ID);
					ArrayList<Usuario> array=new ArrayList<Usuario>();
					for (Iterator<Usuario> iterator = in.iterator(); iterator
							.hasNext();) {
						Usuario usuario = (Usuario) iterator
								.next();
						array.add(usuario);
					}
					arg.putParcelableArrayList("users",array);
					fm.setArguments(arg);
					((MainActivity)self.getActivity()).mudarAbaAtual(fm);
				}
			});
			showLocal.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Vector<Usuario> in = evento.getAtEvent();
					AmigosFragment fm = new AmigosFragment();
					Bundle arg = new Bundle();
					arg.putInt("idTab",ID);
					ArrayList<Usuario> array=new ArrayList<Usuario>();
					for (Iterator<Usuario> iterator = in.iterator(); iterator
							.hasNext();) {
						Usuario usuario = (Usuario) iterator
								.next();
						array.add(usuario);
					}
					arg.putParcelableArrayList("users",array);
					fm.setArguments(arg);
					((MainActivity)self.getActivity()).mudarAbaAtual(fm);
				}
			});

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
						args.putInt("idTab",ID);

						next.setArguments(args);
						((MainActivity)getActivity()).mudarAbaAtual(next);	
					}
				});
				butao_terminar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Server.close_event(evento.getId(), null);
						reloadPage();
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
			addGuys(pessoas,evento);
		}else{
			votacao_nao_iniciada.setVisibility(View.INVISIBLE);
			votacao_nao_iniciada.removeAllViews();
			
			butao_terminar.setVisibility(View.INVISIBLE);

			TextView num_participantes = (TextView) view.findViewById(R.id.num_participantes);
			num_participantes.setText(""+evento.getUsers().size());
			final ImageView starNum[] = new ImageView[5];
			starNum[0] = (ImageView) view.findViewById(R.id.starNum1);
			starNum[1] = (ImageView) view.findViewById(R.id.starNum2);
			starNum[2] = (ImageView) view.findViewById(R.id.starNum3);
			starNum[3] = (ImageView) view.findViewById(R.id.starNum4);
			starNum[4] = (ImageView) view.findViewById(R.id.starNum5);
			rating=0.0;
			LinearLayout percentual = (LinearLayout) view.findViewById(R.id.percentual);
			OnTouchListener estrelas = new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					if((arg1.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_DOWN){
						int value = Math.round((arg1.getX()/(arg0.getWidth()))*100);
						rating=0.0;
						for(int i=0,j=11;i<5;i++,j+=20){
							if(value >= j){
								starNum[i].setImageResource(R.drawable.star1);
								rating+=1.0;
							}
							else if(value >= j-10){
								starNum[i].setImageResource(R.drawable.halfstar);
								rating+=0.5;
							}
							else starNum[i].setImageResource(R.drawable.star2);
						}
					}
					return false;
				}
			};
			percentual.setOnTouchListener(estrelas);
			award1 = (ImageView) view.findViewById(R.id.award1);
			award2 = (ImageView) view.findViewById(R.id.award2);
			award3 = (ImageView) view.findViewById(R.id.award3);
			award4 = (ImageView) view.findViewById(R.id.award4);
			/** ISSO AQUI TEM QUE PEGAR COM O SERVIDOR **/
			award = new boolean[4];
			cantVote = new boolean[4];

			award1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ImageView view = (ImageView)arg0;
					award[0] = !award[0];
					if(award[0]==false) view.setImageResource(R.drawable.icongenteboa);
					else view.setImageResource(R.drawable.okaygenteboa);
				}
			});
			award2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ImageView view = (ImageView)arg0;
					award[1] = !award[1];
					if(award[1]==false) view.setImageResource(R.drawable.iconesforcado);
					else view.setImageResource(R.drawable.okayesforcado);
				}
			});
			award3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ImageView view = (ImageView)arg0;
					award[2] = !award[2];
					if(award[2]==false) view.setImageResource(R.drawable.iconjogaprotime);
					else view.setImageResource(R.drawable.okayjogaprotime);
				}
			});
			award4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ImageView view = (ImageView)arg0;
					award[3] = !award[3];
					if(award[3]==false) view.setImageResource(R.drawable.iconfairplay);
					else view.setImageResource(R.drawable.okayfairplay);
				}
			});
			LinearLayout pessoasVotacao = (LinearLayout)view.findViewById(R.id.nova_foto2); 
			LinearLayout pessoas = (LinearLayout)view.findViewById(R.id.nova_foto3);
			addGuys(pessoas,evento);
			final ImageView foto_doidinho = (ImageView) view.findViewById(R.id.foto_usuario_votar);
			final TextView nome_doidinho = (TextView) view.findViewById(R.id.nome_usuario_votar);
			for (int i = 0; i < evento.getUsers().size(); i++) {
				if(evento.getUsers().get(i).getId().equals(ConfigJP.UserId)) continue;
				ImageView imagem;
				imagem = (ImageView) View.inflate(getActivity(), R.layout.image_foto, null);
				DownloadImagem.postLoad(imagem,	evento.getUsers().get(i).getPhoto());
				pessoasVotacao.addView(imagem);
				final int index=i;
				imagem.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ind = index;
						initBadges(foto_doidinho,evento,nome_doidinho);
					}
				});
			}
			if(evento.getUsers().get(0).getId().equals(ConfigJP.UserId)){
				if(evento.getUsers().size() > 1){
					ind=1;
					initBadges(foto_doidinho,evento,nome_doidinho);
				}else{
					nome_doidinho.setText("No user");
				}
			}else{
				ind=0;
				initBadges(foto_doidinho,evento,nome_doidinho);
			}
			badges = new String[4];
			badges[0] = "Gente Boa";
			badges[1] = "Fair Play";
			badges[2] = "Esforcado";
			badges[3] = "Joga Pro Time";
			Button votar = (Button) view.findViewById(R.id.vote);
			votar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(hasUser){
						if(evento.getUsers().get(ind).getVotedInSport()==false) Server.rate_user(evento.getUsers().get(ind).getId(),getActivity(), evento.getSport(), rating+"", null);
						for(int i=0;i<4;i++){
							if(award[i] && cantVote[i]==false){
								Server.vote_in_tag_user(evento.getUsers().get(ind).getId(), getActivity(), badges[i], null);
								reloadPage();
							}
						}
					}
				}
			});
			Button repeat = (Button) view.findViewById(R.id.button1);
			paintGray(repeat);
			repeat.setText("Deseja repetir o evento?");
			repeat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Bundle args = new Bundle();
					args.putString("esporte",evento.getSport());
					args.putDouble("preco",(evento.getPrice())/100.0);
					args.putString("rua",evento.getLocalizationAddress());
					args.putString("nomeLocal",evento.getLocalizationName());
					args.putString("bairro",evento.getNeighbourhood());
					args.putString("cidade",evento.getCity());
					args.putBoolean("repearEvent", true);
					args.putInt("idTab",ID);
					CriarEventosFragment next = new CriarEventosFragment();
					next.setArguments(args);
					((MainActivity)getActivity()).mudarAbaAtual(next);
				}
			});
		}

		Button butao_enviar = (Button)view.findViewById(R.id.enviar_comentario);
		final EventFragment listener = this;
		butao_enviar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText myEditText = (EditText) view.findViewById(R.id.criar_comentario);
				Server.comment(getActivity(),myEvent.getId(),myEditText.getText().toString(), new Connecter<Comentario>() {
					@Override
					public void onTerminado(final Comentario in) {
						if(listener.getView()!=null){
							getView().post(new Runnable() {
								@Override
								public void run() {
									listener.addComment(in.getUserName(), in.getHour(), in.getDate(), in.getText(), in.getPhoto(),false);
									getView().requestLayout();
									getView().invalidate();
								}
							});
						}
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
		if(evento.getComments()!=null){
			for (Iterator<Comentario> iterator = evento.getComments().iterator(); iterator.hasNext();) {
				Comentario coment = (Comentario) iterator.next();
				addComment(coment.getUserName(),coment.getHour(),coment.getDate(),coment.getText(),coment.getPhoto(),true);
			}
		}
		TextView tipo_da_partida = (TextView)view.findViewById(R.id.tipo_da_partida);	
		TextView descricao_do_esporte = (TextView)view.findViewById(R.id.descricao_do_esporte);
		tipo_da_partida.setText(evento.getSport());
		descricao_do_esporte.setText(evento.getDescription());
		double[] temp=null;
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

		MapaFragment mapaF = new MapaFragment();
		Bundle arg= new Bundle();
		arg.putString("nome", evento.getName());
		arg.putBoolean("isMax", false);
		arg.putString("local", evento.getLocalizationName());
		arg.putDoubleArray("latlng", temp);
		mapaF.setArguments(arg);
		getChildFragmentManager().beginTransaction().replace(R.id.mapa_frag, mapaF).commit();
		view.requestLayout();
		view.postInvalidate();
		((MainActivity) getActivity()).popLoadTela(ID);
	}

	@Override
	public void onClick(View v) {
		if(myEvent!=null){
			if(myEvent.getIsParticipating()==false) Server.enter_event(getActivity(), myEvent.getId(),this );
			else{
				if(myEvent.getHasArrived()) Server.cancel_arrive(getActivity(), myEvent.getId(), this);
				Server.leave_event(getActivity(), myEvent.getId(),this );
			}
			reloadPage();
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
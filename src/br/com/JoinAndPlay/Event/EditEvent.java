package br.com.JoinAndPlay.Event;

import java.util.Iterator;
import java.util.zip.Inflater;

import org.joda.time.DateTime;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
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
import br.com.JoinAndPlay.PesquisarEventosFragment;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Comentario;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.DownloadImagem;
import br.com.JoinAndPlay.Server.Evento;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
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

public class EditEvent extends Fragment implements RadialTimePickerDialog.OnTimeSetListener, CalendarDatePickerDialog.OnDateSetListener,
DatePickerDialogFragment.DatePickerDialogHandler,
TimePickerDialogFragment.TimePickerDialogHandler  {
	private Evento myEvent=null;
	SupportMapFragment suportMap;
	public LinearLayout list;
	public LayoutInflater inf;
	private boolean privado;
	private Button privadoE;
	private Button publicoE;
	private String[] data;
	private int[] dataNOW;
	private Button diaE;
	private Button horabE;
	private Button horaeE;
	private boolean begin = false;
	private boolean end = false;
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
			
			data = new String[3];
			dataNOW = new int[3];
			DateTime now = DateTime.now();
			this.dataNOW[0] = now.getDayOfMonth();
			this.dataNOW[1] = now.getMonthOfYear();
			this.dataNOW[2] = now.getYear();
			
			diaE = (Button) v.findViewById(R.id.editar_dia);
			config = getActivity().getResources().getConfiguration();
			diaE.setText(dia);
			diaE.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Date Picker for mid to high-end smartphones
					if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
						/** Radial Date Picker **/
						DateTime now = DateTime.now();
						CalendarDatePickerDialog calendar = CalendarDatePickerDialog.newInstance(EditEvent.this, 
								now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

						calendar.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
					} else { //Date Picker for low-end smartphones
						DatePickerBuilder dpb = new DatePickerBuilder()
						.setFragmentManager(getChildFragmentManager())
						.setTargetFragment(EditEvent.this)
						.setStyleResId(R.style.BetterPickersLowEndTheme);
						dpb.show();
					}
				}
			});
			
			horabE = (Button) v.findViewById(R.id.editar_hora_inicial);
			horabE.setText(hora_begin);
			
			horabE.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Time picker for mid to high-end smartphones
					if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
						/** Radial Time Picker **/
						DateTime now = DateTime.now();
						RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(EditEvent.this, 
								now.getHourOfDay(), now.getMinuteOfHour(), 
								DateFormat.is24HourFormat(getActivity()));

						radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
					} else { //Time picker for low end smartphones
						/** Better Time Picker **/
						TimePickerBuilder tpb = new TimePickerBuilder();
						tpb.setFragmentManager(getChildFragmentManager());
						tpb.setTargetFragment(EditEvent.this);
						tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
						tpb.show();
					}
					begin = true;
				}
			});
			
			
			horaeE = (Button) v.findViewById(R.id.editar_hora_final);
			horaeE.setText(hora_end);
			horaeE.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Time picker for mid to high-end smartphones
					if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
						/** Radial Time Picker **/
						DateTime now = DateTime.now();
						RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(EditEvent.this, 
								now.getHourOfDay(), now.getMinuteOfHour(), 
								DateFormat.is24HourFormat(getActivity()));

						radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
					} else { //Time picker for low-end smartphones
						/** Better Time Picker **/
						TimePickerBuilder tpb = new TimePickerBuilder();
						tpb.setFragmentManager(getChildFragmentManager());
						tpb.setTargetFragment(EditEvent.this);
						tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
						tpb.show();
					}
					end = true;
				}
			});
			
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
	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
		if((dayOfMonth < this.dataNOW[0] && monthOfYear <= this.dataNOW[1] && year <= this.dataNOW[2])
			|| (monthOfYear < this.dataNOW[1] && year < this.dataNOW[2])
			|| (year < this.dataNOW[2])){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
			}});
			
			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_xml, null));
			AlertDialog alert11 = builder1.create();
			
			OnShowListener onshow = new OnShowListener() {
				@Override
				@SuppressWarnings( "deprecation" )
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
	                        .getButton(AlertDialog.BUTTON_POSITIVE);
					
	                positiveButton.setBackgroundDrawable(getResources()
	                        .getDrawable(R.drawable.alert_button));
	                
	                positiveButton.setText("OK");
	                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
					
				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
		} else {
			this.data[0] = dayOfMonth+"";
			this.data[1] = monthOfYear+1+"";
			this.data[2] = year+"";
			String day;
			String month;
			day = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
			month = (monthOfYear+1) < 10 ? "0" + (monthOfYear+1) : "" + (monthOfYear+1);
			diaE.setText(day + "/" + month + "/" + year);
		}
	}

	@Override
	public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
		if(dayOfMonth < this.dataNOW[0] && monthOfYear <= this.dataNOW[1] && year <= this.dataNOW[2]
			|| (monthOfYear < this.dataNOW[1] && year < this.dataNOW[2])
			|| (year < this.dataNOW[2])){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
			}});	
			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_xml, null));
			AlertDialog alert11 = builder1.create();
			
			OnShowListener onshow = new OnShowListener() {
				@Override
				@SuppressWarnings( "deprecation" )
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
	                        .getButton(AlertDialog.BUTTON_POSITIVE);
					
	                positiveButton.setBackgroundDrawable(getResources()
	                        .getDrawable(R.drawable.alert_button));
	                
	                positiveButton.setText("OK");
	                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
					
				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
		} else {
			this.data[0] = dayOfMonth+"";
			this.data[1] = monthOfYear+1+"";
			this.data[2] = year+"";
			String day;
			String month;
			day = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
			month = (monthOfYear+1) < 10 ? "0" + (monthOfYear+1) : "" + (monthOfYear+1);
			diaE.setText(day + "/" + month + "/" + year);
		}
	}

	@Override
	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;
		
		if (h.length() > 2) h = "" + hourOfDay;
		if (m.length() > 2) m = "" + minute;

		if(begin) {
			if((h + ":" + m).compareTo(horaeE.getText().toString()) < 0){
				horabE.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
				}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();
				
				OnShowListener onshow = new OnShowListener() {
					@Override
					@SuppressWarnings( "deprecation" )
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
		                        .getButton(AlertDialog.BUTTON_POSITIVE);
						
		                positiveButton.setBackgroundDrawable(getResources()
		                        .getDrawable(R.drawable.alert_button));
		                
		                positiveButton.setText("OK");
		                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
						
					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(horabE.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
				}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();
				
				OnShowListener onshow = new OnShowListener() {
					@Override
					@SuppressWarnings( "deprecation" )
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
		                        .getButton(AlertDialog.BUTTON_POSITIVE);
						
		                positiveButton.setBackgroundDrawable(getResources()
		                        .getDrawable(R.drawable.alert_button));
		                
		                positiveButton.setText("OK");
		                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
						
					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			} else {
				horaeE.setText(h + ":" + m);
				end = false;
			}
		}
	}

	@Override
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if (h.length() > 2) h = "" + hourOfDay;
		if (m.length() > 2) m = "" + minute;
		
		if(begin) {
			if((h + ":" + m).compareTo(horaeE.getText().toString()) < 0){
				horabE.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
				}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();
				
				OnShowListener onshow = new OnShowListener() {
					@Override
					@SuppressWarnings( "deprecation" )
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
		                        .getButton(AlertDialog.BUTTON_POSITIVE);
						
		                positiveButton.setBackgroundDrawable(getResources()
		                        .getDrawable(R.drawable.alert_button));
		                
		                positiveButton.setText("OK");
		                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
						
					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(horabE.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
				}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();
				
				OnShowListener onshow = new OnShowListener() {
					@Override
					@SuppressWarnings( "deprecation" )
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
		                        .getButton(AlertDialog.BUTTON_POSITIVE);
						
		                positiveButton.setBackgroundDrawable(getResources()
		                        .getDrawable(R.drawable.alert_button));
		                
		                positiveButton.setText("OK");
		                positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);
						
					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			} else {
				horaeE.setText(h + ":" + m);
				end = false;
			}
		}
	}
}

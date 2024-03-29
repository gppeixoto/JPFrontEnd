package br.com.JoinAndPlay.Event;

import java.text.DecimalFormat;
import java.util.Vector;

import org.joda.time.DateTime;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import br.com.JoinAndPlay.BolaForaFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Endereco;
import br.com.JoinAndPlay.Server.Server;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditEvent extends Fragment implements RadialTimePickerDialog.OnTimeSetListener, CalendarDatePickerDialog.OnDateSetListener,
DatePickerDialogFragment.DatePickerDialogHandler,
TimePickerDialogFragment.TimePickerDialogHandler  {
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
	private boolean eventoPago = false;
	private Double valor = 0.0;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
	private int ID=0;

	public void paintButton(Button red,Button gray){
		red.setBackgroundResource(R.drawable.red_button);
		red.setPadding(10, 10, 10, 10);
		gray.setBackgroundResource(R.drawable.gray_button);
		gray.setPadding(10, 10, 10, 10);
	}

	public void alerta(Button alert,int value){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(alert.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder1.setCancelable(true);
		builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}});

		builder1.setView(MainActivity.self.getLayoutInflater().inflate(value, null));
		AlertDialog alert11 = builder1.create();

		OnShowListener onshow = new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog)
						.getButton(AlertDialog.BUTTON_POSITIVE);
				positiveButton.setBackgroundResource(R.drawable.alert_button);
				positiveButton.setText("OK");
				positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);
			}
		};
		alert11.setOnShowListener(onshow);
		alert11.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapsInitializer.initialize(MainActivity.self);

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
			valor = args.getDouble("preco");
			String dia = args.getString("dia");
			String hora_begin = args.getString("hora_begin");
			String hora_end = args.getString("hora_end");
			String rua = args.getString("rua");
			final String esporte = args.getString("esporte");
			final String id_evento = args.getString("id_evento");
			String local_name = args.getString("local_name");
			
			ID = args.getInt("idTab");

			final EditText nome_local = (EditText) v.findViewById(R.id.editar_nome_local);
			nome_local.setText(local_name);
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
			final CheckBox has_price = (CheckBox) v.findViewById(R.id.com_preco);
			final TextView real = (TextView) v.findViewById(R.id.valor_preco);
			final EditText preco = (EditText) v.findViewById(R.id.editar_preco);
			if(valor == 0.0){
				real.setVisibility(View.INVISIBLE);
				preco.setVisibility(View.INVISIBLE);
			}else{
				valor=valor/100.0;
				has_price.setChecked(true);
				eventoPago=true;
				preco.setText(valor.toString());
			}
			has_price.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(has_price.isChecked()){
						real.setVisibility(View.VISIBLE);
						preco.setVisibility(View.VISIBLE);
						eventoPago=true;
					} else {
						real.setVisibility(View.INVISIBLE);
						preco.setVisibility(View.INVISIBLE);
						eventoPago=false;
					}
				}
			});

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
			this.data[0] = now.getDayOfMonth()+"";
			this.data[1] = now.getMonthOfYear()+"";
			this.data[2] = now.getYear()+"";

			diaE = (Button) v.findViewById(R.id.editar_dia);
			config = MainActivity.self.getResources().getConfiguration();
			diaE.setText(dia+"/"+(now.getYear()+""));
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
								DateFormat.is24HourFormat(MainActivity.self));

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
			horaeE.setText("23:59");
			horaeE.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Time picker for mid to high-end smartphones
					if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
						/** Radial Time Picker **/
						DateTime now = DateTime.now();
						RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(EditEvent.this, 
								now.getHourOfDay(), now.getMinuteOfHour(), 
								DateFormat.is24HourFormat(MainActivity.self));

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

			final Button confirmar_edicao = (Button) v.findViewById(R.id.botao_confirmarEdicao);
			confirmar_edicao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					// TODO Auto-generated method stub
					if(ruaE.getText().toString()==null||ruaE.getText().toString().trim().equals("")){
						alerta(confirmar_edicao,R.layout.alert_create_rua);
						return;
					}else if(nome_local.getText().toString()==null||nome_local.getText().toString().trim().equals("")){
						alerta(confirmar_edicao,R.layout.alert_create_lugar);
						return;
					}else if(bairroE.getText().toString()==null||bairroE.getText().toString().trim().equals("")){
						alerta(confirmar_edicao,R.layout.alert_create_bairro);
						return;
					} else if(cidadeE.getText().toString()==null||cidadeE.getText().toString().trim().equals("")){
						alerta(confirmar_edicao,R.layout.alert_create_cidade);
						return;
					}else if(nome_eventoE.getText().toString() == null || nome_eventoE.getText().toString().trim().equals("")){
						alerta(confirmar_edicao,R.layout.alert_create_nome);
						return;
					}
					Server.getAddresses("", ruaE.getText().toString(), bairroE.getText().toString(), cidadeE.getText().toString(), new Connecter<Vector<Endereco>>() {	
						@Override
						public void onTerminado(Vector<Endereco> in) {
							if(in == null){
								Bundle args = new Bundle();
								args.putBoolean("internet",false);
								BolaForaFragment bfm = new BolaForaFragment();
								bfm.setArguments(args);
								MainActivity.self.mudarAba(ID,bfm);
							}
							else if(in.isEmpty()){
								if(v != null){
									v.post(new Runnable() {
										@Override
										public void run() {
											alerta(confirmar_edicao, R.layout.alert_create_endereco);
										}
									});
								}
								return;
							}
							double priceE = 0.0;
							if(eventoPago){
								String aux = preco.getText().toString();					
								priceE = Double.parseDouble(aux);
								DecimalFormat df = new DecimalFormat("##0.00");
								aux = df.format(priceE);
								for(int i=0;i<aux.length();i++){ 
									if(aux.charAt(i) == ','){
										if(i+1 < aux.length())
											aux = aux.substring(0,i)+'.'+aux.substring(i+1);
										else 
											aux = aux.substring(0,i)+'.'+"00";
										break;
									}
								}
								priceE = Double.parseDouble(aux);
							}
							Server.edit_event(MainActivity.self, nome_local.getText().toString(), ruaE.getText().toString(), cidadeE.getText().toString(), bairroE.getText().toString(), esporte, diaE.getText().toString(), horabE.getText().toString(), horaeE.getText().toString(), descricao_eventoE.getText().toString(), nome_eventoE.getText().toString(), priceE, privado, id_evento, null);
							((MainActivity)MainActivity.self).retirarAbaAtual();
						}
					});
				}
			});
		}
		return  v;
	}
	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
		if((dayOfMonth < this.dataNOW[0] && monthOfYear+1 <= this.dataNOW[1] && year <= this.dataNOW[2])
				|| (monthOfYear+1 < this.dataNOW[1] && year <= this.dataNOW[2])
				|| (year < this.dataNOW[2])){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert_xml, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

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
		if((dayOfMonth < this.dataNOW[0] && monthOfYear+1 <= this.dataNOW[1] && year <= this.dataNOW[2])
				|| (monthOfYear+1 < this.dataNOW[1] && year <= this.dataNOW[2])
				|| (year < this.dataNOW[2])){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});	
			builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert_xml, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

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
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(horabE.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource((R.drawable.alert_button));

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

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
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(horabE.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.self, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(MainActivity.self.getLayoutInflater().inflate(R.layout.alert2_xml, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(MainActivity.self, R.style.AlertStyle);

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

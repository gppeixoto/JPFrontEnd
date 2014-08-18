package br.com.JoinAndPlay;

import java.text.DecimalFormat;
import java.util.Vector;

import org.joda.time.DateTime;

import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Endereco;
import br.com.JoinAndPlay.Server.Server;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CriarEventosFragment extends Fragment implements RadialTimePickerDialog.OnTimeSetListener, 
CalendarDatePickerDialog.OnDateSetListener,Connecter<Vector<Endereco>>,OnClickListener,Runnable {

	private boolean begin = false;
	private boolean end = false;
	private boolean pago = false;


	private ImageButton e1Button;
	private ImageButton e2Button;
	private ImageButton e3Button;
	private ImageButton e4Button;

	private Vector<Endereco> vec;

	private CheckBox checkPago;

	private Button bProximo;
	private Button bDataInicio;
	private Button bDataFim;
	private Button bDia;

	private AutoCompleteTextView eEsporte;

	private EditText eNomeLugar;
	private EditText ePreco;
	private EditText eBairro;
	private EditText eCidade;
	private EditText eRua;

	private TextView tUnidade;

	private Configuration config;
	private String[] data;
	private int[] dataNOW;

	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";


	@Override
	public void onResume() {
		super.onResume();
		RadialTimePickerDialog rtpd = (RadialTimePickerDialog) getFragmentManager().findFragmentByTag(
				FRAG_TAG_TIME_PICKER);
		if (rtpd != null) {
			rtpd.setOnTimeSetListener(this);
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if(container==null) return null;

		View view = inflater.inflate(R.layout.criar_evento, container,false);

		data = new String[3];
		dataNOW = new int[3];

		config = getActivity().getResources().getConfiguration();

		String[] str={"Baseball","Basquete","Boliche","Boxe","Cartas","Ciclismo","Corrida",
				"Domin�","Futebol","Futebol Americano","Golfe","Patina��o","Sinuca",
				"Skate", "T�nis", "T�nis de Mesa", "Video-Game", "V�lei", "V�lei de Praia", 
		"Xadrez"};

		ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_dropdown_item_1line, str);

		e1Button = (ImageButton) view.findViewById(R.id.esporte1);
		e2Button = (ImageButton) view.findViewById(R.id.esporte2);
		e3Button = (ImageButton) view.findViewById(R.id.esporte3);
		e4Button = (ImageButton) view.findViewById(R.id.esporte4);

		e1Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				eEsporte.setText("Futebol");
			}
		});

		e2Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				eEsporte.setText("Basquete");
			}
		});

		e3Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				eEsporte.setText("V�lei");
			}
		});

		e4Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				eEsporte.setText("Ciclismo");
			}
		});

		tUnidade = (TextView) view.findViewById(R.id.tv_reais);
		tUnidade.setVisibility(View.INVISIBLE);

		ePreco = (EditText) view.findViewById(R.id.escolha_preco);	
		ePreco.setVisibility(View.INVISIBLE);

		eEsporte = (AutoCompleteTextView) view.findViewById(R.id.escolha_esporte);
		eEsporte.setAdapter(adp);
		eEsporte.setThreshold(1);

		eNomeLugar = (EditText) view.findViewById(R.id.escolha_nome);

		eBairro = (EditText) view.findViewById(R.id.escolha_enderecoBairro);
		eCidade = (EditText) view.findViewById(R.id.escolha_enderecoCidade);
		eRua = (EditText) view.findViewById(R.id.escolha_enderecoRua);

		checkPago = (CheckBox) view.findViewById(R.id.preco_box);
		if(checkPago.isChecked()){
			pago = true;
			ePreco.setVisibility(View.VISIBLE);
			tUnidade.setVisibility(View.VISIBLE);
		} else {
			pago = false;
			ePreco.setVisibility(View.INVISIBLE);
			tUnidade.setVisibility(View.INVISIBLE);
		}

		bProximo = (Button) view.findViewById(R.id.nextButton);
		bProximo.setText("Pr�ximo");

		bDataInicio = (Button) view.findViewById(R.id.buttonDataInicio);
		bDataInicio.setText("00:00");

		bDataFim = (Button) view.findViewById(R.id.buttonDataFim);
		bDataFim.setText("23:59");

		DateTime now = DateTime.now();
		String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : "" + now.getDayOfMonth();
		String month = (now.getMonthOfYear()) < 10 ? "0" + (now.getMonthOfYear()) : "" + (now.getMonthOfYear());

		bDia = (Button) view.findViewById(R.id.buttonDia);
		bDia.setText(day + "/" + month + "/" + now.getYear());

		this.dataNOW[0] = now.getDayOfMonth();
		this.dataNOW[1] = now.getMonthOfYear()+1;
		this.dataNOW[2] = now.getYear();

		this.data[0] = now.getDayOfMonth()+"";
		this.data[1] = now.getMonthOfYear()+1+"";
		this.data[2] = now.getYear()+"";

		if(ePreco.getVisibility()==View.VISIBLE){
			ePreco.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

				public void afterTextChanged(Editable s) {

				}
			});
		}

		bProximo.setOnClickListener(this);


		bDia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Date Picker **/
					DateTime now = DateTime.now();
					CalendarDatePickerDialog calendar = CalendarDatePickerDialog.newInstance(CriarEventosFragment.this, 
							now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

					calendar.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
				} else { //Date Picker for low-end smartphones
					DatePickerBuilder dpb = new DatePickerBuilder()
					.setFragmentManager(getChildFragmentManager())
					.setTargetFragment(CriarEventosFragment.this)
					.setStyleResId(R.style.BetterPickersLowEndTheme);
					dpb.show();
				}
			}
		});

		bDataInicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Time Picker **/
					DateTime now = DateTime.now();
					RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(CriarEventosFragment.this, 
							now.getHourOfDay(), now.getMinuteOfHour(), 
							DateFormat.is24HourFormat(getActivity()));

					radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				} else { //Time picker for low end smartphones
					/** Better Time Picker **/
					TimePickerBuilder tpb = new TimePickerBuilder();
					tpb.setFragmentManager(getChildFragmentManager());
					tpb.setTargetFragment(CriarEventosFragment.this);
					tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
					tpb.show();
				}
				begin = true;

			}
		});

		bDataFim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Radial Time Picker **/
				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Time Picker **/
					DateTime now = DateTime.now();
					RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(CriarEventosFragment.this, 
							now.getHourOfDay(), now.getMinuteOfHour(), 
							DateFormat.is24HourFormat(getActivity()));

					radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				} else { //Time picker for low-end smartphones
					/** Better Time Picker **/
					TimePickerBuilder tpb = new TimePickerBuilder();
					tpb.setFragmentManager(getChildFragmentManager());
					tpb.setTargetFragment(CriarEventosFragment.this);
					tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
					tpb.show();
				}
				end = true;
			}
		});

		checkPago.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(checkPago.isChecked()){
					pago = true;
					ePreco.setVisibility(View.VISIBLE);
					tUnidade.setVisibility(View.VISIBLE);
				} else {
					pago = false;
					ePreco.setVisibility(View.INVISIBLE);
					tUnidade.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		if(getArguments() != null && getArguments().getBoolean("repearEvent", false)){
			eNomeLugar.setText(getArguments().getString("nomeLocal"));
			eEsporte.setText(getArguments().getString("esporte"));
			eRua.setText(getArguments().getString("rua"));
			eBairro.setText(getArguments().getString("bairro"));
			eCidade.setText(getArguments().getString("cidade"));
			if(getArguments().getDouble("preco") != 0.0){
				pago=true;
				ePreco.setVisibility(View.VISIBLE);
				tUnidade.setVisibility(View.VISIBLE);
				ePreco.setText(getArguments().getDouble("preco")+"");
			}
		}
		return view;
	}

	public String parseMonth(int n){
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
		else return "Janeiro";
	}

	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year,
			int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		if((dayOfMonth < this.dataNOW[0] && monthOfYear <= this.dataNOW[1] && year <= this.dataNOW[2])
				|| (monthOfYear < this.dataNOW[1] && year < this.dataNOW[2])
				|| (year < this.dataNOW[2])){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_dia, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

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
			bDia.setText(day + "/" + month + "/" + year);
		}

	}


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
			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_dia, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

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
			bDia.setText(day + "/" + month + "/" + year);
		}
	}


	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if (h.length() > 2) h = "" + hourOfDay;
		if (m.length() > 2) m = "" + minute;

		if(begin) {
			if((h + ":" + m).compareTo(bDataFim.getText().toString()) < 0){
				bDataInicio.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_hora, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			Log.v("aaaaaa",(h + ":" + m));
			Log.v("bbbbbb", bDataInicio.getText().toString());
			if((h + ":" + m).compareTo(bDataInicio.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_hora, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			} else {
				bDataFim.setText(h + ":" + m);
				end = false;
			}
		}
	}

	@Override
	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay,
			int minute) {
		// TODO Auto-generated method stub
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if (h.length() > 2) h = "" + hourOfDay;
		if (m.length() > 2) m = "" + minute;

		if(begin) {
			if((h + ":" + m).compareTo(bDataFim.getText().toString()) < 0){
				bDataInicio.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_hora, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(bDataInicio.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});	
				builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_hora, null));
				AlertDialog alert11 = builder1.create();

				OnShowListener onshow = new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						Button positiveButton = ((AlertDialog) dialog)
								.getButton(AlertDialog.BUTTON_POSITIVE);

						positiveButton.setBackgroundResource(R.drawable.alert_button);

						positiveButton.setText("OK");
						positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

					}
				};
				alert11.setOnShowListener(onshow);
				alert11.show();
			} else {
				bDataFim.setText(h + ":" + m);
				end = false;
			}
		}
	}

	@Override
	public void onTerminado(Vector<Endereco> in) {
		vec=in;
		if(getView()!=null){
			
			getView().post(this);
		}

	}
	String esporte;
	String rua ;
	String lugar;
	String cidade;
	String bairro;
	@Override
	public void onClick(View v) {

		esporte = eEsporte.getText().toString();
		rua= eRua.getText().toString();
		lugar = eNomeLugar.getText().toString();
		cidade = eCidade.getText().toString();
		bairro = eBairro.getText().toString();

		if(esporte==null||esporte.trim().equals("")){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_esporte, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
			return;
		} else if(rua==null||rua.trim().equals("")){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_rua, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
			return;
		} else if(lugar==null||lugar.trim().equals("")){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_lugar, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
			return;
		}else if(bairro==null||bairro.trim().equals("")){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_bairro, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
			return;

		} else if(cidade==null||cidade.trim().equals("")){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_cidade, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
			return;
		}

		Log.v("query de jp", rua + " " + bairro + " " + cidade);
		Server.getAddresses("", rua, bairro, cidade, this);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(vec==null || vec.isEmpty()){
			AlertDialog.Builder builder1 = new AlertDialog.Builder(bProximo.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			builder1.setCancelable(true);
			builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}});

			builder1.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_create_endereco, null));
			AlertDialog alert11 = builder1.create();

			OnShowListener onshow = new OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					Button positiveButton = ((AlertDialog) dialog)
							.getButton(AlertDialog.BUTTON_POSITIVE);

					positiveButton.setBackgroundResource(R.drawable.alert_button);

					positiveButton.setText("OK");
					positiveButton.setTextAppearance(getActivity(), R.style.AlertStyle);

				}
			};
			alert11.setOnShowListener(onshow);
			alert11.show();
		}else{

			CriarEventosCompFragment next = new CriarEventosCompFragment();

			Bundle args = new Bundle();				

			args.putString("esporte", esporte);
			args.putString("rua", rua);
			args.putString("cidade", cidade);
			args.putString("bairro", bairro);
			args.putString("nomeLocal", lugar);
			args.putString("data", (data[2]+"-"+data[1]+"-"+data[0]));				

			args.putString("horaInicio", bDataInicio.getText().toString());
			args.putString("horaTermino", bDataFim.getText().toString());

			if(pago){
				String aux = ePreco.getText().toString();					
				double a = Double.parseDouble(aux);
				DecimalFormat df = new DecimalFormat("##0.00");
				aux = df.format(a);
				a = Double.parseDouble(aux);
				Log.v("precodouble", a+"");

				args.putDouble("preco", a);
			} else {
				args.putDouble("preco", 0.00);
			}

			Log.v("pegaporra", "chegou aqui?");

			next.setArguments(args);
			((MainActivity)getActivity()).mudarAbaAtual(next);	
		}
		
	}		

}
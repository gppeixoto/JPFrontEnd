package br.com.JoinAndPlay;

import org.joda.time.DateTime;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CriarEventosFragment extends Fragment implements RadialTimePickerDialog.OnTimeSetListener, 
	CalendarDatePickerDialog.OnDateSetListener {
	
	private boolean begin = false;
	private boolean end = false;
	private boolean pago = false;
	
	private CheckBox checkPago;
	
	private Button bProximo;
	private Button bDataInicio;
	private Button bDataFim;
	private Button bDia;
		
	private AutoCompleteTextView eEsporte;
	
	private EditText eNomeLugar;
	private EditText eEnderecoLugar;
	private EditText ePreco;
	
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
		
		ePreco = (EditText) view.findViewById(R.id.escolha_preco);	
		ePreco.setVisibility(View.INVISIBLE);
		
		eEsporte = (AutoCompleteTextView) view.findViewById(R.id.escolha_esporte);
		eEsporte.setAdapter(adp);
		eEsporte.setThreshold(1);
		
		eNomeLugar = (EditText) view.findViewById(R.id.escolha_nome);
		
		eEnderecoLugar = (EditText) view.findViewById(R.id.escolha_endereco);
		
		checkPago = (CheckBox) view.findViewById(R.id.preco_box);
			
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
		
		bProximo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CriarEventosCompFragment next = new CriarEventosCompFragment();
				
				Bundle args = new Bundle();				
				
				String esporte = eEsporte.getText().toString();
				String end = eEnderecoLugar.getText().toString();
				String lugar = eNomeLugar.getText().toString();
				/**
				if(esporte.trim().equals("")){
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Ops");
					error.setMessage("Escolha um esporte!");
					error.setPositiveButton("OK", null);
					error.show();
					return;
				} else if(end.trim().equals("")){
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Ops");
					error.setMessage("Escolha um local!");
					error.setPositiveButton("OK", null);
					error.show();
					return;
				} else if(lugar.trim().equals("")){
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Ops");
					error.setMessage("D� um nome ao local!");
					error.setPositiveButton("OK", null);
					error.show();
					return;
				}*/
				
				args.putString("esporte", esporte);
				args.putString("endereco", end);
				//Log.v("endedeco", eendv.getText().toString());
				args.putString("nomeLocal", lugar);
				//Log.v("nome local", env.getText().toString());
				args.putString("data", (data[2]+"-"+data[1]+"-"+data[0]));
				//Log.v("data", (data[2]+"-"+data[1]+"-"+data[0]));
				 
				
				
				args.putString("horaInicio", bDataInicio.getText().toString());
				//Log.v("hora inicio",b2.getText().toString());
				args.putString("horaTermino", bDataFim.getText().toString());
				//Log.v("hora fim", b3.getText().toString());
				
				if(pago){
					String aux = ePreco.getText().toString();
					double b = (aux.charAt(aux.length()-2)/10) + (aux.charAt(aux.length()-1)/100);
					
					double c = 0;
					double j=1;
					for(int i = aux.length()-4; i > -1; i--){
						c+= aux.charAt(i)*j;
						j*=10;
					}
					
					double a = b+c;
					args.putDouble("preco", a);
				} else {
					args.putDouble("preco", 0.0);
				}
				
				next.setArguments(args);
				
				((MainActivity)getActivity()).mudarAbaAtual(next);
				
			}
		});
		
	
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
				} else {
					pago = false;
					ePreco.setVisibility(View.INVISIBLE);
				}
			}
		});
		
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
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("Crie um evento futuro");
				//builder1.setTitle("Ops");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			} else {
				this.data[0] = dayOfMonth+"";
				this.data[1] = monthOfYear+1+"";
				this.data[2] = year+"";
				String day;
				String month;
				day = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
				month = (monthOfYear+1) < 10 ? "0" + (monthOfYear+1) : "" + (monthOfYear+1);
				//bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
				bDia.setText(day + "/" + month + "/" + year);
			}
        
	}
	
	
	public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
		if(dayOfMonth < this.dataNOW[0] && monthOfYear <= this.dataNOW[1] && year <= this.dataNOW[2]
				|| (monthOfYear < this.dataNOW[1] && year < this.dataNOW[2])
				|| (year < this.dataNOW[2])){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("Crie um evento futuro");
				//builder1.setTitle("Ops");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			} else {
				this.data[0] = dayOfMonth+"";
				this.data[1] = monthOfYear+1+"";
				this.data[2] = year+"";
				String day;
				String month;
				day = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
				month = (monthOfYear+1) < 10 ? "0" + (monthOfYear+1) : "" + (monthOfYear+1);
				//bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
				bDia.setText(day + "/" + month + "/" + year);
			}
	}
	
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if(begin) {
			if((h + ":" + m).compareTo(bDataFim.getText().toString()) < 0){
				bDataInicio.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("O t�rmino deve ser ap�s o in�cio.");
				//builder1.setTitle("Ops");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(bDataInicio.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("O t�rmino deve ser ap�s o in�cio.");
				//builder1.setTitle("Ops");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
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

		if(begin) {
			if((h + ":" + m).compareTo(bDataFim.getText().toString()) < 0){
				bDataInicio.setText(h + ":" + m);
				begin = false;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("O t�rmino deve ser ap�s o in�cio");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}

		} else if (end){
			if((h + ":" + m).compareTo(bDataInicio.getText().toString()) <= 0){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
				builder1.setMessage("O t�rmino deve ser ap�s o in�cio");
				builder1.setCancelable(true);
				builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			} else {
				bDataFim.setText(h + ":" + m);
				end = false;
			}
		}
	}
}
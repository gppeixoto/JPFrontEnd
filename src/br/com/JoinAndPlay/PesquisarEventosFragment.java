package br.com.JoinAndPlay;

import java.util.StringTokenizer;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

public class PesquisarEventosFragment extends Fragment 
implements RadialTimePickerDialog.OnTimeSetListener, CalendarDatePickerDialog.OnDateSetListener,
DatePickerDialogFragment.DatePickerDialogHandler,
TimePickerDialogFragment.TimePickerDialogHandler {

	private Button bg;
	private Button b2;
	private Button b3;
	private Button bd;
	private TextView dv;
	private TextView apv;
	private TextView atv;
	private TextView lv;
	private TextView eev;
	private EditText env;
	private EditText eendv;
	private MultiAutoCompleteTextView eesv;
	private Configuration config;
	private String[] data;
	private int[] dataNOW;
	private boolean begin = false;
	private boolean end = false;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
	public Dialog err;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);
	
		Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arialBold.ttf");
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial.ttf");
		data = new String[3];
		dataNOW = new int[3];
		
		apv = (TextView) v.findViewById(R.id.aPartir);
		dv = (TextView) v.findViewById(R.id.data_view);
		atv = (TextView) v.findViewById(R.id.ateTextView);
		lv = (TextView) v.findViewById(R.id.local);
		eev = (TextView) v.findViewById(R.id.escolhaEsportes);
		env = (EditText) v.findViewById(R.id.escolha_nome);
		eendv = (EditText) v.findViewById(R.id.escolha_endereco);
		eesv = (MultiAutoCompleteTextView) v.findViewById(R.id.escolha_esporte);
	
		/*apv.setTypeface(fontBold);
		dv.setTypeface(fontBold);
		atv.setTypeface(fontBold);
		lv.setTypeface(fontBold);
		eev.setTypeface(fontBold);
		env.setTypeface(font);
		eendv.setTypeface(font);
		eesv.setTypeface(font);*/

		config = getActivity().getResources().getConfiguration();

		String[] str={"Baseball","Basquete","Boliche","Boxe","Cartas","Ciclismo","Corrida",
				"Domin�","Futebol","Futebol Americano","Golfe","Patina��o","Sinuca",
				"Skate", "T�nis", "T�nis de Mesa", "Video-Game", "V�lei", "V�lei de Praia", 
		"Xadrez"};

		eesv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

		ArrayAdapter<String> adp=new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_dropdown_item_1line,str);

		eesv.setThreshold(1);
		eesv.setAdapter(adp);


		DateTime now = DateTime.now();
		this.dataNOW[0] = now.getDayOfMonth();
		this.dataNOW[1] = now.getMonthOfYear();
		this.dataNOW[2] = now.getYear();
		
		bd = (Button) v.findViewById(R.id.buttonDia);
		this.data[0] = now.getDayOfMonth()+"";
		this.data[1] = now.getMonthOfYear()+"";
		this.data[2] = now.getYear()+"";
		//bd.setTypeface(fontBold);
		//bd.setText(now.getDayOfMonth() + " de " + this.parseMonth(now.getMonthOfYear()) + " de " + now.getYear());
		String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : "" + now.getDayOfMonth();
		String month = (now.getMonthOfYear()) < 10 ? "0" + (now.getMonthOfYear()) : "" + (now.getMonthOfYear());
		bd.setText(day + "/" + month + "/" + now.getYear());
		
		b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		//b2.setTypeface(fontBold);
		b2.setText("00:00");

		b3 = (Button) v.findViewById(R.id.buttonDataFim);
		//b3.setTypeface(fontBold);
		b3.setText("23:59");

		bg = (Button) v.findViewById(R.id.bigButton);
		//bg.setTypeface(fontBold);
		//bg.setText("Pesquisar");

		Spannable buttonLabel = new SpannableString("   Pesquisar");
		buttonLabel.setSpan(new ImageSpan(this.getActivity(), R.drawable.lupa_pesq,      
		    ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		bg.setText(buttonLabel);
		
		bd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Date Picker for mid to high-end smartphones
				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Date Picker **/
					DateTime now = DateTime.now();
					CalendarDatePickerDialog calendar = CalendarDatePickerDialog.newInstance(PesquisarEventosFragment.this, 
							now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

					calendar.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
				} else { //Date Picker for low-end smartphones
					DatePickerBuilder dpb = new DatePickerBuilder()
					.setFragmentManager(getChildFragmentManager())
					.setTargetFragment(PesquisarEventosFragment.this)
					.setStyleResId(R.style.BetterPickersLowEndTheme);
					dpb.show();
				}
			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Time picker for mid to high-end smartphones
				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Time Picker **/
					DateTime now = DateTime.now();
					RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(PesquisarEventosFragment.this, 
							now.getHourOfDay(), now.getMinuteOfHour(), 
							DateFormat.is24HourFormat(getActivity()));

					radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				} else { //Time picker for low end smartphones
					/** Better Time Picker **/
					TimePickerBuilder tpb = new TimePickerBuilder();
					tpb.setFragmentManager(getChildFragmentManager());
					tpb.setTargetFragment(PesquisarEventosFragment.this);
					tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
					tpb.show();
				}
				begin = true;
			}
		});

		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Time picker for mid to high-end smartphones
				if((config.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) > 1) {
					/** Radial Time Picker **/
					DateTime now = DateTime.now();
					RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(PesquisarEventosFragment.this, 
							now.getHourOfDay(), now.getMinuteOfHour(), 
							DateFormat.is24HourFormat(getActivity()));

					radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				} else { //Time picker for low-end smartphones
					/** Better Time Picker **/
					TimePickerBuilder tpb = new TimePickerBuilder();
					tpb.setFragmentManager(getChildFragmentManager());
					tpb.setTargetFragment(PesquisarEventosFragment.this);
					tpb.setStyleResId(R.style.BetterPickersLowEndTheme);
					tpb.show();
				}
				end = true;
			}
		});

		bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ListEventosFragment list = new ListEventosFragment();

				Bundle args = new Bundle();

				String[] esportes;
				String textoEsportes = eesv.getText().toString();
				//String textoEsportes = "Basquete;Futebol;Futebol Americano;";
				if(textoEsportes==null ||textoEsportes.equals("") ){
					esportes=null;
				}else
				if(!textoEsportes.contains(",")){
					esportes = new String[1];
					esportes[0] = textoEsportes;
				} else {

					/*while(!textoEsportes.equals("")){
						esportes[i] = textoEsportes.substring(0, textoEsportes.indexOf(','));
						textoEsportes = textoEsportes.substring(textoEsportes.indexOf(',')+1);
						textoEsportes.trim();
						++i;
					}*/
					esportes = textoEsportes.split(",");
				}

				int j = 0;
				if(esportes!=null ){
					Log.v("esporte", j + ": " + esportes[j]+" + "+textoEsportes+ "  "+esportes.length);
					++j;
				}

				args.putInt("esportes_qtd",esportes==null?0 : esportes.length);
				args.putStringArray("esportes", esportes);
				args.putString("endereco", eendv.getText().toString());
				//Log.v("endedeco", eendv.getText().toString());
				args.putString("nome", env.getText().toString());
				//Log.v("nome local", env.getText().toString());
				args.putString("data", (data[2]+"-"+data[1]+"-"+data[0]));
				//Log.v("data", (data[2]+"-"+data[1]+"-"+data[0]));

				args.putString("horaInicio", b2.getText().toString());
				//Log.v("hora inicio",b2.getText().toString());
				args.putString("horaTermino", b3.getText().toString());
				//Log.v("hora fim", b3.getText().toString());

				list.setArguments(args);
				((MainActivity)getActivity()).mudarAba(1, list);
				((MainActivity)getActivity()).mudarAba(1);
			}
		});

		return v;
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
			//bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
			bd.setText(day + "/" + month + "/" + year);
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
			bd.setText(day + "/" + month + "/" + year);
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
			if((h + ":" + m).compareTo(b3.getText().toString()) < 0){
				b2.setText(h + ":" + m);
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
			if((h + ":" + m).compareTo(b2.getText().toString()) <= 0){
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
				b3.setText(h + ":" + m);
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
			if((h + ":" + m).compareTo(b3.getText().toString()) < 0){
				b2.setText(h + ":" + m);
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
			Log.v("aaaaaa",(h + ":" + m));
			Log.v("bbbbbb", b2.getText().toString());
			if((h + ":" + m).compareTo(b2.getText().toString()) <= 0){
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
				b3.setText(h + ":" + m);
				end = false;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		RadialTimePickerDialog rtpd = (RadialTimePickerDialog) getFragmentManager().findFragmentByTag(
				FRAG_TAG_TIME_PICKER);
		if (rtpd != null) {
			rtpd.setOnTimeSetListener(this);
		}
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
}
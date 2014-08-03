package br.com.JoinAndPlay;

import org.joda.time.DateTime;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CriarEventosFragment extends Fragment implements RadialTimePickerDialog.OnTimeSetListener, 
	CalendarDatePickerDialog.OnDateSetListener {
	
	private boolean begin;
	private boolean end;
	private boolean pago;
	private boolean temEsporte;
	private boolean nomeLugar;
	private boolean endLugar;
	
	private CheckBox checkPago;
	
	private Button bProximo;
	private Button bOndeJogar;
	private Button bDataInicio;
	private Button bDataFim;
	private Button bDia;
		
	private EditText eEsporte;
	private EditText eNomeLugar;
	private EditText eEnderecoLugar;
	private EditText ePreco;
	
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);


	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(container==null) return null;
		
		View view = inflater.inflate(R.layout.criar_evento, container,false);
		
		begin = false;
		end=false;
		pago=false;
		endLugar=false;
		nomeLugar=false;
		temEsporte=true; //controla se foi escrito o esporte, deve ser inicializado com falso apos integracao
		
		ePreco = (EditText) view.findViewById(R.id.escolha_preco);	
		ePreco.setVisibility(View.INVISIBLE);
		
		eEsporte = (EditText) view.findViewById(R.id.escolha_esporte);
		
		eNomeLugar = (EditText) view.findViewById(R.id.escolha_nome);
		eEnderecoLugar = (EditText) view.findViewById(R.id.escolha_endereco);
		
		checkPago = (CheckBox) view.findViewById(R.id.preco_box);
			
		bProximo = (Button) view.findViewById(R.id.nextButton);
		bProximo.setText("Pr�ximo");
		
		bOndeJogar = (Button) view.findViewById(R.id.escolha_lugar_button);
		bOndeJogar.setText("N�o sabe onde jogar?\nClique aqui e pesquise locais pr�ximos");
		
		bDataInicio = (Button) view.findViewById(R.id.buttonDataInicio);
		bDataInicio.setText("00:00");
		
		bDataFim = (Button) view.findViewById(R.id.buttonDataFim);
		bDataFim.setText("23:59");
		
		bDia = (Button) view.findViewById(R.id.buttonDia);
		DateTime now = DateTime.now();
		bDia.setText(now.getDayOfMonth() + " de " + this.parseMonth(now.getMonthOfYear()) + " de " + now.getYear());
		
		bProximo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(temEsporte){
					CriarEventosCompFragment nextPage = new CriarEventosCompFragment();
					((MainActivity)getActivity()).mudarAbaAtual(nextPage);
				} else {
					//exemplo de excecao
					Builder error = new AlertDialog.Builder(getActivity());
					error.setCancelable(true);
					error.setTitle("Alerta Join&Play");
					error.setIcon(0);
					error.setMessage("Campo de esporte ainda n�o preenchido!");
					error.setPositiveButton("OK", null);
					error.show();
				}

			}
		});
		
		bOndeJogar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChoosePlaceFragment choose = new ChoosePlaceFragment();
				((MainActivity)getActivity()).mudarAbaAtual(choose);	
			}
		});
		
		bDia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Radial Date Picker **/
				DateTime now = DateTime.now();
				CalendarDatePickerDialog calendar = CalendarDatePickerDialog.newInstance(CriarEventosFragment.this, 
						now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

				calendar.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
				end = true;
			}
		});
		
		bDataInicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/** Better Time Picker **/
				/* TimePickerBuilder tpb = new TimePickerBuilder();
				tpb.setFragmentManager(getChildFragmentManager());
				tpb.setTargetFragment(PesquisarEventosFragment.this);
				tpb.setStyleResId(R.style.BetterPickersDialogFragment);
				tpb.show();*/

				/** Radial Time Picker **/
				DateTime now = DateTime.now();
				RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(CriarEventosFragment.this, 
						now.getHourOfDay(), now.getMinuteOfHour(), 
						DateFormat.is24HourFormat(getActivity()));

				radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				begin = true;

			}
		});

		bDataFim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Radial Time Picker **/
				DateTime now = DateTime.now();
				RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(CriarEventosFragment.this, 
						now.getHourOfDay(), now.getMinuteOfHour(), 
						DateFormat.is24HourFormat(getActivity()));

				radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
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
		else return "fail";
	}

	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year,
			int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
        bDia.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
        
	}

	@Override
	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay,
			int minute) {
		// TODO Auto-generated method stub
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if(begin) {
			bDataInicio.setText(h + ":" + m);
			begin = false;
		} else if (end){
			bDataFim.setText(h + ":" + m);
			end = false;
		}
	}
}
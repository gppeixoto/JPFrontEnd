package br.com.JoinAndPlay;
import java.util.Calendar;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment.TimePickerDialogHandler;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.*;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;

public class PesquisarEventosFragment extends Fragment 
implements RadialTimePickerDialog.OnTimeSetListener, CalendarDatePickerDialog.OnDateSetListener,
			DatePickerDialogFragment.DatePickerDialogHandler,
			TimePickerDialogFragment.TimePickerDialogHandler  {

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
	private EditText eesv;
	private Configuration config;

	private boolean begin = false;
	private boolean end = false;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arialBold.ttf");
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial.ttf");

		apv = (TextView) v.findViewById(R.id.aPartir);
		dv = (TextView) v.findViewById(R.id.data_view);
		atv = (TextView) v.findViewById(R.id.ateTextView);
		lv = (TextView) v.findViewById(R.id.local);
		eev = (TextView) v.findViewById(R.id.escolhaEsportes);
		env = (EditText) v.findViewById(R.id.escolha_nome);
		eendv = (EditText) v.findViewById(R.id.escolha_endereco);
		eesv = (EditText) v.findViewById(R.id.escolha_esporte);
		apv.setTypeface(fontBold);
		dv.setTypeface(fontBold);
		atv.setTypeface(fontBold);
		lv.setTypeface(fontBold);
		eev.setTypeface(fontBold);
		env.setTypeface(font);
		eendv.setTypeface(font);
		eesv.setTypeface(font);

		config = getActivity().getResources().getConfiguration();

		bd = (Button) v.findViewById(R.id.buttonDia);
		DateTime now = DateTime.now();
		bd.setTypeface(fontBold);
		bd.setText(now.getDayOfMonth() + " de " + this.parseMonth(now.getMonthOfYear()) + " de " + now.getYear());

		b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		b2.setTypeface(fontBold);
		b2.setText("00:00");
		//b2.setText("Início");

		b3 = (Button) v.findViewById(R.id.buttonDataFim);
		b3.setTypeface(fontBold);
		b3.setText("23:59");
		//b3.setText("Fim");

		bg = (Button) v.findViewById(R.id.bigButton);
		bg.setTypeface(fontBold);
		bg.setText("Pesquisar");

		Drawable icon= getResources().getDrawable( R.drawable.ib_pesq);
		bg.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );

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
					.setStyleResId(R.style.BetterPickersDialogFragment);
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
					tpb.setStyleResId(R.style.BetterPickersDialogFragment);
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
					tpb.setStyleResId(R.style.BetterPickersDialogFragment);
					tpb.show();
				}
				end = true;
			}
		});

		bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).mudarAba(0);
			}
		});

		return v;
	}

	@Override
	public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
		bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
	}

	@Override
	public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
		bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
	}

	@Override
	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if(begin) {
			b2.setText(h + ":" + m);
			begin = false;
		} else if (end){
			b3.setText(h + ":" + m);
			end = false;
		}
	}

	@Override
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;

		if(begin) {
			b2.setText(h + ":" + m);
			begin = false;
		} else if (end){
			b3.setText(h + ":" + m);
			end = false;
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
		else if (n == 3) return "Março";
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
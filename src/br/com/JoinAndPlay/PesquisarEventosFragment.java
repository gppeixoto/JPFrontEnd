package br.com.JoinAndPlay;
import java.util.Calendar;

import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment.TimePickerDialogHandler;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.*;

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

import org.joda.time.DateTime;

public class PesquisarEventosFragment extends Fragment 
implements RadialTimePickerDialog.OnTimeSetListener, CalendarDatePickerDialog.OnDateSetListener {
	//implements TimePickerDialogFragment.TimePickerDialogHandler {

	private Button bg;
	private Button b2;
	private Button b3;
	private Button bd;
	private boolean begin = false;
	private boolean end = false;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
	static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		bd = (Button) v.findViewById(R.id.buttonDia);
		DateTime now = DateTime.now();
		bd.setText(now.getDayOfMonth() + " de " + this.parseMonth(now.getMonthOfYear()) + " de " + now.getYear());

		b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		//b2.setText("00:00");
		b2.setText("Início");

		b3 = (Button) v.findViewById(R.id.buttonDataFim);
		//b3.setText("23:59");
		b3.setText("Fim");

		bg = (Button) v.findViewById(R.id.bigButton);
		bg.setText("Pesquisar");

		Drawable icon= getResources().getDrawable( R.drawable.ib_pesq);
		bg.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );

		/*Calendar c = Calendar.getInstance();
		long millis = c.getTimeInMillis();

		CalendarView cv = (CalendarView) v.findViewById(R.id.calendarView1);
		cv.setDate(millis,true,true);*/


		bd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Radial Date Picker **/
				DateTime now = DateTime.now();
				CalendarDatePickerDialog calendar = CalendarDatePickerDialog.newInstance(PesquisarEventosFragment.this, 
						now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());

				calendar.show(getFragmentManager(), FRAG_TAG_DATE_PICKER);
				end = true;
			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/** Standard timepicker **/
				//showTimePickerDialog(v);

				/** Better Time Picker **/
				/* TimePickerBuilder tpb = new TimePickerBuilder();
				tpb.setFragmentManager(getChildFragmentManager());
				tpb.setTargetFragment(PesquisarEventosFragment.this);
				tpb.setStyleResId(R.style.BetterPickersDialogFragment);
				tpb.show();*/

				/** Radial Time Picker **/
				DateTime now = DateTime.now();
				RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(PesquisarEventosFragment.this, 
						now.getHourOfDay(), now.getMinuteOfHour(), 
						DateFormat.is24HourFormat(getActivity()));

				radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
				begin = true;

			}
		});

		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Radial Time Picker **/
				DateTime now = DateTime.now();
				RadialTimePickerDialog radial = RadialTimePickerDialog.newInstance(PesquisarEventosFragment.this, 
						now.getHourOfDay(), now.getMinuteOfHour(), 
						DateFormat.is24HourFormat(getActivity()));

				radial.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
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

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	@Override
    public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        bd.setText(dayOfMonth + " de " + this.parseMonth(monthOfYear+1) + " de " + year);
    }
	
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

	/*public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		b2.setText("" + hourOfDay + ":" + minute);
	}*/

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
		else return "fail";
	}
}
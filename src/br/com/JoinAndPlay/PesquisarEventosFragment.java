package br.com.JoinAndPlay;
import java.util.Calendar;

import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment.TimePickerDialogHandler;
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
implements RadialTimePickerDialog.OnTimeSetListener {
	//implements TimePickerDialogFragment.TimePickerDialogHandler {

	private Button bg;
	private Button b2;
	private Button b3;
	static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		bg = (Button) v.findViewById(R.id.bigButton);
		bg.setText("Pesquisar");

		b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		b2.setText("Início");

		b3 = (Button) v.findViewById(R.id.buttonDataFim);
		b3.setText("Término");

		Drawable icon= getResources().getDrawable( R.drawable.ib_pesq);
		bg.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );

		Calendar c = Calendar.getInstance();
		long millis = c.getTimeInMillis();

		CalendarView cv = (CalendarView) v.findViewById(R.id.calendarView1);
		cv.setDate(millis,true,true);

		bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).mudarAba(0);
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

			}
		});

		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});

		return v;
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute) {
		String h,m;
		h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
		m = minute < 10 ? "0" + minute : "" + minute;
		
		b2.setText(h + ":" + m);

	}

	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		b2.setText("" + hourOfDay + ":" + minute);
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


}
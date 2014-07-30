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
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

public class PesquisarEventosFragment extends Fragment 
//implements RadialTimePickerDialog.OnTimeSetListener {
implements TimePickerDialogFragment.TimePickerDialogHandler {
	
	private Button bg;
	private Button b2;
	private Button b3;
	private boolean mHasDialogFrame;
	 
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
		// year, month, day, hourOfDay, minute
		//c.set(1990, 7, 12, 9, 34);
		long millis = c.getTimeInMillis();

		CalendarView cv = (CalendarView) v.findViewById(R.id.calendarView1);
		//cv.setMinDate(millis-10000);
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
				Log.v("log1","oi");
				//showTimePickerDialog(v);
				TimePickerBuilder tpb = new TimePickerBuilder();
				tpb.setFragmentManager(getChildFragmentManager());
				tpb.setTargetFragment(PesquisarEventosFragment.this);
				tpb.setStyleResId(R.style.BetterPickersDialogFragment);
				Log.v("log1","oi2");
				/*.addTimePickerDialogHandler(PesquisarEventosFragment.this)
				.setFragmentManager(getFragmentManager())
				.setStyleResId(R.style.BetterPickersDialogFragment);*/
				
				tpb.show();
				Log.v("log1","oi3");
				/*Time now = new Time();
				now.setToNow();
                RadialTimePickerDialog timePickerDialog = RadialTimePickerDialog
                        .newInstance(PesquisarEventosFragment.this, now.getHourOfDay(), now.getMinuteOfHour(),
                                DateFormat.is24HourFormat(SampleRadialTimeDefault.this));
                if (mHasDialogFrame) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    ft.add(R.id.frame, timePickerDialog, FRAG_TAG_TIME_PICKER)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    timePickerDialog.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                }*/
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

	public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay,
			int minute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		b2.setText("" + hourOfDay + ":" + minute);
	}


}
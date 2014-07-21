package br.com.JoinAndPlay;
import java.util.Calendar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

public class PesquisarEventosFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.pesquisa_fragment, container,false);

		Button bg = (Button) v.findViewById(R.id.bigButton);
		bg.setText("Pesquisar");
		
		Button b2 = (Button) v.findViewById(R.id.buttonDataInicio);
		b2.setText("Início");
		
		Button b3 = (Button) v.findViewById(R.id.buttonDataFim);
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
				//Necessário obter especificação da designer sobre animações ao clicar nos botões.
				//Button b = (Button) v.findViewById(R.id.bigButton);
				//b.setTextColor(getResources().getColor(R.color.red));
				//b.setTextColor(getResources().getColor(R.color.white));
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
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
}
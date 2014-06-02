package br.com.JoinAndPlay;


import br.com.JoinAndPlay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class AgendaEventosFragment extends Fragment implements OnClickListener{

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			 View v=inflater.inflate(R.layout.fragment_list_event, container,false);
		    Button button = (Button) v.findViewById(R.id.button1);
		    button.setOnClickListener(this);
			return v;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.v("ola","mundo");
			fm=getFragmentManager();
			getFragmentManager().beginTransaction().add(R.id.lista,new AgendaEventosFragmentAntigos(fm)).addToBackStack(this.getClass().getName()).commit();	Log.v("ola","mundo");

		}
		FragmentManager fm;
}
class AgendaEventosFragmentAntigos extends Fragment implements OnClickListener{
	FragmentManager fm;
public AgendaEventosFragmentAntigos(FragmentManager fm) {
	// TODO Auto-generated constructor stub
	this.fm=fm;
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View v=inflater.inflate(R.layout.agenda_fragment, container,false);
	    Button button = (Button) v.findViewById(R.id.handle);
	    button.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.v("ola","mundo");
		getFragmentManager().popBackStack();
	}
}
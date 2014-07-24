package br.com.JoinAndPlay.Event;

import br.com.JoinAndPlay.ListEventosFragment;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class EventFragment extends Fragment implements OnClickListener{
 private ItemEvent myEvent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	
		if (container == null) {
			return null;
		}
		View v = inflater.inflate(R.layout.event_fragment, container, false);;
		Button b = (Button)v.findViewById(R.id.como_chegar);
		b.setOnClickListener(this);
		if(getArguments()!=null){
			
			myEvent= (ItemEvent)getArguments().getParcelable("evento");
			myEvent= myEvent==null?new ItemEvent():myEvent;
		}
		return  v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		((MainActivity)getActivity()).mudarAbaAtual(new ListEventosFragment());
	}

}

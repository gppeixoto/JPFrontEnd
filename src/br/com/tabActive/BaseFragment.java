package br.com.tabActive;

import br.com.JoinAndPlay.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment implements Runnable {
	private View load;
	void open(){

load.post(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			load.setVisibility(View.VISIBLE);

		}
	});

	}
	void close(){

load.post(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		load.setVisibility(View.INVISIBLE);

	}
});
	}	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.base_fragment, container, false);
		load= v.findViewById(R.id.loader);
		load.setVisibility(View.INVISIBLE);
		return v;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


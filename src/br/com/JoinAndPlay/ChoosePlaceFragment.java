package br.com.JoinAndPlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChoosePlaceFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		if(container==null) return null;
		View ret = inflater.inflate(R.layout.choose_place, container,false);
		
		return ret;
	}
}

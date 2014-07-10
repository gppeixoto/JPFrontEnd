package br.com.tabActive;


import java.util.List;

import br.com.JoinAndPlay.R;
import android.R.bool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class FragmentBase extends Fragment implements Runnable {
	public void run() {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();

		 ft.replace(R.id.tela_aba_1, newFragment.get(id)).commit();
	    
	     Log.v(";;;"+id,""+newFragment); 
	}

    private int id = 0;
    static List<Fragment> newFragment;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getArguments()!=null?getArguments().getInt("id"):0;
            // Do first time initialization -- add initial fragment.
    }       


    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tela_aba_fragment_1, container, false);
        Log.v("___"+id,""+newFragment);

        return v;
    }
@Override
public void onActivityCreated(Bundle n){
	super.onActivityCreated(n);
   
		    Log.v("---"+id,""+newFragment);
	getView().post(this);
}
@Override
public void setUserVisibleHint(boolean isVisibleToUser){
	

		super.setUserVisibleHint(isVisibleToUser);

}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

 
}

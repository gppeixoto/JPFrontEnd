package br.com.JoinAndPlay;


import java.util.ArrayList;

import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable.Factory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.Toast;


public class ListEventosFragment extends Fragment implements OnClickListener, OnTouchListener,OnItemClickListener {
	static ArrayList<ItemEvent> lista = new ArrayList<ItemEvent>();
	protected AdapterListView adapter ;
	protected Button Button_criar;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		adapter = new AdapterListView(getActivity(),lista);
		

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		//	setListAdapter(adapter);
		/*
			Button bt = new Button(getActivity());
			bt.setText("+");
			LinearLayout linear = new LinearLayout(getActivity() );
			linear.setOrientation(LinearLayout.VERTICAL);

            View v = super.onCreateView(inflater, container, savedInstanceState);
           v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
			v.setBackgroundResource(R.drawable.linha);
           bt.setBackgroundResource(R.drawable.seletc_tab);
           linear.addView(v);
		 */
		View tela=inflater.inflate(R.layout.fragment_list_event,container,false) ;
		ListView listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setOnItemClickListener(this);
		//redundancia para versoes antigas do android
		listV.setDivider(getResources().getDrawable(R.drawable.linha));

		Button_criar = (Button) tela.findViewById(R.id.button1);
		Button_criar.setText("Criar Evento");
		Button_criar.setOnClickListener(this);
		Button_criar.setTextColor(0xffffffff);
		Button_criar.setOnTouchListener(this);
		listV.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		return tela;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lista.add(new ItemEvent(null));
		adapter.notifyDataSetChanged();


	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public    void  onSaveInstanceState( Bundle outState){




	}
	@Override
	public boolean onTouch(View arg0,MotionEvent  arg1) {
		// TODO Auto-generated method stub
		switch (arg1.getAction()&255) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			Button_criar.setTextColor(getResources().getColor(R.color.red));


			break;

		default:
			Button_criar.setTextColor(getResources().getColor(R.color.white));

			break;
		}


		return false;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	ItemEvent item = lista.get(arg2);
lista.remove(arg2);
AgendaEventosFragment.lista.add(	item);

adapter.notifyDataSetChanged();

	}


}

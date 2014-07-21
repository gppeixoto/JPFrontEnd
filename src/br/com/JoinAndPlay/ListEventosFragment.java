package br.com.JoinAndPlay;

import br.com.tabActive.TabFragment;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Request.GraphUserCallback;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;

import br.com.JoinAndPlay.Event.EventFragment;
import br.com.JoinAndPlay.ListEvent.AdapterListView;
import br.com.JoinAndPlay.ListEvent.ItemEvent;
import android.Manifest.permission;
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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
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
        listV.setDividerHeight(20);
        View v=new View(getActivity());
        v.setMinimumHeight(15);
        listV.addHeaderView(v);
		Button_criar = (Button) tela.findViewById(R.id.bigButton);
		Button_criar.setText("Criar Evento");
		Button_criar.setOnClickListener(this);
		Button_criar.setTextColor(0xffffffff);
		Button_criar.setOnTouchListener(this);
		listV.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		lista.add(new ItemEvent(null));
		adapter.notifyDataSetChanged();lista.add(new ItemEvent(null));
		adapter.notifyDataSetChanged();


		return tela;
	}
	boolean loguin = true;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lista.add(new ItemEvent(null));
		adapter.notifyDataSetChanged();
List<String> PERMISSIONS = new ArrayList<String>();
PERMISSIONS.add("user_friends");
PERMISSIONS.add("public_profile");

PERMISSIONS.add("offline_access");

		if(loguin){
			Session session=			Session.openActiveSession(getActivity(), true,PERMISSIONS, new Session.StatusCallback() {

				// callback when session changes state
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					
					if (session.isOpened()) {

						// make request to the /me API
						Request.newMeRequest(session, new GraphUserCallback() {

							// callback after Graph API response with user object

							@Override
							public void onCompleted(GraphUser user, Response response) {
								// TODO Auto-generated method stub
						    	Log.v("uuou","dasasd"+		user);
								
							}
						}
								).executeAsync();
					}
				}
			});

	    	Log.v("token","dasasd"+		session.getPermissions());
	    	Log.v("token","dasasd"+session.isOpened());

	    	Log.v("token"," "+ session.getAccessToken());
	    	Log.v("token","dasasd");

		    if (session != null && session.isOpened()) {
		        Toast.makeText(getActivity(), session.getAccessToken(), Toast.LENGTH_LONG).show();

		    }
			
			loguin=false;
		}

	}
@Override
public void onResume(){
	super.onResume();
	
	
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
		arg2--;
		((MainActivity)getActivity()).mudarAba(0,new EventFragment() );
		ItemEvent item = lista.get(arg2);
		lista.remove(arg2);
		AgendaEventosFragment.lista.add(	item);

		adapter.notifyDataSetChanged();

	}


}

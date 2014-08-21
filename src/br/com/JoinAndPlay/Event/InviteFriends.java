package br.com.JoinAndPlay.Event;
import java.util.ArrayList;
import java.util.Vector;

import br.com.JoinAndPlay.BolaForaFragment;
import br.com.JoinAndPlay.ConfigJP;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class InviteFriends extends Fragment implements OnItemClickListener {
	LayoutInflater inflater;
	ListView listV;
	private ArrayList<Usuario> vetor;
	private AdapterAmigo adapter ;
	private String id_evento;
	boolean[] selector;
	private int ID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

		this.inflater=inflater;
		ViewGroup tela=(ViewGroup)inflater.inflate(R.layout.fragment_list_event,container,false) ;

		listV=(ListView) tela.findViewById(R.id.listView1);
		listV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listV.setOnItemClickListener(this);
		vetor=null;
		if(getArguments()!=null){
			vetor= getArguments().getParcelableArrayList("users");
			ID = getArguments().getInt("idTab");
			if(vetor.size()>0)
				selector= new boolean[vetor.size()];
			if(selector!=null)
				for (int i = 0; i < selector.length; i++) {
					selector[i]=false;
				}
			id_evento = getArguments().getString("id_evento");
		}
		if(vetor!=null){
			adapter=new AdapterAmigo(vetor, inflater,selector);
			listV.setAdapter(adapter);
		}
		Button invite = (Button) tela.findViewById(R.id.bigButton);
		invite.setText("Convidar amigos");
		invite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				SparseBooleanArray checked = listV.getCheckedItemPositions();
				Vector<String> conv = new Vector<String>();
				for (int i = 0; i < checked.size(); i++) {
					int position = checked.keyAt(i);
					if (checked.valueAt(i)) conv.add(((Usuario)(listV.getAdapter().getItem(position))).getId());
				}				
				Server.invite(ConfigJP.UserId, id_evento, conv, new Connecter<Boolean>(){
					@Override
					public void onTerminado(Boolean in) {
						Bundle args = new Bundle();
						args.putBoolean("internet",false);
						BolaForaFragment bfm = new BolaForaFragment();
						bfm.setArguments(args);
						MainActivity.self.mudarAba(ID,bfm);
					}
				});
				((MainActivity)MainActivity.self).retirarAbaAtual();
			}
		});
		return  tela;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
		selector[i-1]=!selector[i-1];

		if(adapter!=null){

			adapter.draw(arg1, i-1);
		}

	}

}

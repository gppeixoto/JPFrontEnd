package br.com.JoinAndPlay.Event;

import java.util.ArrayList;
import java.util.Vector;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import br.com.JoinAndPlay.MainActivity;
import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.ListFriend.AdapterGridViewFriend;
import br.com.JoinAndPlay.ListFriend.ItemFriend;
import br.com.JoinAndPlay.Server.Connecter;
import br.com.JoinAndPlay.Server.Server;
import br.com.JoinAndPlay.Server.Usuario;
import br.com.JoinAndPlay.gridViewWithScroll.ExpandableHeightGridView;

import com.facebook.Session;

public class InviteFriends extends Fragment implements OnItemClickListener{
	private Button inviteFriends;
	
	private ExpandableHeightGridView grid;
	
	private Vector<Usuario> aux;
	private ArrayList<ItemFriend> amigos;
	private Vector<String> convidados;
	
	private String id_evento;
			
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){

		View view = inflater.inflate(R.layout.invite_fragment, container,false);
		if(container==null || view ==null) return null;
		
		amigos = new ArrayList<ItemFriend>();
		convidados = new Vector<String>();
		
		Server.get_friends(getActivity(), new Connecter<Vector<Usuario>>(){

			@Override
			public void onTerminado(Vector<Usuario> in) {
				aux = (Vector<Usuario>) in;
				if(aux!=null){
					for(int i = 0; i < aux.size(); i++){
						amigos.add(new ItemFriend(aux.elementAt(i)));
					}
					grid.post(new Runnable() {						
						@Override
						public void run() {
							grid.setAdapter(new AdapterGridViewFriend(getActivity(), amigos));
						}
					});
				}	
			}
		});	
		
		grid = (ExpandableHeightGridView) view.findViewById(R.id.gridView_invite);
		grid.setExpanded(true);
		grid.setOnItemClickListener(this);
		
		inviteFriends = (Button) view.findViewById(R.id.criar_evento_button);
		inviteFriends.setText("Convidar Amigos");
		if(getArguments()!=null){
			Bundle args = getArguments();
			id_evento = args.getString("id_evento");
		}
		inviteFriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Server.invite(Session.getActiveSession().getAccessToken(), id_evento, convidados, null);
				((MainActivity)getActivity()).retirarAbaAtual();
			}
		});
			
		return view;	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ItemFriend f = amigos.get(position);
		if(f.getSelected()){
			amigos.get(position).deselected();
			if(convidados.contains(f.id)) convidados.remove(f.id);
		} else {
			amigos.get(position).selected();
			if(convidados.contains(f.id) == false) convidados.add(f.id);
		}
	}

}

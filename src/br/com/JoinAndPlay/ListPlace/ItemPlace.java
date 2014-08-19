package br.com.JoinAndPlay.ListPlace;

import br.com.JoinAndPlay.R;
import br.com.JoinAndPlay.Server.Local;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemPlace implements Parcelable {
	
	public String nomeLocal;
	public String cidade;
	public String telefone;
	public int preco_centavos;
	public String bairro;
	public String rua;
	public String complemento;
	public Local local;
	
	public ItemPlace(){
		
	}
	
	public ItemPlace(Parcel in){
		nomeLocal =in.readString();
		telefone = in.readString();
		cidade =in.readString();
		rua=in.readString();
		bairro =in.readString();
		preco_centavos =in.readInt();
		complemento = in.readString();
		local=(Local)in.readSerializable();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(bairro);
		arg0.writeString(nomeLocal);
		arg0.writeString(cidade);
		arg0.writeString(rua);
		arg0.writeInt(preco_centavos);
		arg0.writeString(telefone);
		arg0.writeSerializable(local);
	}
	
	
	public void drawerView(View v){
		
		ImageView icone_local=(ImageView) v.findViewById(R.id.local_icone);

		TextView localView = (TextView) v.findViewById(R.id.local_nome);
		if(nomeLocal!=null){
			localView.setText(nomeLocal);
		}
		
		TextView ruaComplementoView = (TextView) v.findViewById(R.id.local_rua_complemento);
		if(rua!=null){
			if(complemento!=null){
				ruaComplementoView.setText(rua + ", " + complemento);
			} else {
				ruaComplementoView.setText(rua);
			}
		}
		
		TextView telefoneView = (TextView) v.findViewById(R.id.local_telefone);
		if(telefone!=null) telefoneView.setText("Tel: "+telefone);
		
		TextView cidadeBairroView = (TextView) v.findViewById(R.id.local_bairro_cidade);
		if(cidade!=null && bairro!=null){
			cidadeBairroView.setText(bairro+ " - " +cidade);
		} else if(cidade!=null){
			cidadeBairroView.setText("Cidade: "+cidade);
		} else if(bairro!=null){
			cidadeBairroView.setText("Bairro: "+bairro);
		}
		
		TextView precoView = (TextView) v.findViewById(R.id.local_preco);
		if(preco_centavos==0){
			precoView.setText("0,00");
		}else{
			String temp=""+preco_centavos;
			int qtd=temp.length();
			for (; qtd <3; qtd++) {
				temp=0+temp;
			}
			temp= temp.substring(0,qtd-2 )+","+temp.substring(qtd-2);
			precoView.setText(temp);

		}	
	}
}
package br.com.JoinAndPlay.ListEvent;

import java.util.Random;

import br.com.JoinAndPlay.R;
import android.R.bool;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ItemEvent implements Parcelable ,Runnable{
int[] amigos;

Random gerador;
public void gerar(){
	for (int i = 0; i < amigos.length; i++) {
		amigos[i]=gerador.nextInt(7);
		for (int j = 0; j < i; j++) {
			if(amigos[j]==amigos[i]){
				
				i--;
				break;
			}
		}
	}

}
	public ItemEvent(Parcel in){
		gerador = new Random();

		amigos=new int[4];
		gerar();
		
	}
	 public static final Parcelable.Creator<ItemEvent> CREATOR  = new Parcelable.Creator<ItemEvent>() {
 public ItemEvent createFromParcel(Parcel in) {
     return new ItemEvent(in);
 }

 public ItemEvent[] newArray(int size) {
     return new ItemEvent[size];
 }
};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	View view;
	AdapterListView ad;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int i = gerador.nextInt(amigos.length);
		boolean saida= true;
		
		while(saida){
			
			amigos[i]=gerador.nextInt(7);
			for (int j = 0; j < amigos.length; j++) {
				saida=false;
				if((amigos[i]==amigos[j])&&(i!=j)){
					
saida=true;
break;}
			}
		}
	//	Log.v("gerar","["+amigos[0]+","+amigos[1]+","+amigos[2]+","+amigos[3]+"]"+amigos);

		((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap(ad.amigos[amigos[0]]);
		((ImageView) view.findViewById(R.id.imageView2)).setImageBitmap(ad.amigos[amigos[1]]);
		((ImageView) view.findViewById(R.id.imageView3)).setImageBitmap(ad.amigos[amigos[2]]);
		((ImageView) view.findViewById(R.id.imageView4)).setImageBitmap(ad.amigos[amigos[3]]);
		view.postDelayed(this,gerador.nextInt(9)*1000 + 1000);// gerador.nextInt());
	//	ad.notifyDataSetChanged();
	}
	

}
//4003-0484
//jackson
package br.com.JoinAndPlay.ListEvent;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemEvent implements Parcelable {

	public ItemEvent(Parcel in){
		
		
	}
	 public static final Parcelable.Creator<ItemEvent> CREATOR
     = new Parcelable.Creator<ItemEvent>() {
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
	

}
//4003-0484
//jackson
package br.com.JoinAndPlay;

import java.util.HashMap;
import java.util.Map;



public final class ConfigJP {

	private static Map<String,Integer> mapafutebol = new HashMap<String, Integer>();
	private static boolean init=false;
	private static void init(){
		if(!init){
			mapafutebol.put("futebol",ESPORTE_FUTEBOL);

		}
		init=true;
	}
	public static final int ESPORTE_FUTEBOL=0;

	public static final int[] ESPORTE_BITMAP ={
		R.drawable.futebol
	};
	public static final int[] ESPORTE_COR ={
		R.color.verde_futebol

	};
	public static final int[] ESPORTE_BARRA ={
		R.drawable.campo_esporte_futebol

	};


	public static final int getID(String esporte){
		init();
		esporte=esporte.toLowerCase();
		return mapafutebol.get(esporte);
	}


}

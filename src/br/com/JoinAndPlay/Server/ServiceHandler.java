package br.com.JoinAndPlay.Server;

                                                                                                                         
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

public class ServiceHandler {                                                                                            
                                                                                                                         
	public final static String HOST = "192.168.0.100";                                                                     
	public final static int PORT = 8000;                                                                                 
	public final static String URL_BASE = "http://join-play.herokuapp.com";                                           
	private final static int GET = 1, POST = 2;   
    ExecutorService executor = Executors.newFixedThreadPool(1);
                                                                                                                         
	public ServiceHandler() {                                                                                            
		                                                                                                                 
	}                                                                                                                    
	                                                                                                                     
	public void makeGET(String targetURL,Connecter<String> con) {
		MyThread t = new MyThread(targetURL, ServiceHandler.GET, null, con);
        executor.execute(t);
	}                                                                                           
	
	                                                                                                                     
	public void makePOST(String targetURL, String jsonData,Connecter<String>  con) {                                                          
		MyThread t = new MyThread(targetURL, ServiceHandler.POST, jsonData, con);
        executor.execute(t);
 	}
	
	private class MyThread extends Thread {
		private String targetURL;
		private String jsonData;
		private int method;
		private String retorno;
		Connecter<String>  con;
		
		public MyThread(String targetURL, int method, String jsonData,Connecter<String>  con) {
			this.targetURL = targetURL;
			this.jsonData = jsonData;
			this.method = method;
			this.con = con;
			this.retorno = null;
		}
		
		@Override
		public void run() {
			//Log.v("enviando", targetURL+jsonData);

			if (this.method == ServiceHandler.GET) {
				retorno = makeRequest(this.targetURL, null, ServiceHandler.GET);
		//	Log.v("user...",this.targetURL+""+ retorno);
				if (con != null) con.onTerminado(retorno);
			} else {
				retorno = makeRequest(this.targetURL, this.jsonData, ServiceHandler.POST);
		///	Log.v("user..",this.targetURL+""+ retorno);
				if (con != null) con.onTerminado(retorno);
			}
		}
	}
                                                                                                                         
	private String makeRequest(String targetURL, String jsonData, int method) {                                          
		try {                                                                                                            
			URL url = new URL(targetURL);                                                                                
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();                                  
			try {                                                                                                        
				if (method == ServiceHandler.POST) {                                                                     
					byte[] data = jsonData.getBytes();                                                                   
					urlConnection.setDoInput(true);                                                                      
					urlConnection.setDoOutput(true);                                                                     
					urlConnection.setFixedLengthStreamingMode(data.length);                                              
					urlConnection.setRequestProperty("Content-Type", "application/json");                                
					urlConnection.setRequestMethod("POST");                                                              
	                                                                                                                     
					OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());                        
					out.write(data);                                                                                     
					out.flush();                                                                                         
					out.close();                                                                                         
				} else {                                                                                                 
					urlConnection.setRequestMethod("GET");                                                               
				}                                                                                                        
                                                                                                                         
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));  
				StringBuilder sb = new StringBuilder();                                                                  
				                                                                                                         
				for (String line = in.readLine(); line != null; line = in.readLine()) {                                  
					sb.append(line + '\n');                                                                              
				}                                                                                                        
				                                                                                                         
				in.close();                                                                                              
				                                                                                                         
				return sb.toString();                                                                                    
			} catch(Exception _) {                                                                                       
                                                                                                                         
			} finally {                                                                                                  
				urlConnection.disconnect();                                                                              
			}                                                                                                            
		} catch (Exception _) {                                                                                          
                                                                                                                         
		}                                                                                                                
		return null;                                                                                                     
	}                                                                                                                    
}                                                                                                                        
                                                                                                                         
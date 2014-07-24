package br.com.JoinAndPlay.Server;

                                                                                                                         
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceHandler {                                                                                            
                                                                                                                         
	public final static String HOST = "192.168.0.110";                                                                     
	public final static int PORT = 8000;                                                                                 
	public final static String URL_BASE = "http://" + HOST + ":" + PORT;                                           
	private final static int GET = 1, POST = 2;                                                                          
                                                                                                                         
	public ServiceHandler() {                                                                                            
		                                                                                                                 
	}                                                                                                                    
	                                                                                                                     
	public String makeGET(String targetURL) {
		MyThread t = new MyThread(targetURL, ServiceHandler.GET, null);
		t.start();
		try { t.join(); } catch (Exception _) {}
		return t.retorno;                                                         
	}                                                                                           
	
	                                                                                                                     
	public String makePOST(String targetURL, String jsonData) {                                                          
		MyThread t = new MyThread(targetURL, ServiceHandler.POST, jsonData);
		t.start();
		try { t.join(); } catch (Exception _) {}
		return t.retorno;                                                    
	}
	
	private class MyThread extends Thread {
		private String targetURL;
		private String jsonData;
		private int method;
		private String retorno;
		
		public MyThread(String targetURL, int method, String jsonData) {
			this.targetURL = targetURL;
			this.jsonData = jsonData;
			this.method = method;
			this.retorno = null;
		}
		
		@Override
		public void run() {
			if (this.method == ServiceHandler.GET) {
				retorno = makeRequest(this.targetURL, null, ServiceHandler.GET);
			} else {
				retorno = makeRequest(this.targetURL, this.jsonData, ServiceHandler.POST);
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
                                                                                                                         
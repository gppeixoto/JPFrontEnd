package br.com.JoinAndPlay.Server;

                                                                                                                         
import java.io.BufferedOutputStream;                                                                                     
import java.io.BufferedReader;                                                                                           
import java.io.IOException;                                                                                              
import java.io.InputStreamReader;                                                                                        
import java.io.OutputStream;                                                                                             
import java.io.UnsupportedEncodingException;                                                                             
import java.net.HttpURLConnection;                                                                                       
import java.net.URL;                                                                                                     
                                                                                                                         
import org.apache.http.HttpEntity;                                                                                       
import org.apache.http.HttpResponse;                                                                                     
import org.apache.http.auth.AuthScope;                                                                                   
import org.apache.http.auth.UsernamePasswordCredentials;                                                                 
import org.apache.http.client.ClientProtocolException;                                                                   
import org.apache.http.client.methods.HttpGet;                                                                           
import org.apache.http.impl.auth.BasicScheme;                                                                            
import org.apache.http.impl.client.DefaultHttpClient;                                                                    
import org.apache.http.util.EntityUtils;                                                                                 
                                                                                                                         
public class ServiceHandler {                                                                                            
                                                                                                                         
	public final static String HOST = "192.168.0.105";                                                                     
	public final static int PORT = 8000;                                                                                 
	public final static String URL_BASE = "http://" + HOST + ":" + PORT + "/";                                           
	private final static int GET = 1, POST = 2;                                                                          
                                                                                                                         
	public ServiceHandler() {                                                                                            
		                                                                                                                 
	}                                                                                                                    
	                                                                                                                     
	public String makeGET(String targetURL
			) {                                                                            
		return makeRequest(targetURL, null, ServiceHandler.GET);                                                         
	}                                                                                           
	
	                                                                                                                     
	public String makePOST(String targetURL, String jsonData) {                                                          
		return makeRequest(targetURL, jsonData, ServiceHandler.POST);                                                    
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
                                                                                                                         
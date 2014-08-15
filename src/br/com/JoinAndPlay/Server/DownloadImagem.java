package br.com.JoinAndPlay.Server;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

public class DownloadImagem extends AsyncTask<String, Void, Bitmap>{
	ImageView img;
	static final int BufferSize= 100;
	static Bitmap[] buffer = new Bitmap[BufferSize];
	static String[] str = new String[BufferSize];

	public static void postLoad(final ImageView img,String url){
		final int id = (url.hashCode()<0?url.hashCode()*(-1):url.hashCode())%BufferSize;
		synchronized (buffer) {
			if(str[id]!=null && buffer[id]!=null && str[id].equals(url)){
				Log.v("imag++", url+" "+str[id]);
				if(img!=null){
					img.setImageBitmap(buffer[id]);
					img.setVisibility(View.VISIBLE);

				}
			}else{
				
				if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
					new DownloadImagem(img).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
				}else{
					new DownloadImagem(img).execute(url);

				}
			}
		}




	}
	private DownloadImagem(ImageView img){
		super();
		this.img=img;
	}

	@Override 
	protected Bitmap doInBackground(String... params) {
		String urlString = params[0];
		final int id = (urlString.hashCode()<0?urlString.hashCode()*(-1):urlString.hashCode())%BufferSize;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("GET");
			conexao.setDoInput(true);
			conexao.connect();
			InputStream is = conexao.getInputStream();
			Bitmap imagem = BitmapFactory.decodeStream(is);
			synchronized (buffer) {
				buffer[id]=imagem;
				str[id]=urlString;
			}
				return imagem;

			
		} catch (Exception e) { 
			e.printStackTrace();
		}
	
		return  null;
	}

	@Override 
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null){
			if(img!=null){

				img.setImageBitmap(result);
				img.setVisibility(View.VISIBLE);
			}
		} 
	} 
} 


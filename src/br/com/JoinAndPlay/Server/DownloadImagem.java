package br.com.JoinAndPlay.Server;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import br.com.JoinAndPlay.MainActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

public class DownloadImagem extends AsyncTask<String, Void,Pair<Bitmap,String>>{
	ImageView img;
	static final int BufferSize= 100;
	static Bitmap[] buffer = new Bitmap[BufferSize];
	static String[] str = new String[BufferSize];
	static Map<ImageView,String> map = new HashMap<ImageView, String>();
	public static void postLoad(final ImageView img,String url){

		final int id = (url.hashCode()<0?url.hashCode()*(-1):url.hashCode())%BufferSize;

		synchronized (map) {
			map.put(img,url);
		}
		synchronized (buffer) {
			if(str[id]!=null && buffer[id]!=null && str[id].equals(url)){
				if(img!=null){
					synchronized (map) {
						if(map.get(img).equals(url)){

							img.setImageBitmap(buffer[id]);

							img.setVisibility(View.VISIBLE);
							img.requestLayout();
							img.invalidate();
						}
					}
				}
			}else{

				if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
					try {
						new DownloadImagem(img).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);

					} catch (Exception e) {
						// TODO: handle exception
					}
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
	protected Pair<Bitmap,String> doInBackground(String... params) {
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

			return new Pair<Bitmap, String>(imagem,urlString);


		} catch (Exception e) { 
			e.printStackTrace();
		}

		return  null;
	}

	@Override 
	protected void onPostExecute(Pair<Bitmap, String> result) {
		super.onPostExecute(result);
		if (result != null && result.first!=null){
			if(img!=null){
				synchronized (map) {
					if(map.get(img) == null ){
						return;
					}else
					if( map.get(img).equals(result.second)){

						img.setImageBitmap(result.first);
						img.setVisibility(View.VISIBLE);
						img.requestLayout();
						img.invalidate();
						map.remove(result.first);
					}else{
						postLoad(img,map.get(img));
						
					}

				}

			}
		} 
	} 
} 


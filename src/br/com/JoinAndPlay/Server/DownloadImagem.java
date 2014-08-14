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

public class DownloadImagem extends AsyncTask<String, Void, Pair<Bitmap,ImageView>>{
	ProgressDialog dialog;
	Context context;
	ImageView img;
	static final int BufferSize= 100;
	static Bitmap[] buffer = new Bitmap[BufferSize];
	static String[] str = new String[BufferSize];
	static  DownloadImagem executor= new DownloadImagem();
	static List<ImageView>list = new ArrayList<ImageView>();

	public static void postLoad(final ImageView img,String url){
		final int id = (url.hashCode()<0?url.hashCode()*(-1):url.hashCode())%BufferSize;
		synchronized (buffer) {
			if(str[id]!=null && buffer[id]!=null && str[id].equals(url)){
				if(img!=null){

					img.setImageBitmap(buffer[id]);
					img.setVisibility(View.VISIBLE);

				}
			}else{
				list.add(img);
				if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
					new DownloadImagem().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
				}else{
					new DownloadImagem().execute(url);

				}
			}
		}




	}
	private DownloadImagem(){
		super();

	}

	@Override 
	protected Pair<Bitmap,ImageView> doInBackground(String... params) {
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
			return new Pair<Bitmap, ImageView>(imagem,		list.remove(0));
		} catch (Exception e) { 
			e.printStackTrace();
		}

		list.remove(0);
		return  null;
	}

	@Override 
	protected void onPostExecute(Pair<Bitmap,ImageView> result) {
		super.onPostExecute(result);
		if (result != null){
			if(result.second!=null){

				result.second.setImageBitmap(result.first);
				result.second.setVisibility(View.VISIBLE);
			}
		} else {
			//	AlertDialog.Builder builder = new AlertDialog.Builder(context). setTitle("Erro"). setMessage("N�o foi possivel carregar imagem, tente novamente mais tarde!"). setPositiveButton("OK", null);
			//builder.create().show();
		} 
	} 
} 


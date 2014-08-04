package br.com.JoinAndPlay.Server;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadImagemAsyncTask extends AsyncTask<String, Void, Bitmap>{
	ProgressDialog dialog;
	Context context;
	ImageView img;

	public DownloadImagemAsyncTask(Context context, ImageView img){
		this.context = context;
		this.img = img;

	}

	@Override 
	protected Bitmap doInBackground(String... params) {
		String urlString = params[0];
		try {
			URL url = new URL(urlString);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("GET");
			conexao.setDoInput(true);
			conexao.connect();
			InputStream is = conexao.getInputStream();
			Bitmap imagem = BitmapFactory.decodeStream(is);
			return imagem;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return null;
	}

	@Override 
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null){
			img.setImageBitmap(result);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(context). setTitle("Erro"). setMessage("Não foi possivel carregar imagem, tente novamente mais tarde!"). setPositiveButton("OK", null);
			//builder.create().show();
		} 
	} 
} 


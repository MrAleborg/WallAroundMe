package fr.univpau.leborgne.wallaroundme.asyncTask;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class SendMessage extends AsyncTask<Void, Void, Void> {

	HttpClient _client;
	HttpPost _req;
    private ProgressDialog progress;
    Activity _context;
    boolean success = true;
	
	public SendMessage(Activity context, HttpClient _client, HttpPost _req) {
		this._client = _client;
		this._req = _req;
		this._context = context;
		this.progress = new ProgressDialog(this._context);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			HttpResponse response = _client.execute(_req);
		} catch (ClientProtocolException e) {
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		return null;
	}

	 @Override
	    protected void onPreExecute() {                           
	        progress.setTitle("Veuillez patienter");
	        progress.setMessage("Envoi du message...");
	        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);      
	        progress.show();
	    }

	@Override
	protected void onPostExecute(Void result) {
		Intent i = new Intent();
		if(progress.isShowing()) 
			progress.dismiss();
		if(success)
		{
			i.putExtra("res", "Message envoyé avec succès");
			_context.setResult(Activity.RESULT_OK, i);
		}
		else
		{
			i.putExtra("res", "Le message n'a pas pu être envoyé");
			_context.setResult(Activity.RESULT_CANCELED, i);
		}
		_context.finish();
	}
	
}

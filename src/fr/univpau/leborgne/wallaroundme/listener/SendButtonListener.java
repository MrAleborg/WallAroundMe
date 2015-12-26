package fr.univpau.leborgne.wallaroundme.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import fr.univpau.leborgne.wallaroundme.asyncTask.SendMessage;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SendButtonListener implements OnClickListener {

	private static final String HTTPMETHOD = "POST";
	private static final String LATITUDE = "my_latitude";
	private static final String LONGITUDE = "my_longitude";
	private static final String MESSAGE = "my_message";
	private static final String GENDER = "my_gender";
	
	private Activity _context;
	private EditText messageEditText;
	private int gender;
	
	
	
	public SendButtonListener(Activity _context, EditText messageEditText,
			int gender) {
		this._context = _context;
		this.messageEditText = messageEditText;
		this.gender = gender;
	}



	@Override
	public void onClick(View v) {
		HttpClient client = new DefaultHttpClient();
		Log.i("adresse", WallAroundMe.NEWMESSAGE);
		HttpPost req = new HttpPost(WallAroundMe.NEWMESSAGE);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE); 
//        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, new fr.univpau.leborgne.wallaroundme.location.WAAMLocationListener( _context));
		Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		params.add(new BasicNameValuePair(LATITUDE, Double.toString(l.getLatitude())));
		params.add(new BasicNameValuePair(LONGITUDE, Double.toString(l.getLongitude())));
		Log.i("envoi message location", l.getLatitude()+" "+l.getLongitude());
		params.add(new BasicNameValuePair(MESSAGE, messageEditText.getText().toString()));
		params.add(new BasicNameValuePair(GENDER, Integer.toString(gender)));
		try {
			req.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		new SendMessage(_context ,client, req).execute();
		
	}

}

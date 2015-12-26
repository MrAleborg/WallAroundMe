package fr.univpau.leborgne.wallaroundme.asyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import fr.univpau.leborgne.wallaroundme.adapter.WallAroundMe_Adapter;
import fr.univpau.leborgne.wallaroundme.objects.Message;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadWallAroundMe extends AsyncTask <Void, Void, ArrayList<Message>> {

	private WallAroundMe w;
    private HttpClient client;
    private ProgressDialog progress;
    private JSONObject jsonobj;
    private JSONArray jsonarr;
    private Location location;
    private List<Message> messages;
    private int radius=5;

    private static final String TAG_MESSAGES = "items";
    private static final String TAG_TIME = "time";
    private static final String TAG_MSG = "msg";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_METERS = "meters";
    private static final String GEO = "geo";
    private static final String DEFAULT_CHARSET = "UTF-8";
    
	
    public DownloadWallAroundMe(WallAroundMe s, Location l, int rad) {
    	w = s;
		client = new DefaultHttpClient();
        progress = new ProgressDialog(w);
        this.location = l;
        messages = new ArrayList<Message>();
        this.radius = rad>5?rad:5;
	}
    
    @Override
    protected void onPreExecute() {                           
        progress.setTitle("Veuillez patienter");
        progress.setMessage("Chargement des messages...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);      
        progress.show();
    }

	@Override
	protected ArrayList<Message> doInBackground(Void... params) {
		InputStream content = null;
        //ArrayList<Message> messages = new ArrayList<Message>();
//        Log.i("coucou", "avant try");
        try {
//        	Log.i("coucou", "try");
//        	String uriStr = String.format("%smy_latitude=%s&my_longitude=%s&my_radius=%s", WallAroundMe.WALLMESSAGE, "48.856614", "2.352233", "2000000");
        	Double lat = new Double(location.getLatitude());
        	Double longit = new Double(location.getLongitude());
        	String uriStr = String.format("%smy_latitude=%s&my_longitude=%s&my_radius=%s", WallAroundMe.WALLMESSAGE, lat, longit, radius);
        	Log.i("location", lat.toString() + " , " + longit.toString());
//            Log.i("coucou", "uriStr OK " + uriStr);
        	HttpResponse response = this.client.execute(new HttpGet(uriStr));
            //content = response.getEntity().getContent();            
//            Log.i("coucou", "avant jsonstr");
            String jsonStr = EntityUtils.toString(response.getEntity());
//            byte[] utf8 = jsonStr.getBytes(DEFAULT_CHARSET);
//            jsonStr = new String(utf8, DEFAULT_CHARSET);
//            Log.i("jsonstr", jsonStr);
//            Log.i("coucou", "après jsonstr");
            if (jsonStr != null) { 
            	try {
            		jsonobj = new JSONObject(jsonStr);
            		jsonarr = jsonobj.getJSONArray(TAG_MESSAGES);
            		
            		for(int i=0; i<jsonarr.length(); i++) {
            			JSONObject o = jsonarr.getJSONObject(i);
            			String date = o.getString(TAG_TIME);
            			String m = o.getString(TAG_MSG);
            			String g = o.getString(TAG_GENDER);
            			String meters = o.getString(TAG_METERS);
            			JSONArray jsonarr2 = o.getJSONArray(GEO);
            			double geo[] = new double[2];
            			geo[0] = Double.parseDouble(jsonarr2.getString(0));
            			geo[1] = Double.parseDouble(jsonarr2.getString(1));
            			Log.i("geo", geo[0] + " " + geo[1]);
            			Message tmpM = new Message(m, date, Integer.parseInt(g));
            			tmpM.set_distance(Double.parseDouble(meters));
            			tmpM.set_geo(geo);
            			messages.add(tmpM);
            			
            		}
            		
            	} catch (Exception e){
            		Log.e("JSON", "JSON s'est royalement planté, mais c'est surement ma faute...", e);
            	}
        	}
            
        } catch (Exception e) { Log.e("RPC","Exception levée", e); }
        
		return null;
        
	}

	@Override
	protected void onPostExecute(ArrayList<Message> result) {
		
		if(progress.isShowing()) 
			progress.dismiss();
		w.populate(messages, null);
	}

	
 
}

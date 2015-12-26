package fr.univpau.leborgne.wallaroundme;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.leborgne.wallaroundme.R;
import fr.univpau.leborgne.wallaroundme.asyncTask.DownloadWallAroundMe;
import fr.univpau.leborgne.wallaroundme.listener.NextButtonListener;
import fr.univpau.leborgne.wallaroundme.listener.PrevButtonListener;
import fr.univpau.leborgne.wallaroundme.objects.Message;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;


public class WallAroundMe extends Activity {

	public static final String WALLMESSAGE = "http://www.iut-adouretud.univ-pau.fr/~olegoaer/waam/wallMessages.php?";
	public static final String NEWMESSAGE = "http://www.iut-adouretud.univ-pau.fr/~olegoaer/waam/newMessage.php";
	public static final String radius = "radius";
	public static final String gender = "gender";
	public static final String pagination = "pagination";
	public static final int maxChars = 140;
	private static final int nbItemsPage =15;
	
	private List<Message> _messages;
	private fr.univpau.leborgne.wallaroundme.adapter.WallAroundMe_Adapter _adapter;
	
    private LocationManager locationManager;
    private Location location;
    private SharedPreferences sp;
    private Integer _radius;
    private Boolean _pagination;
    private String _gender;
    private ListView V;
	Integer page = 1;
	int pages = 1;
	private Button _nextButton;
	private Button _prevButton;
	private RelativeLayout _header;
	private RelativeLayout _footer;
	private TextView _headerText;
	private Item _refreshItem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_around_me);
        
        _messages = new ArrayList<Message>();
        
        V = (ListView) findViewById(R.id.messageList);
        
        _nextButton = (Button) findViewById(R.id.nextButton);
        _prevButton = (Button) findViewById(R.id.prevButton);
        _header = (RelativeLayout) findViewById(R.id.header);
        _footer = (RelativeLayout) findViewById(R.id.footer);
        _headerText = (TextView) findViewById(R.id.headerText);
        
        
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        
        
//        _gender = sp.getString(gender, "");
//        _radius = sp.getInt(radius, 0);
//        _pagination = sp.getBoolean(pagination, false);
        
        Log.i("prefs", ""+_gender+" "+_radius+" "+_pagination);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
        
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, new fr.univpau.leborgne.wallaroundme.location.WAAMLocationListener(this));
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        _gender = sp.getString(gender, "");
        _radius = sp.getInt(radius, 0);
        _pagination = sp.getBoolean(pagination, false);
        new DownloadWallAroundMe(this, locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER), _radius).execute();
    	
    }

//    public void displayList(int p) {
//    	page = p;
//    	
//    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall_around_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent pref = new Intent(this, fr.univpau.leborgne.wallaroundme.activities.Preferences.class);
        	startActivity(pref);
        }
        else if (id == R.id.refresh_wall){
        	refreshActivity(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        else if (id == R.id.new_message){
        	Intent newMessage = new Intent(this, fr.univpau.leborgne.wallaroundme.activities.NewMessage.class);
        	startActivityForResult(newMessage, 1);
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void populate(List<Message> m, Integer p){
    	_messages = m;
    	if(p!=null){
    		if(p%pages!=0)
    			page = p%pages;
    		else page = pages;
    	}
        if(_pagination)
        {
        	        	
        	Log.i("pagination", "ok");
        	pages = (_messages.size()/nbItemsPage)+1;
        	Log.i("pagination", ""+pages);
        	Log.i("page", ""+page);
        	String text = "Page " + page + "/" + pages + " - " + _messages.size() + " messages" ;
        	
//        	TextView v = new TextView(this);
//        	v.setText(text);
//        	v.setTextColor(getResources().getColor(R.color.white));
        	
        	_footer.setVisibility(View.VISIBLE);
        	
        	_headerText.setText(text);

            fr.univpau.leborgne.wallaroundme.listener.NextButtonListener nextListener = new NextButtonListener(this, page, _messages);
            fr.univpau.leborgne.wallaroundme.listener.PrevButtonListener prevListener = new PrevButtonListener(this, page, _messages);
        	
            _nextButton.setOnClickListener(nextListener);
            _prevButton.setOnClickListener(prevListener);
            
        	ArrayList<Message> pageList = new ArrayList<Message>();
        	
        	if(_messages.size()>0)
        	{
	        	for(int i = 0; i<15; i++){
	        		int itemid = i+15*(page-1);
	        		if(_messages.size()>=itemid+1)
	        		pageList.add(_messages.get(itemid));
	        	}
        	}
	        _adapter = new fr.univpau.leborgne.wallaroundme.adapter.WallAroundMe_Adapter(this, R.layout.itemmessagelayout, pageList);
	        V.setAdapter(_adapter);  
	        Log.i("pagination", "oui "+pageList.size());
        }
        else {

        	String text = _messages.size() + " messages" ;
        	_headerText.setText(text);
        	_footer.setVisibility(View.GONE);
	        _adapter = new fr.univpau.leborgne.wallaroundme.adapter.WallAroundMe_Adapter(this, R.layout.itemmessagelayout, _messages);
	        V.setAdapter(_adapter);
	        Log.i("pagination", "NOPE");
        }
    	//_adapter.addAll(m);
    }
    
    public void refreshActivity(Location l){
    	_adapter.clear();
    	new DownloadWallAroundMe(this, l, _radius).execute();
    }
    
    @Override
   	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    	if (requestCode == 1)
    	{
    		try{
    			if(resultCode == RESULT_OK)
    			{
    				Toast toast = Toast.makeText(this, data.getCharSequenceExtra("res"), Toast.LENGTH_LONG);
    				toast.show();
    			}
    			if(resultCode == RESULT_CANCELED)
    			{
    				Toast toast = Toast.makeText(this, data.getCharSequenceExtra("res"), Toast.LENGTH_LONG);
    				toast.show();
    			}

    		}
    		catch(Exception e){
    			//Log.e("result", e.getMessage());
    			Toast toast = Toast.makeText(this, "Envoi annulé", Toast.LENGTH_LONG);
    			toast.show();
    		}

    	}

    }
    
}

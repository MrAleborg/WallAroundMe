package fr.univpau.leborgne.wallaroundme.activities;

import fr.univpau.leborgne.wallaroundme.R;
import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import fr.univpau.leborgne.wallaroundme.listener.MessageChangedListener;
import fr.univpau.leborgne.wallaroundme.listener.SendButtonListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class NewMessage extends Activity {

    private SharedPreferences sp;
	private int _gender;
	private ImageView _image;
	private EditText _messageTxt;
	private TextView _charRestants;
	private Button _sendButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(fr.univpau.leborgne.wallaroundme.R.layout.messagelayout);

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String g = sp.getString(WallAroundMe.gender, "1");

//        int g = ;
        Log.i("g", "g="+g);
        _gender = Integer.parseInt(g)%2;
        
        _image = (ImageView) findViewById(R.id.gender_newmessage);

        if(_gender==1)
        	_image.setImageDrawable(getResources().getDrawable(R.drawable.male));
        else if(_gender==0)
        	_image.setImageDrawable(getResources().getDrawable(R.drawable.female));
        

        _messageTxt = (EditText) findViewById(R.id.messageText);
        _charRestants = (TextView) findViewById(R.id.charRestants);
        _charRestants.setText("0/"+WallAroundMe.maxChars);
        
        MessageChangedListener watcher = new MessageChangedListener(this, _charRestants);
        
    	_messageTxt.addTextChangedListener(watcher);
    	
    	_sendButton = (Button) findViewById(R.id.sendButton);
    	
    	SendButtonListener l = new SendButtonListener(this, _messageTxt, _gender);
    	
        _sendButton.setOnClickListener(l);
        	
	}	
	
	
}

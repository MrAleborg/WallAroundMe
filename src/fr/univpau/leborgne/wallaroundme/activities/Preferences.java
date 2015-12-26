package fr.univpau.leborgne.wallaroundme.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class Preferences extends PreferenceActivity {


	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(fr.univpau.leborgne.wallaroundme.R.layout.radius);
		addPreferencesFromResource(fr.univpau.leborgne.wallaroundme.R.xml.preferences);
		
		sp = getPreferences(MODE_PRIVATE);
		
		Preference p = (Preference) findPreference("radius");
		
		
		fr.univpau.leborgne.wallaroundme.listener.RadiusListener listener = 
				new fr.univpau.leborgne.wallaroundme.listener.RadiusListener(this, p);
		p.setOnPreferenceChangeListener(listener);
	}

	
	
}

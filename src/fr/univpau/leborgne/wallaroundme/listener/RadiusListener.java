package fr.univpau.leborgne.wallaroundme.listener;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class RadiusListener implements OnPreferenceChangeListener {

	Context _context;
	Preference _preference;
	SharedPreferences sp;
	
	public RadiusListener(Context _context, Preference _preference) {
		this._context = _context;
		this._preference = _preference;
		_preference.setTitle("Radius : " + _preference.getSharedPreferences().getInt(WallAroundMe.radius, 0));
		
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if((Integer)newValue > 5)
		{
			_preference.setTitle("Radius : " + newValue);
		}
		else
			_preference.setTitle("Radius : " + 5);
		
		return true;
	}
	
	
	

	

}

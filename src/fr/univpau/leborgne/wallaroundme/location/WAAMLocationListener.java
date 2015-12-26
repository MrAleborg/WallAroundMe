package fr.univpau.leborgne.wallaroundme.location;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;

public class WAAMLocationListener implements LocationListener {

	WallAroundMe source;
	
	public WAAMLocationListener(WallAroundMe wallAroundMe) {
		source = wallAroundMe;
	}

	@Override
	public void onLocationChanged(Location location) {
		source.refreshActivity(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		source.startActivity(intent);
	}

}

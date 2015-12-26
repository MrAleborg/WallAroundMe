package fr.univpau.leborgne.wallaroundme.listener;

import fr.univpau.leborgne.wallaroundme.objects.Message;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class OnMessageClickListener implements OnClickListener {

	private Message _message;
	private Uri _uri;
	Intent _intent;
	private Context _context;
	private static final String PACKAGE = "com.google.android.apps.maps";
	
	public OnMessageClickListener(Message _message, Context _context) {
		this._message = _message;
		this._context = _context;
		this._uri = Uri.parse(String.format("geo:0,0?q=%s,%s(%s)", 
				Double.toString(this._message.get_geo()[0]), 
				Double.toString(this._message.get_geo()[1]),
				_message.get_message()));
		Log.i("geo_map", _message.get_geo()[0] + " " + _message.get_geo()[1]);
		this._intent = new Intent(Intent.ACTION_VIEW, this._uri);
		this._intent.setPackage(PACKAGE);
	}

	@Override
	public void onClick(View v) {
		if(_intent.resolveActivity(_context.getPackageManager()) != null)
			_context.startActivity(_intent);
	}

}

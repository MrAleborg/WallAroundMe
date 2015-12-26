package fr.univpau.leborgne.wallaroundme.adapter;

import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import fr.univpau.leborgne.wallaroundme.listener.OnMessageClickListener;
import fr.univpau.leborgne.wallaroundme.objects.Message;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.preference.PreferenceManager;

public class WallAroundMe_Adapter extends ArrayAdapter<Message> {

	private Context _context;
	List<Message> _messages = Collections.EMPTY_LIST;
		
	public WallAroundMe_Adapter(Context context, int textViewResourceId, List<Message> _messages) {
		super(context, textViewResourceId, _messages);
		this._context = context;
		this._messages = _messages;
	}
	
	public void updateList(List<Message> l){
		_messages = l;
		notifyDataSetChanged();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
				
		if(convertView == null){
			convertView = LayoutInflater
					.from(getContext())
					.inflate(fr.univpau.leborgne.wallaroundme.R.layout.itemmessagelayout, parent, false);
		}
		
			ImageView genderImage = (ImageView) convertView.findViewById(fr.univpau.leborgne.wallaroundme.R.id.image_gender);
			TextView messageText = (TextView) convertView.findViewById(fr.univpau.leborgne.wallaroundme.R.id.message_text);
			TextView messageDate = (TextView) convertView.findViewById(fr.univpau.leborgne.wallaroundme.R.id.message_date);
			TextView messageDistance = (TextView) convertView.findViewById(fr.univpau.leborgne.wallaroundme.R.id.distance);
						
			Message m = getItem(position);
		
			
			if(m.get_gender() == 0)
			{
				genderImage.setImageDrawable(_context.getResources().getDrawable(fr.univpau.leborgne.wallaroundme.R.drawable.female));
				convertView.setBackgroundColor(_context.getResources().getColor(fr.univpau.leborgne.wallaroundme.R.color.Rose));
			}
			else
			{
				genderImage.setImageDrawable(_context.getResources().getDrawable(fr.univpau.leborgne.wallaroundme.R.drawable.male));
				convertView.setBackgroundColor(_context.getResources().getColor(fr.univpau.leborgne.wallaroundme.R.color.Bleu));
			}			
			
			String utf8Text = "";
			
			try {
				utf8Text = new String(m.get_message().getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			messageText.setText(m.get_message());
			messageText.setText(utf8Text);
			messageDate.setText(m.get_date());
			messageDistance.setText((m.get_distance()) + " m");
//			Log.i("distance", messageDistance.getText().toString());
		
			OnMessageClickListener l = new OnMessageClickListener(m, _context);
			convertView.setOnClickListener(l);
			
		return convertView;
		
	}

}

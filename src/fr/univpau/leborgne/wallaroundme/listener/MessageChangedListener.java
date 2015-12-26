package fr.univpau.leborgne.wallaroundme.listener;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import fr.univpau.leborgne.wallaroundme.activities.NewMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MessageChangedListener implements TextWatcher {

	NewMessage _source;
	TextView _chars;
	//EditText _text;
	
	public MessageChangedListener(NewMessage newMessage, TextView _charRestants) {
		this._source = newMessage;
		_chars = _charRestants;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		_chars.setText(s.length() + "/" + WallAroundMe.maxChars);
	}

}

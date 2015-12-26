package fr.univpau.leborgne.wallaroundme.listener;

import java.util.List;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;
import fr.univpau.leborgne.wallaroundme.objects.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class PrevButtonListener implements OnClickListener {

	WallAroundMe _activity;
	int page;
	List<Message> messages;
	
	public PrevButtonListener(WallAroundMe _activity, int page, List<Message> m) {
		this._activity = _activity;
		this.page = page;
		this.messages = m;
	}

	@Override
	public void onClick(View v) {
		_activity.populate(messages, page-1);
	}

}

package fr.univpau.leborgne.wallaroundme.objects;

import fr.univpau.leborgne.wallaroundme.WallAroundMe;

public class Message {

	private String _message;
	private String _date;
	private int _gender;
	private Double _distance;
	private double _geo[];
	
	public double[] get_geo() {
		return _geo;
	}

	public void set_geo(double[] _geo) {
		this._geo = _geo;
	}

	public void set_distance(Double _distance) {
		this._distance = _distance;
	}

	public Double get_distance() {
		return _distance;
	}

	public void set_distance(double d) {
		this._distance = d;
	}

	public Message(String _message, String _date, int _gender) {
		this._geo = new double[2];
		this._message = _message;
		this._date = _date;
		this._gender = _gender;
	}

	public String get_message() {
		return _message;
	}

	public void set_message(String _message) {
		this._message = _message;
	}

	public String get_date() {
		return _date;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public int get_gender() {
		return _gender;
	}

	public void set_gender(int _gender) {
		this._gender = _gender;
	}
	
//	public void send() {
//		String url = ;
//	}
	
}

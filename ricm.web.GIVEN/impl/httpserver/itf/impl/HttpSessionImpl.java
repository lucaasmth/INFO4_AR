package httpserver.itf.impl;

import java.util.HashMap;

import httpserver.itf.HttpSession;

public class HttpSessionImpl implements HttpSession {

	private int id;
	private HashMap<String, Object> data;

	public HttpSessionImpl(int id) {
		data = new HashMap<String, Object>();
		this.id = id;
	}

	@Override
	public String getId() {
		return Integer.toString(id);
	}

	@Override
	public Object getValue(String key) {
		return this.data.get(key);

	}

	@Override
	public void setValue(String key, Object value) {
		this.data.put(key, value);
	}

}

package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	private HashMap<String, String> args;
	private HashMap<String, String> cookies;

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br,
			String cookiesString) throws IOException {
		super(hs, method, ressname, br);

		// Parse args and store them into a Hashmap
		args = new HashMap<String, String>();
		if (m_ressname.split("\\?").length > 1) {
			String args_string = m_ressname.split("\\?")[1];
			for (String arg : args_string.split("&")) {
				args.put(arg.split("=")[0], arg.split("=")[1]);
			}
		}

		// Parse cookies and store them into a Hashmap
		cookies = new HashMap<String, String>();
		for (String cookie : cookiesString.split(";")) {
			cookies.put(cookie.split("=")[0], cookie.split("=")[1]);
		}
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		return args.get(name);
	}

	@Override
	public String getCookie(String name) {
		return cookies.get(name);
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		String clsname = m_ressname; // /ricmlets/examples/CountRicmlet examples.CountRicmlet
		clsname = clsname.replace("/", ".").substring(10);
		clsname = clsname.split("\\?")[0];
		System.out.println(clsname);
		try {
			m_hs.getInstance(clsname).doGet(this, (HttpRicmletResponse) resp);
		} catch (ClassNotFoundException e) {
			resp.setReplyError(404, "Ricmlet not found");
		}

	}

}

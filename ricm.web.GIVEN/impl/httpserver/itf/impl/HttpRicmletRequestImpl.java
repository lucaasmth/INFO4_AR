package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname, br);
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		String url = this.m_ressname;
		String items_string = url.split("\\?")[1];
		String[] items = items_string.split("&");
		for (String item : items) {
			String key = item.split("=")[0];
			if (key.equals(name)) {
				return item.split("=")[1];
			}
		}

		return null;
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		String clsname = m_ressname; // /ricmlets/examples/CountRicmlet examples.CountRicmlet
		clsname = clsname.replace("/", ".").substring(10);
		clsname = clsname.split("\\?")[0];
		System.out.println(clsname);
		try {
//			Class<?> c = Class.forName(clsname);
//			HttpRicmlet ricmlet = (HttpRicmlet) c.getDeclaredConstructor().newInstance();
//			ricmlet.doGet(this, (HttpRicmletResponse) resp);
			m_hs.getInstance(clsname).doGet(this, (HttpRicmletResponse) resp);

		} catch (ClassNotFoundException e) {
			resp.setReplyError(404, "Ricmlet not found");
		}

	}

}

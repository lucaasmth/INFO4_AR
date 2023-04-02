package httpserver.itf.impl;

import java.io.PrintStream;

import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;

public class HttpRicmletResponseImpl extends HttpResponseImpl implements HttpRicmletResponse {

	protected HttpRicmletRequest m_req;

	protected HttpRicmletResponseImpl(HttpServer hs, HttpRicmletRequest req, PrintStream ps) {
		super(hs, req, ps);
		this.m_req = req;
	}

	@Override
	public void setCookie(String name, String value) {
		m_ps.println("Set-Cookie: " + name + "=" + value);
	}

	@Override
	public void setReplyOk() {
		super.setReplyOk();
		setCookie("session-id", m_req.getSession().getId());
	}

}

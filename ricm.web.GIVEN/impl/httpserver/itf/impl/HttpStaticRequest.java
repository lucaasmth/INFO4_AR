package httpserver.itf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

/*
 * This class allows to build an object representing an HTTP static request
 */
public class HttpStaticRequest extends HttpRequest {
	static final String DEFAULT_FILE = "index.html";

	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}

	public void process(HttpResponse resp) throws Exception {
		File file = new File(m_hs.getFolder() + m_ressname);
		if (!file.exists()) {
			resp.setReplyError(404, "File not found");
			return;
		}
		resp.setReplyOk();
		resp.setContentType(getContentType(m_ressname));
		resp.setContentLength((int) file.length());
		PrintStream ps = resp.beginBody();
		FileInputStream fis = new FileInputStream(file);
		ps.write(fis.readAllBytes());
		ps.flush();
		fis.close();
	}

}

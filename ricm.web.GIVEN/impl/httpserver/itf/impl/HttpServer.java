package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpSession;

/**
 * Basic HTTP Server Implementation
 * 
 * Only manages static requests The url for a static ressource is of the form:
 * "http//host:port/<path>/<ressource name>" For example, try accessing the
 * following urls from your brower: http://localhost:<port>/
 * http://localhost:<port>/voile.jpg ...
 */
public class HttpServer {

	private int m_port;
	private File m_folder; // default folder for accessing static resources (files)
	private ServerSocket m_ssoc;
	private HashMap<String, HttpRicmlet> ricmletInstances;
	private HashMap<Integer, HttpSession> sessions;
	private int sessionIndex;

	protected HttpServer(int port, String folderName) {
		m_port = port;
		if (!folderName.endsWith(File.separator))
			folderName = folderName + File.separator;
		m_folder = new File(folderName);
		ricmletInstances = new HashMap<String, HttpRicmlet>();
		sessions = new HashMap<Integer, HttpSession>();
		sessionIndex = 0;
		try {
			m_ssoc = new ServerSocket(m_port);
			System.out.println("HttpServer started on port " + m_port);
		} catch (IOException e) {
			System.out.println("HttpServer Exception:" + e);
			System.exit(1);
		}
	}

	public File getFolder() {
		return m_folder;
	}

	public HttpRicmlet getInstance(String clsname)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> c = Class.forName(clsname);
		HttpRicmlet instance = ricmletInstances.get(clsname);
		if (instance == null) {
			instance = (HttpRicmlet) c.getDeclaredConstructor().newInstance();
			ricmletInstances.put(clsname, instance);
		}
		return instance;
	}

	/*
	 * Reads a request on the given input stream and returns the corresponding
	 * HttpRequest object
	 */
	public HttpRequest getRequest(BufferedReader br) throws IOException {
		HttpRequest request = null;

		String startline = br.readLine();
		StringTokenizer parseline = new StringTokenizer(startline);
		String method = parseline.nextToken().toUpperCase();
		String ressname = parseline.nextToken();
		String next_line = br.readLine();
		String cookies = null;
		while (!next_line.equals("")) {
			if (next_line.split(":")[0].toLowerCase().equals("cookie")) {
				cookies = next_line.split(":")[1].trim();
				break;
			}
			next_line = br.readLine();
		}
		if (method.equals("GET")) {
			if (ressname.split("/")[1].equals("ricmlets")) {
				request = new HttpRicmletRequestImpl(this, method, ressname, br, cookies);
			} else {
				request = new HttpStaticRequest(this, method, ressname);
			}
		} else
			request = new UnknownRequest(this, method, ressname);
		return request;
	}

	/*
	 * Returns an HttpResponse object associated to the given HttpRequest object
	 */
	public HttpResponse getResponse(HttpRequest req, PrintStream ps) {
		if (req instanceof HttpRicmletRequest)
			return new HttpRicmletResponseImpl(this, (HttpRicmletRequest) req, ps);
		return new HttpResponseImpl(this, req, ps);
	}

	public HttpSession getSession(int sessionId) {
		deleteOldSessions();
		if (sessionId == -1) {
			HttpSession newSession = new HttpSessionImpl(sessionIndex++);
			sessions.put(Integer.parseInt(newSession.getId()), newSession);
			newSession.setValue("access", System.currentTimeMillis());
			return newSession;
		}
		HttpSession session = sessions.get(sessionId);
		if (session == null) {
			HttpSession newSession = new HttpSessionImpl(sessionId);
			sessions.put(Integer.parseInt(newSession.getId()), newSession);
			newSession.setValue("access", System.currentTimeMillis());
			return newSession;
		}
		session.setValue("access", System.currentTimeMillis());
		return sessions.get(sessionId);
	}

	private void deleteOldSessions() {
		for (Map.Entry<Integer, HttpSession> set : sessions.entrySet()) {
			if (((long) set.getValue().getValue("access")) + (10 * 1000) < System.currentTimeMillis()) {
				sessions.remove(set.getKey());
			}
		}
	}

	/*
	 * Server main loop
	 */
	protected void loop() {
		try {
			while (true) {
				Socket soc = m_ssoc.accept();
				(new HttpWorker(this, soc)).start();
			}
		} catch (IOException e) {
			System.out.println("HttpServer Exception, skipping request");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 0;
		if (args.length != 2) {
			System.out.println("Usage: java Server <port-number> <file folder>");
		} else {
			port = Integer.parseInt(args[0]);
			String foldername = args[1];
			HttpServer hs = new HttpServer(port, foldername);
			hs.loop();
		}
	}

}

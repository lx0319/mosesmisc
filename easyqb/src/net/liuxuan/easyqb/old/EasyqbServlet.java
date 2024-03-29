package net.liuxuan.easyqb.old;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.liuxuan.easyqb.old.SwaperFactory;
import net.liuxuan.util.Base64Coder;
import net.liuxuan.util.ExceptionUtils;




@SuppressWarnings("serial")
public class EasyqbServlet extends HttpServlet {
	public static String SwapServletUrl = "https://fwswaper.appspot.com/swap/"; // prod

	public static int SwaperConnTimeoutMS = 30000;
	public static int SwaperReadTimeoutMS = 30000;

	public static short FWSwaperAppVersion = 1;

	private static final Logger log = Logger.getLogger(HttpServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// resp.setContentType("text/plain");
		// resp.getWriter().println("Hello, world");
//		this.doGetPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
//		this.doGetPost(req, resp);
	}

	public void doGetPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		StringBuilder targetUrl = null;

		HttpURLConnection swaperConn = null;

		try {
			targetUrl = new StringBuilder(new String(Base64Coder.decode(req
					.getRequestURL().substring(SwapServletUrl.length())
					.toCharArray())));

			if ((targetUrl.length() > 0) && (req.getQueryString() != null)
					&& (req.getQueryString().length() > 1)) {
				targetUrl.append(String.format("?%s", req.getQueryString()));
			}

			// TODO: Consummate target url check pattern.
			// 1. Like non-http scheme;
			// 2. local resource;
			// 3. recurse slef
			// etc. .
			if (targetUrl.length() == 0) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			// Get target resource by swaper.

			log.info(String.format("Get target resource: '%s'.", targetUrl));

			swaperConn = (HttpURLConnection) new URL(targetUrl.toString())
					.openConnection();

			setupSwaperConnProperty(swaperConn, req);
			setupResponseProperty(resp, swaperConn);
			markFwswaperTagInResponseHead(resp);

			// Swap target!
			SwaperFactory.createSwaper().swap(swaperConn, resp);
		} catch (Exception e) {
			if (targetUrl != null)
				ExceptionUtils.swapFailedException(targetUrl.toString(), resp,
						e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			else
				ExceptionUtils.swapFailedException(resp, e,
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			if (swaperConn != null)
				swaperConn.disconnect();
		}
	}

	@SuppressWarnings("unchecked")
	protected static void setupSwaperConnProperty(HttpURLConnection swaperConn,
			HttpServletRequest req) throws ProtocolException {

		swaperConn.setConnectTimeout(SwaperConnTimeoutMS);
		swaperConn.setReadTimeout(SwaperReadTimeoutMS);

		// TODO: PoC: "java.io.IOException: http method POST against".
		swaperConn.setRequestMethod(req.getMethod());

		// http redirecting cookies (response code 3xx).
		swaperConn.setInstanceFollowRedirects(true);
		swaperConn.setUseCaches(false);

		Enumeration<String> e = (Enumeration<String>) req.getHeaderNames();
		String name = null;

		while (e.hasMoreElements()) {
			name = e.nextElement();
			swaperConn.setRequestProperty(name, req.getHeader(name));
		}
	}

	// TODO: Fix "Cookie rejected" warnning. domain must start with a dot.
	// WARNING: Cookie rejected:
	// "$Version=0; _javaeye3_session_=BAh7BiIKZmxhc2hJQzonQWN0aW9uQ29udHJvbGxlcjo6Rmxhc2g6OkZsYXNoSGFzaHsABjoKQHVzZWR7AA%3D%3D--d983012383f33595e8b4015c6235ad6e21fa81cf; $Path=/; $Domain=javaeye.com".
	// Domain attribute "javaeye.com" violates RFC 2109: domain must start with
	// a dot

	protected static void setupResponseProperty(HttpServletResponse resp,
			HttpURLConnection swaperConn) throws IOException {
		for (String name : swaperConn.getHeaderFields().keySet())
			resp.setHeader(name, swaperConn.getHeaderField(name));
	}

	protected static void markFwswaperTagInResponseHead(HttpServletResponse resp) {
		resp.setHeader("fwswaper", String.format("com.lzy.fwswaper.%d",
				FWSwaperAppVersion));
	}

}

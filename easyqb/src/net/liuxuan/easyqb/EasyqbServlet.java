package net.liuxuan.easyqb;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.liuxuan.gaeutils.PMF;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

@SuppressWarnings("serial")
public class EasyqbServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(HttpServlet.class
			.getName());


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// resp.setContentType("text/plain");
		// resp.getWriter().println("Hello, world");
		this.doGetPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		this.doGetPost(req, resp);
	}

	public void doGetPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// System.out.println("11111");
		// String message = URLEncoder.encode("my message", "UTF-8");

		resp.setCharacterEncoding("UTF-8");

		try {
			URL url = new URL("http://www.qiushibaike.com/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			conn.setRequestMethod("POST");

			try {
				Parser p = new Parser(conn);

				NodeFilter filter = new AndFilter(new HasAttributeFilter(
						"class", "qiushi_body article"), new TagNameFilter(
						"DIV"));
				NodeList nodelist = p.extractAllNodesThatMatch(filter);

				for (Node node : nodelist.toNodeArray()) {
					String id = ((Div) node).getAttribute("id");
					System.out.println(node.toHtml());
					// System.out.println(node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
					// "").replaceAll("<br/>", "\r\n").replaceAll("(\r\n){2,}",
					// "\r\n"));
					resp.getWriter().println(
							node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
							"").replaceAll("<br/>", "\r\n").replaceAll(
									"(\r\n){2,}", "\r\n"));
					
					PersistenceManager pm = PMF.get().getPersistenceManager();

					QiuShi qs;
					
					Query query = pm.newQuery(QiuShi.class);
					query.setFilter("QB_Id == qiubaiid");
				    query.declareParameters("String qiubaiid");
				    
				    try {
				        List<QiuShi> results = (List<QiuShi>) query.execute(id);
				        if (results.iterator().hasNext()) {
//				            for (QiuShi e : results) {
//				                // ...
//				            }
				        	//啥也不做了。
				        } else {
				            // ... no results ...
				        	qs = new QiuShi(id, node.toHtml().replaceAll(
									"<div.*>|</.*>|<p.*</p>", "").trim());
				        	pm.makePersistent(qs);
				        }
				    } finally {
				        query.closeAll();
				        pm.close();
				    }
					
					
					

					// System.out.println(node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
					// "").replaceAll("<br/>", "\r\n").replaceAll("(\r\n){2,}",
					// "\r\n"));
				}
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// ...
			log.log(Level.WARNING, e.getMessage());
		} catch (IOException e) {
			// ...
			log.log(Level.WARNING, e.getMessage());
		}

	}

}

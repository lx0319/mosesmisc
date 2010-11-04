package net.liuxuan.parser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.liuxuan.fetcher.ContentFetcher;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 
 * @author Moses
 */
public class ParerExample {

	public static void doHtmlParse2(String content) throws ParserException,
			MalformedURLException, IOException {
		URL url = new URL("http://www.qiushibaike.com/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
		conn.setRequestMethod("POST");
		try {
			Parser p = new Parser(conn);

			NodeFilter filter = new AndFilter(new HasAttributeFilter("class",
					"qiushi_body article"), new TagNameFilter("DIV"));
			NodeList nodelist = p.extractAllNodesThatMatch(filter);

			for (Node node : nodelist.toNodeArray()) {

				System.out.println(node.toHtml());

				// System.out.println(node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
				// "").replaceAll("<br/>", "\r\n").replaceAll("(\r\n){2,}",
				// "\r\n"));
				// resp.getWriter().println(node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
				// "").replaceAll("<br/>", "\r\n").replaceAll("(\r\n){2,}",
				// "\r\n"));
				// System.out.println(node.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
				// "").replaceAll("<br/>", "\r\n").replaceAll("(\r\n){2,}",
				// "\r\n"));
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void doHtmlParse1(String content) throws ParserException {
		Parser parser_div = new Parser(content);
		NodeFilter filter1 = new HasAttributeFilter("class",
				"qiushi_body article");
		NodeFilter filter2 = new TagNameFilter("DIV");
		NodeFilter filter = new AndFilter(filter1, filter2);

		NodeList nodelist = parser_div.extractAllNodesThatMatch(filter);

		Node node0 = nodelist.elementAt(1);
		Parser parser_ptag = new Parser(node0.toHtml());
		NodeFilter filter_ptag = new AndFilter(new HasAttributeFilter("class",
				"qiushi_body article"), new TagNameFilter("p"));
		Node Node1 = parser_ptag.extractAllNodesThatMatch(filter_ptag)
				.elementAt(1);

		System.out.println(node0.getText());
		System.out
				.println("====================================================");
		System.out.println(node0.getFirstChild().toPlainTextString().trim());
		System.out
				.println("====================================================");
		System.out.println(node0.toPlainTextString().trim());
		System.out
				.println("====================================================");
		System.out.println(node0.toHtml());
		System.out
				.println("====================================================");
		System.out.println(node0.toHtml().replaceAll("<div.*>|</.*>|<p.*</p>",
				"").replaceAll("<br/>", "\r\n")
				.replaceAll("(\r\n){2,}", "\r\n"));
		System.out
				.println("====================================================");
		System.out.println(((Div) node0).getAttribute("id"));
		// for(Node node:nodelist.toNodeArray()){
		// // System.out.println(node.toPlainTextString().trim());
		// System.out.println(node.toHtml());
		// }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ContentFetcher cf = new ContentFetcher();

		String a = cf.getContent("", "http://www.jinantuan.com/");
		try {
			doHtmlParse1(a);
		} catch (Exception ex) {
			
		}
	}

}

package net.liuxuan.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.SAXException;

public class NekoHtmlTest {
	public static void main(String[] argv) throws Exception {

//		sample3();
		String a = "        var sysSecond = parseInt(43467);var interValObj = window.setInterval(setRemainTime, 1000); var statusTimeout;";
		try {
			Pattern regex = Pattern.compile("sysSecond = parseInt\\((\\d+)\\);");
			Matcher ma = regex.matcher(a);
			System.out.println(ma.groupCount());
			System.out.println("-------");
			if(ma.find()){
				System.out.println(ma.group(1));
			}
			
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

	}

	public static void main2(String[] argv) throws Exception {
		DOMFragmentParser parser = new DOMFragmentParser();
		HTMLDocument document = new HTMLDocumentImpl();
		DocumentFragment fragment = document.createDocumentFragment();
		parser.parse("http://sourceforge.net/projects/nekohtml/", fragment);
		htmlprint(fragment, "");
	}

	public static void htmlprint(Node node, String indent) {
		System.out.println(indent + node.getClass().getName());
		Node child = node.getFirstChild();
		while (child != null) {
			print(child, indent + " ");
			child = child.getNextSibling();
		}
	}

	public static void sample2() throws SAXException, IOException {
		DOMParser parser = new DOMParser();
		// // parser.parse("D:\\springside3\\pom.xml");
		parser.parse("http://sourceforge.net/projects/nekohtml/");
		Document doc = parser.getDocument();

		Node child = doc.getFirstChild();

		while (child != null) {
			System.out.println(child.getClass().getName() + "\t");
			child = child.getNextSibling();
		}

		System.out.println(doc.getFirstChild().getTextContent());
		System.out.println(doc.getNodeValue());
		String productsXpath = "/html/body";
		NodeList products;

		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile(productsXpath);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			System.out.println(nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeValue());
			}
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void sample1() {

		Document doc = NekoHtmlUtils.getUrlDocument("http://www.jinantuan.com",
				"utf-8");
		// tags should be in upper case
		String titleXpathString = "//TITLE";
		String title = NekoHtmlUtils.extractNodeContent(doc, titleXpathString);

		String nameXpathString = "//DIV[@id='deal-intro' and @class='cf']/H1";
		String name = NekoHtmlUtils.extractNodeContent(doc, nameXpathString);

		String urlxpathString = "//DIV[@id='deal-intro' and @class='cf']/H1/A";
		String urls = NekoHtmlUtils.extractNodeLink(doc, urlxpathString);

		String timexpathString = "//DIV[@id='deal-timeleft' and @class='deal-box deal-timeleft deal-on']";
		String time1 = NekoHtmlUtils.extractNodeAttr(doc, timexpathString,
				"curtime");
		String time2 = NekoHtmlUtils.extractNodeAttr(doc, timexpathString,
				"diff");

		String imgsrcxpathString = "//DIV[@id='team_images' and @class='deal-buy-cover-img']/IMG";
		String imgsrc = NekoHtmlUtils.extractNodeAttr(doc, imgsrcxpathString,
				"src");
		// NodeList nl = NekoHtmlUtils.extractNodes(doc , timexpathString);
		System.out.println(title);
		System.out.println(name);
		System.out.println(urls);
		System.out.println(time1);
		System.out.println(time2);
		System.out.println(imgsrc);
		// Calendar ca = Calendar.getInstance();
		// ca.setTimeInMillis(Long.parseLong(time1));
		// Date date = new Date();
		// SimpleDateFormat dateFm = new SimpleDateFormat("EEEE-MMMM-dd-yyyy");
		// System.out.println(dateFm.format(date));
		// System.out.println(ca.YEAR+""+ca.MONTH+""+ca.DATE);

	}

	public static void sample3() {

		Document doc = NekoHtmlUtils.getUrlDocument(
				"http://go.qilehui.com/huigou.html", "gb2312");
		// tags should be in upper case
		String jsXpathString = "//DIV[@id='huigou_view']/SCRIPT";
		String js = NekoHtmlUtils.extractNodeContent(doc, jsXpathString);
		NodeList nl = NekoHtmlUtils.extractNodes(doc, jsXpathString);
		// NodeList nl = NekoHtmlUtils.extractNodes(doc , timexpathString);
		System.out.println(nl.getLength());
		System.out.println(js);

		// Calendar ca = Calendar.getInstance();
		// ca.setTimeInMillis(Long.parseLong(time1));
		// Date date = new Date();
		// SimpleDateFormat dateFm = new SimpleDateFormat("EEEE-MMMM-dd-yyyy");
		// System.out.println(dateFm.format(date));
		// System.out.println(ca.YEAR+""+ca.MONTH+""+ca.DATE);

	}

	public static void main1(String[] argv) throws Exception {
		DOMParser parser = new DOMParser();
		parser.parse("D:\\h.html");
		// parser
		// .parse("http://product.dangdang.com/product.aspx?product_id=9317290");
		print(parser.getDocument(), "");
	}

	public static void print(Node node, String indent) {
		// System.out.println(indent + node.getClass().getName() +"    "+
		// node.getTextContent());
		System.out.println(indent + node.getClass().getName());
		Node child = node.getFirstChild();
		while (child != null) {
			print(child, indent + "\t");
			child = child.getNextSibling();
		}
	}
}

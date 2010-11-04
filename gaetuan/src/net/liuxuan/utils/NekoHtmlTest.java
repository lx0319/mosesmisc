package net.liuxuan.utils;

import java.io.PrintWriter;

import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NekoHtmlTest {
	public static void main(String[] argv) throws Exception {
		NekoHtmlTest nh = new NekoHtmlTest();
		String url = "http://www.sentom.net/index.asp?page=2&classid=3&Nclassid=6";
		nh.printNodeValue(url);
		nh.parsePage(url);
		nh.fragment(url);
	}

	// 打印每个节点的值
	public void printNodeValue(String url) throws Exception {
		DOMParser parser = new DOMParser();
		parser.parse(url);
		print(parser.getDocument(), "");

	}

	public void fragment(String url) throws Exception {
		DOMFragmentParser parser = new DOMFragmentParser();
		HTMLDocument document = new HTMLDocumentImpl();

		DocumentFragment fragment = document.createDocumentFragment();
		parser.parse(url, fragment);
		print(fragment, "");

	}

	public static void print(Node node, String indent) {
		if (node.getNodeValue() != null) {
			if ("".equals(node.getNodeValue().trim())) {
			} else {
				System.out.print(indent);
				System.out.println(node.getNodeValue());
			}
		}

		Node child = node.getFirstChild();
		while (child != null) {
			print(child, indent + " ");
			child = child.getNextSibling();
		}
	}

	// 打印整个页面
	public void parsePage(String url) throws Exception {
		DOMParser parser = new DOMParser();
		parser.parse(url);
		print(parser.getDocument());
	}

	// 和Jtidy的一样，但中文不需要做特殊处理
	protected PrintWriter out = new PrintWriter(System.out);

	public void print(Node node) {

		if (node == null) {
			return;
		}

		int type = node.getNodeType();
		switch (type) {
			case Node.DOCUMENT_NODE:
				print(((Document) node).getDocumentElement());
				out.flush();
				break;

			case Node.ELEMENT_NODE:
				out.print('<');

				out.print(node.getNodeName());
				NamedNodeMap attrs = node.getAttributes();

				for (int i = 0; i < attrs.getLength(); i++) {
					out.print(' ');
					out.print(attrs.item(i).getNodeName());
					out.print("=\"");

					out.print(attrs.item(i).getNodeValue());
					out.print('"');
				}
				out.print('>');
				out.println(); // HACK
				NodeList children = node.getChildNodes();
				if (children != null) {
					int len = children.getLength();
					for (int i = 0; i < len; i++) {
						print(children.item(i));
					}
				}
				break;

			case Node.TEXT_NODE:
				out.print(node.getNodeValue());
				break;

		}

		if (type == Node.ELEMENT_NODE) {
			out.print("</");
			out.print(node.getNodeName());
			out.print('>');
			out.println();
		}

		out.flush();

	}
}

package net.liuxuan.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class NekoHtmlUtils {

	/**
	 * 根据指定的xpath抽取节点，返回一个NodeList
	 * 
	 * @param doc
	 * @param xpathString
	 * @return NodeList
	 */
	public static NodeList extractNodes(Node doc, String xpathString) {
		NodeList nodes = null;
		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile(xpathString);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			nodes = (NodeList) result;
			// System.out.println("元素个数"+nodes.getLength());
			// for (int i = 0; i < nodes.getLength(); i++) {
			// System.out.println(nodes.item(i).getTextContent());
			// }

		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nodes;
	}

	/**
	 * 获取指定xpath节点的内容。如果没有找到则返回空
	 * 
	 * @param doc
	 *            根节点
	 * @param xpathString
	 *            指定xpath
	 * @return
	 */
	public static String extractNodeContent(Node doc, String xpathString) {
		return extractNodeContent(doc, xpathString, "");
	}

	/**
	 * 获取制定xpath节点的内容。如果没有找到则返回空
	 * 
	 * @param doc
	 *            根节点
	 * @param xpathString
	 *            指定xpath
	 * @param indent
	 *            间隔
	 * @return
	 */
	public static String extractNodeContent(Node doc, String xpathString,
			String indent) {
		NodeList nodes;
		nodes = extractNodes(doc, xpathString);

		if (nodes.getLength() == 0) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < nodes.getLength(); i++) {
				sb.append(nodes.item(i).getTextContent());
				sb.append(indent);
			}
			return sb.toString();
		}
	}

	/**
	 * 从一个的document中，按照给定的xpath，找到链接返回。
	 * 
	 * @param doc
	 * @param xpathString
	 * @return
	 */
	public static String extractNodeLink(Node doc, String xpathString) {
		return extractNodeAttr(doc, xpathString, "href");
	}

	public static String extractNodeLink(Node node) {
		return extractNodeAttr(node, "href");
	}

	/**
	 * 从一个node中取出名为attrstr的属性
	 * @param node
	 * @param attrstr
	 * @return
	 */
	public static String extractNodeAttr(Node node, String attrstr) {
		String target = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			// if ("a".equalsIgnoreCase(node.getNodeName())) {
			NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				//
				Node attr = attrs.item(i);
				if (attrstr.equalsIgnoreCase(attr.getNodeName())) {
					target = attr.getNodeValue();// 在DOM树中，属性是一个结点。
					break;
				}
			}
			if (target != null) {
				return target;
			} else {
				return null;
			}
		}
		// }
		return null;
	}

	/**
	 * 给定一个document和xpath，通过xpath选定element后取出名为attrstr的属性值
	 * 
	 * @param node
	 * @param xpathstr
	 * @param attrstr
	 * @return
	 */
	public static String extractNodeAttr(Node node, String xpathstr,
			String attrstr) {
		NodeList nodes;
		nodes = extractNodes(node, xpathstr);

		for (int i = 0; i < nodes.getLength(); i++) {
			String link = extractNodeAttr(nodes.item(i), attrstr);
			if (link != null) {
				return link;
			}
		}
		return null;
	}

	// others code
	public static void getText(StringBuilder sb, Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			sb.append(node.getNodeValue());// 取得结点值，即开始与结束标签之间的信息
		}
		NodeList children = node.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				getText(sb, children.item(i));// 递归遍历DOM树
			}
		}
	}

	// others code
	public static void getOutlinks(URL base, ArrayList outlinks, Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if ("a".equalsIgnoreCase(node.getNodeName())) {
				StringBuilder linkText = new StringBuilder();
				getText(linkText, node);

				NamedNodeMap attrs = node.getAttributes();
				String target = null;
				for (int i = 0; i < attrs.getLength(); i++) {
					if ("href".equalsIgnoreCase(attrs.item(i).getNodeName())) {
						target = attrs.item(i).getNodeValue();// 在DOM树中，属性是一个结点。
						break;
					}
				}
				// if (target != null)
				// try {
				// URL url = new URL(base, target);
				// outlinks.add(Outlink(url.toString(), linkText
				// .toString().trim()));
				// } catch (MalformedURLException e) {
				// // don't care
				// }
			}
		}
		NodeList children = node.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				getOutlinks(base, outlinks, children.item(i));// 递归遍历DOM树
			}
		}
	}

	/**
	 * 得到指定URL的Document
	 * 
	 * @param url
	 * @return
	 */
	public static Document getUrlDocument(String url) {
		return getUrlDocument(url, "utf-8");
	}

	/**
	 * 得到指定URL的Document
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static Document getUrlDocument(String url, String charset) {
		DOMParser parser = new DOMParser();

		// 设置网页的默认编码
		try {
			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					charset);
			/*
			 * The Xerces HTML DOM implementation does not support namespaces
			 * and cannot represent XHTML documents with namespace information.
			 * Therefore, in order to use the default HTML DOM implementation
			 * with NekoHTML's DOMParser to parse XHTML documents, you must turn
			 * off namespace processing.
			 */
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
		} catch (SAXNotRecognizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getUrlDocument(parser, url, charset);

	}

	/**
	 * 得到指定URL的Document
	 * 
	 * @param parser
	 * @param url
	 * @param charset
	 * @return
	 */
	public static Document getUrlDocument(DOMParser parser, String url,
			String charset) {

		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new URL(url)
					.openStream(), charset));
			parser.parse(new InputSource(in));
			in.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = parser.getDocument();
		return doc;
	}

}

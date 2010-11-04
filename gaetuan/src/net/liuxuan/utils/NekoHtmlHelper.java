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

public class NekoHtmlHelper {

	private String indent = "\t"; // 缩进的标志
	private int indentnum = 0; // 缩进次数
	private String lineSeparator = "\n"; // 换行的标志
	private boolean noindentflag = false;
	private PrintWriter out = null; // outprinter
	StringBuilder outsb = new StringBuilder();

	public static void main(String[] argv) throws Exception {
		DOMParser parser = new DOMParser();
		// System.setProperty("http.proxyHost", "88.148.41.44");
		// System.setProperty("http.proxyPort", "51966");
		parser.setFeature("http://xml.org/sax/features/namespaces", false);
		parser.parse("http://www.google.com.hk");// 也可以是如：http://www.searchfull.net/blog

		// writeDoc(parser.getDocument().getDocumentElement());
		// print(parser.getDocument(), "");
		NekoHtmlHelper nhh = new NekoHtmlHelper();
		nhh.out = new PrintWriter(System.out);
		// nhh.printDOMTree(parser.getDocument());
		nhh.writeDoc(parser.getDocument());
		
		nhh.out.print(nhh.outsb.toString());
		nhh.out.flush();

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

	/** Prints the specified node, then prints all of its children. */
	/**
	 * 打印xml/html文档
	 */
	public void printDOMTree(Node node) {
		if (node == null) {
			return;
		}
		if (out == null) {
			return;
		}
		int type = node.getNodeType();
		switch (type) {
			// print the document element
			case Node.DOCUMENT_NODE: {
				out.println("<?xml version=\"1.0\" ?>");
				indentnum++;
				printDOMTree(((Document) node).getDocumentElement());
				indentnum--;
				out.flush();
				break;
			}

				// print element with attributes
			case Node.ELEMENT_NODE: {
				out.print("<");
				out.print(node.getNodeName());
				NamedNodeMap attrs = node.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					Node attr = attrs.item(i);
					out.print(" " + attr.getNodeName() + "=\""
							+ attr.getNodeValue() + "\"");
				}
				out.print(">");
				NodeList children = node.getChildNodes();
				if (children != null) {
					int len = children.getLength();
					for (int i = 0; i < len; i++) {
						indentnum++;
						printDOMTree(children.item(i));
						indentnum--;
					}
				}

				break;
			}

				// handle entity reference nodes
			case Node.ENTITY_REFERENCE_NODE: {
				out.print("&");
				out.print(node.getNodeName());
				out.print(";");
				break;
			}

				// print cdata sections
			case Node.CDATA_SECTION_NODE: {
				out.print("<![CDATA[");
				out.print(node.getNodeValue());
				out.print("]]>");
				break;
			}

				// print text
			case Node.TEXT_NODE: {
				out.print(node.getNodeValue());
				break;
			}

				// print processing instruction
			case Node.PROCESSING_INSTRUCTION_NODE: {
				out.print("<?");
				out.print(node.getNodeName());
				String data = node.getNodeValue();
				{
					out.print(" ");
					out.print(data);
				}
				out.print("?>");
				break;
			}
		}

		if (type == Node.ELEMENT_NODE) {
			out.println();
			out.print("</");
			out.print(node.getNodeName());
			out.print(">");
		}
		out.print(indent);
		out.flush();
	}

	/**
	 * 输出经过修整干净的html文档，fix up and clean html
	 * 
	 * @param node
	 */
	public String writeDoc(Node node) {
		short type = node.getNodeType();
		if (node == null) {
			return null;
		}
		switch (type) {
			case Node.DOCUMENT_NODE: {
				// out.println("<?xml version=\"1.0\" ?>");
				writeDoc(((Document) node).getDocumentElement());
				// out.flush();
				break;
			}
			case Node.ELEMENT_NODE: {
				indentnum++;
				// StringBuilder outsb = new StringBuilder();
				outsb.append(lineSeparator);
				appendindent(outsb);
				outsb.append("<");
				outsb.append(node.getNodeName());
				NamedNodeMap attrs = node.getAttributes();
				if (attrs != null) {
					int length = attrs.getLength();
					for (int i = 0; i < length; i++) {
						Node attr = attrs.item(i);
						outsb.append(" ").append(attr.getNodeName()).append(
								"=\"").append(attr.getNodeValue()).append("\"");
					}
				}
				outsb.append(">");
				// out.println(outsb.toString());

				NodeList children = node.getChildNodes();
				if (children != null) {
					int length = children.getLength();
					for (int i = 0; i < length; i++) {
						writeDoc(children.item(i));
					}
				}
				appendindent(outsb);
				outsb.append("</").append(node.getNodeName()).append(">");
				outsb.append(lineSeparator);
				indentnum--;
				break;
			}
			case Node.TEXT_NODE: {
				if(node.getNodeValue().trim().equals("")){
//					outsb.deleteCharAt(outsb.length()-1);
//					outsb.delete(outsb.length()-1, outsb.length());
					noindentflag = true;
					break;
				}
				if(node.getNodeValue().length()<20){
//					if(outsb.charAt(outsb.length()-1)==lineSeparator.charAt(lineSeparator.length()-1)){
//						outsb.deleteCharAt(outsb.length()-1);
//					}
//					outsb.delete(outsb.length()-1, outsb.length());
					outsb.append(node.getNodeValue());
					noindentflag = true;
					break;
				}
				indentnum++;
				outsb.append(lineSeparator);
				appendindent(outsb);
				outsb.append(node.getNodeValue());
				outsb.append(lineSeparator);
				indentnum--;
				break;
			}
		}
		// if (type == Node.ELEMENT_NODE) {
		// out.println();
		// out.print("</");
		// out.print(node.getNodeName());
		// out.print(">");
		// }
		// out.flush();
		
		return outsb.toString();
	}

	private void appendindent(StringBuilder outsb) {
		if(noindentflag){
			noindentflag = false;
			return;
		}
		for (int i = 0; i < indentnum - 1; i++) {
			// out.print(indent);
			outsb.append(indent);
		}
	}

	/**
	 * 抽取html文档里的文本Text
	 * 
	 * @param node
	 * @param indent
	 */
	public void print(Node node, String indent) {
		// System.out.println(indent+node.getClass().getName());
		if (node.getNodeValue() != null) {
			if ("".equals(node.getNodeValue().trim())) {

			} else {
				out.print(indent);
				out.println(node.getNodeValue());
			}
		}

		Node child = node.getFirstChild();
		while (child != null) {
			print(child, indent + " ");
			child = child.getNextSibling();
		}
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
}

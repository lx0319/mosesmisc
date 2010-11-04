/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newTofle;

import net.liuxuan.utils.FilePlus;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

/**
 *
 * @author Moses
 */
public class temp {
    public static void main(String[] args) throws ParserException {
        String content = FilePlus.ReadTextFileToStringLn("outputs\\index.html");
//        System.out.println(content);

        NodeFilter afilter = new TagNameFilter("a");
        NodeFilter lifilter = new TagNameFilter("li");
        NodeFilter lafilter = new AndFilter(new HasParentFilter(lifilter),afilter);

        Parser p = new Parser(content);
        NodeList nodelist = p.extractAllNodesThatMatch(lafilter);
        System.out.println(nodelist.size());
        for (int i = 0; i < nodelist.size(); i++) {
            LinkTag n = (LinkTag)nodelist.elementAt(i);
//            Node n = nodelist.elementAt(i);\
            String str = n.extractLink();
            if(str.startsWith("http://www.lixiaolai.com/index.php")){
                str = str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
                int intvalue = Integer.parseInt(str);
                str = String.format("%1$03d", (intvalue-7884))+".html";
                n.setLink(str);
            }

            System.out.println(str);

        }
        Node node =nodelist.elementAt(0);


        while(node.getParent()!=null){
            node = node.getParent();
        }
//        System.out.println(node.toHtml());
        FilePlus.writeText("outputs\\index1.html", node.toHtml());
//        System.out.println(p.toString());
//        System.out.println(nodelist.elementAt(0).getPage().getText());
        
    }
}

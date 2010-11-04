/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newTofle;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.management.Query;
import net.liuxuan.utils.FilePlus;
import net.liuxuan.utils.net.HC;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author Moses
 */
public class FetcherUtil {

    private static final Logger log = Logger.getLogger(FetcherUtil.class.getName());

    /**
     * 从nodelist中，将符合条件等于给定布尔量的node移除
     * @param nodelist
     * @param filter
     * @param bool
     */
    public static void pickNodes(NodeList nodelist, NodeFilter filter, boolean bool) {
        for (int i = nodelist.size() - 1; i >= 0; i--) {
            Node tempnode = nodelist.elementAt(i);
            if (filter.accept(tempnode) == bool) {
                nodelist.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        for(int i=7885;i<=8076;i++){
//        for(int i=7885;i<=7888;i++){
            GetOneChapter("http://www.lixiaolai.com/index.php/archives/"+i+".html");
        }

//        File f = new File("lxl\\aa.txt");
//        System.out.println(f.getAbsolutePath());
//        int a = 1;
//        String b = String.format("%1$03d", a);
//        System.out.println(b);
    }

    public static void GetOneChapter(String url) {
        try {
            //获取html文件名
            //http://www.lixiaolai.com/misc/toefltv/audio/092.mp3
            int position = url.lastIndexOf("/") > 0 ? url.lastIndexOf("/") : url.lastIndexOf("\\") > 0 ? url.lastIndexOf("\\") : 0;
            String htmlname = url.substring(position+1);
            String postnum = htmlname.substring(0, htmlname.indexOf("."));
            int ipostnum = Integer.parseInt(postnum);

            String strTitle = null;


            String content = HC.geturl(url);
            Parser p = new Parser(content);
            //定义多个filter。
            NodeFilter divfilter = new TagNameFilter("DIV");
            NodeFilter h1filter = new TagNameFilter("h1");
            NodeFilter pfilter = new TagNameFilter("p");
            NodeFilter afilter = new TagNameFilter("a");
            NodeFilter imgfilter = new TagNameFilter("img");
            NodeFilter filter = new AndFilter(
                    new HasAttributeFilter("id", "post-"+postnum),
                    divfilter);
            NodeList nodelist = p.extractAllNodesThatMatch(filter);
            //nodelist的第一个节点就是要获得的
            Node contentnode = nodelist.elementAt(0);
            ((Div) contentnode).setAttribute("style", "width:780", '"');
            ((Div) contentnode).setAttribute("class", "post", '"');
            nodelist = contentnode.getChildren();
//            System.out.println(nodelist.size());
            //只剩两个divnode了。


            pickNodes(nodelist, divfilter, false);
            Node node1 = nodelist.elementAt(0);
            Node node2 = nodelist.elementAt(1);
            NodeList subnl1 = node1.getChildren();
            pickNodes(subnl1, h1filter, false);

            if (subnl1.size() == 1) {
                strTitle = subnl1.asString().trim();
//                System.out.println(a);
            }


            NodeList subnl2 = node2.getChildren();
            NodeFilter tempfilter = new OrFilter(
                    new HasAttributeFilter("class", "audioplayer_container"),
                    new HasAttributeFilter("class", "post_tags"));
            pickNodes(subnl2, tempfilter, true);



            NodeFilter pafilter = new HasChildFilter(afilter);
            for (int i = subnl2.size() - 1; i >= 0; i--) {
                Node tempnode = subnl2.elementAt(i);
                if (pafilter.accept(tempnode) == true) {
                    pickNodes(tempnode.getChildren(), imgfilter, false);
//                    System.out.println(tempnode.toHtml());
//                    System.out.println("===");
//                    System.out.println(tempnode.getChildren().size());
//                    for (int j = 0; j < tempnode.getChildren().size(); j++) {
//                         System.out.println(tempnode.getChildren().elementAt(j));
//
//                    }
//                    System.out.println(tempnode.getChildren().elementAt(1).getChildren().size());
//                    System.out.println(tempnode.getChildren().size());
                    if (tempnode.getChildren().size() == 1) {
                        String a = ((ImageTag) tempnode.getChildren().elementAt(0)).extractImageLocn();
                        HC.download(a, "outputs\\images", null);
//                        System.out.println(a);
                        String jpgfilename = a.substring((a.lastIndexOf("/") > 0 ? a.lastIndexOf("/") : a.lastIndexOf("\\") > 0 ? a.lastIndexOf("\\") : 0)+1);
                        ((ImageTag) tempnode.getChildren().elementAt(0)).setImageURL("images/"+jpgfilename);
                    }

                }
            }


//            HC.download(url, url, htmlname)
            String outhtmlfilename = String.format("%1$03d", (ipostnum-7884))+".html";
            String header = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>"+strTitle+"</title><link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\"/></head><body>";
            String ender = "<div style=\"width:780;TEXT-ALIGN: center;\"><p><strong><a href="+(String.format("%1$03d", (ipostnum-7885))+".html")+">&nbsp;&lt;&nbsp;上一页&nbsp;</a>&nbsp;&nbsp;<a href=\"index.html\">&nbsp;&nbsp;首页&nbsp;</a>&nbsp;&nbsp;<a href="+(String.format("%1$03d", (ipostnum-7883))+".html")+">&nbsp;下一页&nbsp;&gt;&nbsp;</a>&nbsp;&nbsp;</strong></p></body></html>";

            FilePlus.writeText("outputs", outhtmlfilename, header + contentnode.toHtml() + ender);
//            System.out.println(contentnode.toHtml());

        } catch (ParserException ex) {
            java.util.logging.Logger.getLogger(FetcherUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger(FetcherUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FetcherUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.liuxuan.utils.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 *
 * @author Moses
 */
public class HC {

    public static Logger logger = Logger.getLogger(HC.class.getName());

    public static String geturl(String url) throws IllegalStateException, IOException {
//        Logger logger = Logger.getLogger(HC.class);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        logger.debug("executing request " + httpget.getURI());

        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpget, responseHandler);
//        logger.info(responseBody);

//        logger.info("----------------------------------------");

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
        return responseBody;
    }

    public static File download(String url, String path, String name) {
        if (name == null || name.equals("")) {
            int position = url.lastIndexOf("/") > 0 ? url.lastIndexOf("/") : url.lastIndexOf("\\") > 0 ? url.lastIndexOf("\\") : 0;
            name = url.substring(position);
        }
        File fp = new File(path);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        File storeFile = new File(path+"\\"+name);
        
        HttpClient client = new DefaultHttpClient();
        //设定目标站点  web的默认端口 80可以不写的 当然如果是其它端口就要标明
        HttpGet get = new HttpGet(url);
        try {
//            client.execute(get);
            HttpResponse response = client.execute(get);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                //请求成功
                //取得请求内容
                HttpEntity entity = response.getEntity();

                //显示内容
                if (entity != null) {
                    //这里可以得到文件的类型 如image/jpg /zip /tiff 等等 但是发现并不是十分有效，有时明明后缀是.rar但是取到的是null，这点特别说明
                    logger.debug(entity.getContentType());
                    //可以判断是否是文件数据流
                    logger.debug(entity.isStreaming());
                    //设置本地保存的文件
                    
                    FileOutputStream output = new FileOutputStream(storeFile);
                    //得到网络资源并写入文件
                    InputStream input = entity.getContent();
                    byte b[] = new byte[1024];
                    int j = 0;
                    while ((j = input.read(b)) != -1) {
                        output.write(b, 0, j);
                    }
                    output.flush();
                    output.close();
                }
                if (entity != null) {
                    entity.consumeContent();
                }
            }
//        client.executeMethod(get);
//        String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//        File storeFile = new File("C:/"+name + ".jpg");
//        FileOutputStream fileOutputStream = new FileOutputStream(storeFile);
//        FileOutputStream output = fileOutputStream;
//        output.write(get.getResponseBody());
//        output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeFile;
    }

    public static void main(String[] args) {
        try {
            geturl("http://192.168.1.168");
        } catch (IllegalStateException ex) {
//            logger.fatal(null,ex);
            java.util.logging.Logger.getLogger(HC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

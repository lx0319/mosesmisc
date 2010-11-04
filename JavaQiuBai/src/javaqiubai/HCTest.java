/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaqiubai;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Moses
 */
public class HCTest {

    public static void main(String[] args) {
        try {
            geturl2("http://192.168.1.168/");
        } catch (IllegalStateException ex) {
            Logger.getLogger(HCTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HCTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void geturl(String url) throws IllegalStateException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
            entity.consumeContent();
        }
        if (entity != null) {

            InputStream instream = entity.getContent();
            InputStreamReader isr = new InputStreamReader(instream);
            int l;
            byte[] tmp = new byte[2048];
            while ((l = instream.read(tmp)) != -1) {
                
            }

        }
    }
    public static void geturl2(String url) throws IllegalStateException, IOException {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet(url);

        System.out.println("executing request " + httpget.getURI());

        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpget, responseHandler);
        System.out.println(responseBody);

        System.out.println("----------------------------------------");

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
}

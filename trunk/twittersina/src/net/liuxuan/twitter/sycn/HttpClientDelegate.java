package net.liuxuan.twitter.sycn;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpClientDelegate {

	private HttpResponse response;
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private CookieStore cookieStore = null;
	// 设置COOKIES
	private HttpContext localContext = new BasicHttpContext();
	
	public String getTextG(HttpGet httpget) {
		try {
			response = httpclient.execute(httpget, localContext);
			cookieStore = httpclient.getCookieStore();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		}

		String redirect = getRedirectLocation();
		String responseBody = "";
		if (redirect == null) {
			// 无跳转
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				
				responseBody = responseHandler.handleResponse(response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return responseBody;
		} else {
			// 有跳转，执行跳转。
			httpget.abort();
			return getTextG(redirect);
		}
	}

	public String getTextG(String url) {
		HttpGet httpget = new HttpGet(url);
		return getTextG(httpget);
	}

	private String getRedirectLocation() {
		Header locationHeader = response.getFirstHeader("Location");
		if (locationHeader == null) {
			return null;
		}
		return locationHeader.getValue();
	}

	public String getTextP(HttpPost httppost) {
		try {
			response = httpclient.execute(httppost, localContext);
			cookieStore = httpclient.getCookieStore();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httppost.abort();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		}

		String redirect = getRedirectLocation();
		String responseBody = "";
		if (redirect == null) {
			// 无跳转
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				responseBody = responseHandler.handleResponse(response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return responseBody;
		} else {
			// 有跳转，执行跳转,Get新页面。
			httppost.abort();
			return getTextG(redirect);
		}
	}

	public void shutdownHC(){
		httpclient.getConnectionManager().shutdown();
	}
	
	public void initHC(){
		httpclient = new DefaultHttpClient();
	}
	
	public void printAllHeaders(HttpResponse response){
		 
		System.out.println("-----------返回的HTTP头信息------------------------");
		Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i]);
 
		}
		System.out.println("-------------返回的HTTP头信息----------------");	
	}

	
	
	public String getTextP(String url) {
		HttpPost httppost = new HttpPost(url);
		return getTextP(httppost);

	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public DefaultHttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(DefaultHttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public HttpContext getLocalContext() {
		return localContext;
	}

	public void setLocalContext(HttpContext localContext) {
		this.localContext = localContext;
	}

}

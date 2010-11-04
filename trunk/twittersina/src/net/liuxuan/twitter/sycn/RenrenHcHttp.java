package net.liuxuan.twitter.sycn;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class RenrenHcHttp {

	private HttpClientDelegate hcd = new HttpClientDelegate();

	// 登录字符串
	private static String username = "lx.0319@gmail.com";
	private static String password = "mosesmoses";
	private static String redirectUrl = "http://www.renren.com/Home.do";

	// 登录地址
	private static String renRenLoginURL = "http://www.renren.com/PLogin.do";
	private static String renRenPublishURL = "http://status.renren.com/doing/update.do";
	
	


	public boolean login(String username, String password) {
		init();
		HttpPost httpost = new HttpPost(renRenLoginURL);
		// All the parameters post to the web site

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("email", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("autoLogin", "true"));
		nvps.add(new BasicNameValuePair("origURL", redirectUrl));
		nvps.add(new BasicNameValuePair("domain", "renren.com"));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String strResponse = getHcd().getTextP(httpost);
		if(strResponse==null){
			return false;
		}
		return true;
	}
	
	public String publish(String content) {
		init();
		HttpPost httpost = new HttpPost(renRenPublishURL);
		// All the parameters post to the web site

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("c", content));
		nvps.add(new BasicNameValuePair("raw", content));
		nvps.add(new BasicNameValuePair("isAtHome", "1"));
		nvps.add(new BasicNameValuePair("publisher_form_ticket", "186642753"));
		nvps.add(new BasicNameValuePair("content", "186642753"));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String strResponse = getHcd().getTextP(httpost);

		return strResponse;
	}

	private void init() {
		if(getHcd()==null){
			setHcd(new HttpClientDelegate());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RenrenHcHttp rr = new RenrenHcHttp();
		rr.login(username,password);
		String str_res = rr.publish("测试一下HttpClient");
		System.out.println(str_res);
		rr.getHcd().shutdownHC();
		rr.setHcd(null);
	}

	public void setHcd(HttpClientDelegate hcd) {
		this.hcd = hcd;
	}

	public HttpClientDelegate getHcd() {
		return hcd;
	}

}

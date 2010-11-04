package net.liuxuan.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import net.liuxuan.utils.StringPlus;

/**
 * @author Moses
 * 
 */
public class ContentFetcher {
	String encoding = "utf-8";// 默认编码UTF-8
	HttpURLConnection httpURLConnection = null;
	OutputStream httpOutputStream = null;
	String referer = "";
	String host = "";
	String existsCookie = "";
	String cookieStr = null;
	String callbackUrl = null;
	String url = "";
	String requestmethod = "POST";

	String responseStr;

	/**
	 * @param _postData
	 * @param _url
	 * @return
	 */
	public String postContent(String _postData, String _url) {

		String postData = _postData;
		try {
			byte[] postByte = postData.getBytes(encoding);

			URLConnection con = new URL(_url).openConnection();
			if (con != null) {
				httpURLConnection = (HttpURLConnection) con;
			} else {
				return null;
			}

			referer = "http://www.renren.com/SysHome.do";
			host = "passport.renren.com";

			initializeConnection();
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("ContentLength", String
					.valueOf(postByte.length));
			httpOutputStream = httpURLConnection.getOutputStream();
			httpOutputStream.write(postByte);

			BufferedReader httpBufferedReader = new BufferedReader(
					new InputStreamReader(httpURLConnection.getInputStream(),
							encoding));
			responseStr = StringPlus.BufferedReaderToString(httpBufferedReader);
			System.out.println(responseStr);
			String[] tmp = responseStr.split("\\\"");
			if (tmp == null || tmp.length != 3) {
				return null;
			}
			callbackUrl = tmp[1];

			for (int i = 1; httpURLConnection.getHeaderFieldKey(i) != null; i++) {
				if (httpURLConnection.getHeaderFieldKey(i).equals("set-cookie")) {
					cookieStr = httpURLConnection.getHeaderField(i) + ",";
					break;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpOutputStream.close();
				httpURLConnection.disconnect();
			} catch (Exception e) {
				return null;
			}
		}

		return responseStr;

	}

	/**
	 * @param _postData
	 * @param _url
	 * @return
	 */
	public String getContent(String _postData, String _url) {
		requestmethod = "GET";
		String postData = _postData;
		try {
			byte[] postByte = postData.getBytes(encoding);

			URLConnection con = new URL(_url).openConnection();
			if (con != null) {
				httpURLConnection = (HttpURLConnection) con;
			} else {
				return null;
			}

			referer = "";
			host = "";

			initializeConnection();

			BufferedReader httpBufferedReader = new BufferedReader(
					new InputStreamReader(httpURLConnection.getInputStream(),
							encoding));
			responseStr = StringPlus.BufferedReaderToString(httpBufferedReader);
			// responseStr = new String(responseStr.getBytes("gb2312"));
			System.out.println(responseStr);
			String[] tmp = responseStr.split("\\\"");
			if (tmp == null || tmp.length != 3) {
				return null;
			}
			callbackUrl = tmp[1];

			for (int i = 1; httpURLConnection.getHeaderFieldKey(i) != null; i++) {
				if (httpURLConnection.getHeaderFieldKey(i).equals("set-cookie")) {
					cookieStr = httpURLConnection.getHeaderField(i) + ",";
					break;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpOutputStream.close();
				httpURLConnection.disconnect();
			} catch (Exception e) {
				return null;
			}
		}

		return responseStr;

	}

	/**
	 * 简单的get方法
	 * 
	 * @param _url
	 *            网址
	 * @param charset
	 *            编码
	 * @return
	 */
	public String getContent2(String _url, String charset) {
		url = _url;
		try {
			httpURLConnection = (HttpURLConnection) new URL(url)
					.openConnection();
			BufferedReader httpBufferedReader = new BufferedReader(
					new InputStreamReader(httpURLConnection.getInputStream(),
							charset));
			responseStr = StringPlus.BufferedReaderToString(httpBufferedReader);
			System.out.println(responseStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;

	}

	/**
	 * 初始化Connection
	 * 
	 * @throws ProtocolException
	 * @throws IOException
	 */
	private void initializeConnection() throws ProtocolException, IOException {
		// httpURLConnection.setRequestMethod("post");
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection
				.setRequestProperty(
						"Accept",
						"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");

		httpURLConnection
				.setRequestProperty(
						"UserAgent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)");
		httpURLConnection.setRequestProperty("ContentType",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Referer", referer);
		httpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
		httpURLConnection
				.setRequestProperty("Accept-Encoding", "gzip, deflate");
		if (host != null && !host.equals("")) {
			httpURLConnection.setRequestProperty("Host", host);
		}
		httpURLConnection.setRequestProperty("Pragma", "no-cache");
		httpURLConnection.setRequestProperty("Cookie", existsCookie);
		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.setUseCaches(false);

	}

	public static void main(String[] args) {
		ContentFetcher cf = new ContentFetcher();
		cf.getContent("", "http://www.jinantuan.com/");
		cf.getContent2("http://www.jinantuan.com/", "utf-8");
	}

}

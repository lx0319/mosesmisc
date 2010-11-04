package net.liuxuan.easyqb.old;

import java.net.HttpURLConnection;

import javax.servlet.http.HttpServletResponse;

public interface ISwaper {
	void swap(HttpURLConnection swaperConn, HttpServletResponse resp)
			throws Exception;
}

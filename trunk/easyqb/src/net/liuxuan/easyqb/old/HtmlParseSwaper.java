package net.liuxuan.easyqb.old;

import java.net.HttpURLConnection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import net.liuxuan.util.CommonUtils;

public class HtmlParseSwaper extends DirectlySwaper {

	private static final Logger log = Logger.getLogger(HtmlParseSwaper.class
			.getName());

	public void swap(HttpURLConnection swaperConn, HttpServletResponse resp)
			throws Exception {

		if (!HtmlHandlerHelper.isHtmlContentType(swaperConn)) {
			super.swap(swaperConn, resp);
			return;
		}

		byte[] htmlBuff = HtmlHandlerHelper.getHttpBodyBuffer(swaperConn);

		boolean gzipped = HtmlHandlerHelper.guessIsGZipCompressed(htmlBuff);

		if (gzipped)
			htmlBuff = HtmlHandlerHelper.ungzip(htmlBuff);

		String charset = HtmlHandlerHelper.guessCharset(htmlBuff);

		htmlBuff = new HttpLikeUrlSwapizeHandler(swaperConn.getURL()).handling(
				new String(htmlBuff, charset), charset).getBytes(charset);

		if (gzipped)
			htmlBuff = HtmlHandlerHelper.gzip(htmlBuff);

		resp.setContentLength(htmlBuff.length);

		CommonUtils.writeBufferToStream(resp.getOutputStream(), htmlBuff);

		log.info(String.format("Swap '%s' well done by '%s'.", swaperConn
				.getURL(), this.getClass().getSimpleName()));
	}
}

package net.liuxuan;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.liuxuan.database.TuanService;
import net.liuxuan.database.bean.TuanBean;

import com.google.appengine.repackaged.org.apache.commons.logging.Log;
import com.google.appengine.repackaged.org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class GaetuanServlet extends HttpServlet {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(GaetuanServlet.class);

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doGet(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		Controler.writeTB();
		List<TuanBean> results = TuanService.searchUseTB();
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			TuanBean tb = (TuanBean) iterator.next();
			resp.getWriter().println("<br/>");
			Date d = new Date();
			d.setTime(tb.getFinishDate());
			resp.getWriter().print(tb.getSitename()+","+new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(d));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("doGet(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}
}

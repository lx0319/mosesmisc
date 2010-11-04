/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.liuxuan.tuan.site.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.liuxuan.tuan.site.bean.ProcessNodeBean;
import net.liuxuan.tuan.site.bean.TuanSiteBean;
import net.liuxuan.tuan.site.services.TuanSiteUtils;

public class TuanSiteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4014805944203129618L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sitename = req.getParameter("sitename");
		String siteurl = req.getParameter("siteurl");
		ProcessNodeBean productname = parsenodebean("productname",req);
		
		TuanSiteBean tsb = new TuanSiteBean();
		tsb.setSitename(sitename);
		tsb.setSiteurl(siteurl);
		TuanSiteUtils.insertNew(tsb);
		resp.sendRedirect("/tuansite.jsp");
	}

	private ProcessNodeBean parsenodebean(String beanname,
			HttpServletRequest req) {
		String _processtype = req.getParameter(beanname + "_processtype");
		String _xpath = req.getParameter(beanname + "_xpath");
		String _regex = req.getParameter(beanname + "_regex");
		String _multipler = req.getParameter(beanname + "_multipler");

		if (_processtype == null || _xpath == null || _regex == null
				|| _multipler == null) {
			return null;
		}

		ProcessNodeBean pnb = new ProcessNodeBean(
				Integer.parseInt(_processtype), _xpath, _regex,
				Integer.parseInt(_multipler));

		return pnb;
	}

}

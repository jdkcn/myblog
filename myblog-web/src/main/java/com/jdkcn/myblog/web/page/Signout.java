package com.jdkcn.myblog.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id$
 */
public class Signout {

	@Inject
	private HttpServletRequest request;
	
	@Inject
	HttpSession session;
	
	@Get
	@Post
	public String logout() {
		session.invalidate();
		String refer = request.getHeader("Referer");
		if (StringUtils.isNotBlank(refer)) {
			return refer;
		}
		return request.getContextPath() + "/";
	}
}

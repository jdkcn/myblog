package com.jdkcn.myblog.web.page;

import com.google.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id$
 */
public abstract class AbstractPage {

	@Inject
	HttpServletRequest request;
	
	public String getContextPath() {
		return request.getContextPath();
	}
}

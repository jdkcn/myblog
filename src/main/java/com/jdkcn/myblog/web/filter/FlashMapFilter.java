package com.jdkcn.myblog.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdkcn.myblog.util.FlashMap;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id$
 */
public class FlashMapFilter extends OncePerRequestFilter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
	    if (session != null) {
	      @SuppressWarnings("unchecked")
		Map<String, ?> flash = (Map<String, ?>) session.getAttribute(FlashMap.FLASH_MAP_ATTRIBUTE);
	      if (flash != null) {
	        for (Map.Entry<String, ?> entry : flash.entrySet()) {
	          Object currentValue = request.getAttribute(entry.getKey());
	          if (currentValue == null) {
	            request.setAttribute(entry.getKey(), entry.getValue());
	          }
	        }
	        session.removeAttribute(FlashMap.FLASH_MAP_ATTRIBUTE);
	      }
	    }
	    filterChain.doFilter(request, response);

	}

}

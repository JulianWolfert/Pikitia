package de.htw.fb4.bilderplattform.spring.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * 
 * @author Wojciech Konitzer
 * 
 * 22.12.2012
 *
 */
public class ServletContextProvider implements ServletContextAware {
	private static ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletCtx) {
		servletContext = servletCtx;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
}

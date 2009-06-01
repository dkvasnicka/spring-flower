/*
FLOWER framework for Spring -- expose your Spring beans through HTTP -- http://code.google.com/p/spring-flower/
Copyright (C) 2009 Daniel Kvasnička jr.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

package net.danielkvasnicka.flower;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.danielkvasnicka.flower.beans.WebAccessibleBean;
import net.danielkvasnicka.flower.core.Constants;
import net.danielkvasnicka.flower.core.UrlMappingHolder;
import net.danielkvasnicka.flower.core.WebAccessibleBeanMetadata;
import net.danielkvasnicka.flower.core.api.WebAccessibleBeanResolver;
import net.danielkvasnicka.flower.exception.FlowerRuntimeException;
import net.danielkvasnicka.flower.response.api.Response;

/**
 * This is the heart of Flower.
 * This servlet receives the URL, uses the resolver to get the bean metadata, loads
 * the bean, runs the appropriate method and then sends the response.
 * 
 * @author Daniel Kvasnicka jr.
 */
public class FlowerDispatcherServlet implements Servlet {

	/**
	 * Spring app ctx.
	 */
	private ApplicationContext appCtx;
	
	/**
	 * Servlet config reference
	 */
	private ServletConfig servletConfig;
	
	/**
	 * The resolver used to get the bean metadata
	 */
	@Autowired
	private WebAccessibleBeanResolver webAccessibleBeanResolver;
	
	/**
	 * @see Servlet#destroy()
	 */
	@Override
	public void destroy() {

	}
	
	/**
	 * @see Servlet#service(ServletRequest, ServletResponse)
	 */
	@Override
	public void service(final ServletRequest request, final ServletResponse response)
			throws ServletException, IOException {
		
		// get the URL mapping bean
		final UrlMappingHolder urlMappingHolder = (UrlMappingHolder) this.appCtx.getBean(Constants.URL_MAPPING_HOLDER_BEAN_NAME);
		
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		// get the mapping
		final Map<String, String> urlMapping = urlMappingHolder.getUrlMapping();
		
		// get the current url and use it together with the mapping to retreive the bean metadata
		final String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());		
		final WebAccessibleBeanMetadata beanMetadata = this.webAccessibleBeanResolver.resolve(urlMapping, url);
		
		// no bean has been found for this URL
		if (beanMetadata == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "No web accessible bean has been found for this URL.");
			return;
		}
		
		// we get the bean and inject the params
		final WebAccessibleBean bean = (WebAccessibleBean) this.appCtx.getBean(beanMetadata.name);
		bean.setParameters(beanMetadata.parameters);
		
		// we add GET/POST params, which OVERWRITES the REST params
		for (final Object key : httpServletRequest.getParameterMap().keySet()) {
			bean.getParameters().put(key.toString(), httpServletRequest.getParameter(key.toString()));
		}
		
		// we run the desired method and send the response
		try {
			final Method method = bean.getClass().getMethod(beanMetadata.methodName);
			final Response beanResponse = (Response) method.invoke(bean);
			beanResponse.send(httpServletRequest, httpServletResponse);
			
		} catch (SecurityException e) {
			throw new FlowerRuntimeException(e);
		} catch (NoSuchMethodException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, 
				"Method '" + beanMetadata.methodName + "' not found on " + bean.getClass().getName());
			return;
		} catch (IllegalArgumentException e) {
			throw new FlowerRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new FlowerRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new FlowerRuntimeException(e);
		}
	}

	/**
	 * @param webAccessibleBeanResolver the webAccessibleBeanResolver to set
	 */
	public void setWebAccessibleBeanResolver(
			WebAccessibleBeanResolver webAccessibleBeanResolver) {
		this.webAccessibleBeanResolver = webAccessibleBeanResolver;
	}

	@Override
	public ServletConfig getServletConfig() {
		return this.servletConfig;
	}

	@Override
	public String getServletInfo() {		
		return "Flower dispatcher servlet";
	}

	/**
	 * Setup Spring managed resources on me
	 */
	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		this.servletConfig = servletConfig;
		this.appCtx = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
		this.appCtx.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
	}
}

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

package net.danielkvasnicka.flower.response;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielkvasnicka.flower.exception.FlowerRuntimeException;
import net.danielkvasnicka.flower.response.api.Response;

/**
 * A forwarding response, that internally forwards the request
 * and optionally attaches some data to the request.
 * 
 * @author Daniel Kvasnicka jr.
 */
public class ForwardingResponse implements Response {

	/**
	 * Where to forward
	 */
	private String targetResource;
	
	/**
	 * What to attach
	 */
	private Map<String, Object> requestScopedAttributes;
		
	public ForwardingResponse(final String targetResource, final Map<String, Object> requestScopedAttributes) {
		this.targetResource = targetResource;
		this.requestScopedAttributes = requestScopedAttributes;
	}
	
	@SuppressWarnings("unchecked")
	public ForwardingResponse(final String targetResource) {
		this.targetResource = targetResource;
		this.requestScopedAttributes = Collections.EMPTY_MAP;
	}
	
	/**
	 * @see net.danielkvasnicka.flower.response.api.Response#send(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void send(final HttpServletRequest request, final HttpServletResponse response) {
		
		// attach the desired objects
		for (final String key : this.requestScopedAttributes.keySet()) {
			request.setAttribute(key, this.requestScopedAttributes.get(key));
		}
		
		// fire!
		try {
			request.getRequestDispatcher(this.targetResource).forward(request, response);
		} catch (ServletException e) {
			throw new FlowerRuntimeException(e);
		} catch (IOException e) {
			throw new FlowerRuntimeException(e);
		}
	}

}

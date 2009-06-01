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
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.danielkvasnicka.flower.exception.FlowerRuntimeException;
import net.danielkvasnicka.flower.response.api.Response;

/**
 * A primitive streaming response for sending any kind of data to the client.
 * (XML, JSON, whatever)
 * 
 * @author Daniel Kvasnicka jr.
 */
public class StreamingResponse implements Response {

	/**
	 * Content type of the data
	 */
	private String contentType;
	
	/**
	 * The data
	 */
	private Object content;
	
	public StreamingResponse(final String contentType, final Object content) {
		this.contentType = contentType;
		this.content = content;
	}
	
	/**
	 * @see net.danielkvasnicka.flower.response.api.Response#send()
	 */
	@Override
	public void send(final HttpServletRequest request, final HttpServletResponse response) {
		
		response.setContentType(this.contentType);
		try {
			final PrintWriter out = response.getWriter();
			out.append(this.content.toString());
			out.close();
		} catch (IOException e) {
			throw new FlowerRuntimeException(e);
		}
	}

}

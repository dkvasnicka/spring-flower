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

package net.danielkvasnicka.flower.core;

import java.util.Map;

/**
 * This is the URL mapping holder DTO.
 * 
 * @author Daniel Kvasnicka jr.
 */
public class UrlMappingHolder {

	/**
	 * URL mapping, e.g. <"/helloworld/({f_method}[a-zA-Z]+)", "beanName">
	 */
	private Map<String, String> urlMapping;

	/**
	 * @return the urlMapping
	 */
	public Map<String, String> getUrlMapping() {
		return this.urlMapping;
	}

	/**
	 * @param urlMapping the urlMapping to set
	 */
	public void setUrlMapping(Map<String, String> urlMapping) {
		this.urlMapping = urlMapping;
	}
}

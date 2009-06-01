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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import net.danielkvasnicka.flower.core.api.WebAccessibleBeanResolver;
import net.danielkvasnicka.flower.utils.NamedPattern;

/**
 * Default web accessible bean resolver that uses regular expressions 
 * with named groups to extract params from a RESTful URL.
 * 
 * @author Daniel Kvasnicka jr.
 * @see WebAccessibleBeanResolver
 */
public class DefaultWebAccessibleBeanResolver implements
		WebAccessibleBeanResolver {
	
	/**
	 * @see net.danielkvasnicka.flower.core.api.WebAccessibleBeanResolver#resolve(java.util.Map, java.lang.String)
	 */
	@Override
	public WebAccessibleBeanMetadata resolve(final Map<String, String> urlMapping, final String url) {

		for (final String pattern : urlMapping.keySet()) {
			final NamedPattern p = new NamedPattern(pattern, 0);
		    final Matcher m = p.matcher(url);
		    
		    if (m.matches()) {			    				    	
		    	final Map<String, String> params = this.getParametersMap(m, p);
				return new WebAccessibleBeanMetadata(urlMapping.get(pattern), params, params.get(Constants.EXPOSED_METHOD_REST_PARAM_NAME));
			}
		}
		
		return null;
	}

	/**
	 * Extracts named groups and their values.
	 * 
	 * @param matcher
	 * @param pattern
	 * @return named groups and their values
	 */
	private Map<String, String> getParametersMap(final Matcher matcher, final NamedPattern pattern) {
		final List<String> groups = pattern.getGroups();
		final Map<String, String> parameters = new HashMap<String, String>();
		
		for (int i = 0; i < pattern.getGroups().size(); i++) {
			parameters.put(groups.get(i), pattern.group(matcher, groups.get(i)));
		}
		
		return parameters;
	}
}

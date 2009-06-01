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

/**
 * Consts used throughout the system.
 * 
 * @author Daniel Kvasnicka jr.
 */
public final class Constants {

	/** so that we can be both final and non-instantiable */
	private Constants() {}

	/**
	 * Name of the bean that holds the URL mapping
	 */
	public final static String URL_MAPPING_HOLDER_BEAN_NAME = "flowerUrlMappingHolder";
	
	/**
	 * Name of the property used to hold the mapping
	 */
	public final static String URL_MAPPING_HOLDER_PROPERTY = "urlMapping";
	
	/**
	 * Prefix used to differentiate REST params reserved to the Flower core.
	 * e.g. f_method
	 */
	public final static String RESERVED_REST_PARAMS_PREFIX = "f_";
	
	/**
	 * Name of the method that should be called on the web accesible bean.
	 */
	public final static String EXPOSED_METHOD_REST_PARAM_NAME = RESERVED_REST_PARAMS_PREFIX + "method";
	
}

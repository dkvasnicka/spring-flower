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

package net.danielkvasnicka.flower.exception;

/**
 * Thrown when there is a problem in Flower configuration
 * 
 * @author Daniel Kvasnicka jr.
 */
public class FlowerConfigException extends RuntimeException {

	public FlowerConfigException(final String message) {
		super(message);
	}

	private static final long serialVersionUID = -738246129074073024L;
}

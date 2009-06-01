/**
 * 
 */
package net.danielkvasnicka.flower.test.beans;

import java.util.HashMap;

import net.danielkvasnicka.flower.beans.AbstractWebAccessibleBean;
import net.danielkvasnicka.flower.response.ForwardingResponse;
import net.danielkvasnicka.flower.response.api.Response;

/**
 * @author Daniel Kvasnicka jr.
 *
 */
public class CopyOfTestWebAccessibleBean extends AbstractWebAccessibleBean {

	public Response hi() {
		
		return new ForwardingResponse("/WEB-INF/jsp/hi.jsp", new HashMap() {{ put("x", 6); }});
	}

}

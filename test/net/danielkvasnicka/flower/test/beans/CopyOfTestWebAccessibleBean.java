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

	/**
	 * This shows how to use JSP as a View. The map passed in
	 * the second parameter contains values that are later set as request attributes 
	 * before the forward to the JSP is executed.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	public Response hi() {
		
		return new ForwardingResponse("/WEB-INF/jsp/hi.jsp", new HashMap() {{ put("x", 6); }});
	}

}

/**
 * 
 */
package net.danielkvasnicka.flower.test.beans;

import net.danielkvasnicka.flower.beans.AbstractWebAccessibleBean;
import net.danielkvasnicka.flower.response.StreamingResponse;
import net.danielkvasnicka.flower.response.api.Response;


/**
 * @author Daniel Kvasnicka jr.
 *
 */
public class TestWebAccessibleBean extends AbstractWebAccessibleBean {

	public Response index() {
		
		return new StreamingResponse("text/plain", "x = " + this.parameters.get("x"));
	}

	public Response hello() {
		
		return new StreamingResponse("text/xml", "<a f=\"" + this.parameters.get("y") + "\" />");
	}
}

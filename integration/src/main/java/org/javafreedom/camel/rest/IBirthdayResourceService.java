package org.javafreedom.camel.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/birthdays")
public interface IBirthdayResourceService {

	@GET
	@Path("{id}")
	public Response getBirthday(@PathParam("id") String id);

}

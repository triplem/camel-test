package org.javafreedom.camel.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javafreedom.camel.model.Birthday;

/**
 *
 */
@Path("/birthdays")
public interface IBirthdayResourceService {

	@GET
	@Path("{id}")
	public Birthday getBirthday(@PathParam("id") String id);

}

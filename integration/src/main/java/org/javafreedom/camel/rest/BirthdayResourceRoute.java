package org.javafreedom.camel.rest;

import org.apache.camel.builder.RouteBuilder;

/**
 *
 */
public class BirthdayResourceRoute extends RouteBuilder {

	public void configure() throws Exception {
		from("direct:start")
				.to("velocity:HTMLBody.vm");
	}
}

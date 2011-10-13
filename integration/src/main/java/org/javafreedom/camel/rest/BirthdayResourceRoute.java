package org.javafreedom.camel.rest;

import java.awt.*;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.language.bean.BeanLanguage;

/**
 *
 */
public class BirthdayResourceRoute extends RouteBuilder {

	public void configure() throws Exception {
		from("direct:start")
				.setHeader(Exchange.FILE_NAME, BeanLanguage.bean(FilenameGenerator.class, "generateFilename"))
				.to("velocity:HTMLBody.vm")
				.to("file://target/mail");

		from("file://target/mail")
				.setHeader("subject", constant("Birthday retrieved"))
				.to("smtp://someone@localhost?password=secret&to=birthday@mycompany.com");
	}
}

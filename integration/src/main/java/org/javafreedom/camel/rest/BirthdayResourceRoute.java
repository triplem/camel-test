package org.javafreedom.camel.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.language.bean.BeanLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class BirthdayResourceRoute extends RouteBuilder {

	private static Logger log = LoggerFactory.getLogger(BirthdayResourceService.class);

	public void configure() throws Exception {
		CamelContext context = this.getContext();

		log.info("Name: " + context.getName());
		log.info("BirthdayService: " + context.getRegistry().lookup("birthdayService"));


		from("cxfrs://bean://rsServer")
				.beanRef("birthdayService", "findById")
				.wireTap("direct:tap")
				.to("log:org.javafreedom.camel");

		from("direct:start")
				.beanRef("birthdayService", "findById")
				.wireTap("direct:tap")
				.to("log:org.javafreedom.camel");

		from("direct:tap")
				.to("log:org.javafreedom.camel")
				.setHeader(Exchange.FILE_NAME, BeanLanguage.bean(FilenameGenerator.class, "generateFilename"))
				.to("velocity:HTMLBody.vm")
				.to("file://target/mail");

		from("file:target/mail")
				.setHeader("subject", constant("Birthday retrieved"))
				.to("log:org.javafreedom.camel");
//				.to("smtp://someone@localhost?password=secret&to=birthday@mycompany.com");
	}
}

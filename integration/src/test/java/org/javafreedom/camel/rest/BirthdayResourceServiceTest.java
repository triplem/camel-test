package org.javafreedom.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Date;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.jaxrs.client.ResponseReader;
import org.javafreedom.camel.model.Birthday;
import org.javafreedom.camel.service.BirthdayService;
import org.javafreedom.camel.service.IBirthdayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
public class BirthdayResourceServiceTest extends ContextTestSupport {


	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new BirthdayResourceRoute();
	}

	@Test
	public void testGetBirthday() throws Exception {
		Mailbox box = Mailbox.get("birthday@mycompany.com");
		assertEquals(0, box.size());

		Birthday bday = new Birthday();
		bday.setDate(new Date());
		bday.setId("4711");
		bday.setName("TEST4711");

		assertNotNull(context);

		ProducerTemplate template = context.createProducerTemplate();

		Object out = template.sendBody("direct:start", ExchangePattern.InOut, bday);

		assertNotNull(out);

		String body = context.getTypeConverter().convertTo(String.class, out);

		assertTrue(body.startsWith("<html>"));
		assertTrue(body.contains("4711"));

		File file = new File("target/mail", "birthday-4711.html");

		assertTrue(file.exists());

		Thread.sleep(10 * 1000);

		assertEquals(1, box.size());
		assertEquals("Birthday retrieved", box.get(0).getSubject());
	}
}

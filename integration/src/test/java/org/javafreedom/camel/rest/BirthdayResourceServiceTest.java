package org.javafreedom.camel.rest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.javafreedom.camel.model.Birthday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/TEST-applicationContext-integration.xml" })
public class BirthdayResourceServiceTest {

	@Autowired
	private CamelContext camel;

	@DirtiesContext
	@Test
	public void testGetBirthday() throws Exception {
		Mailbox box = Mailbox.get("birthday@mycompany.com");
		assertEquals(0, box.size());

		Birthday bday = new Birthday();
		bday.setDate(new Date());
		bday.setId("4711");
		bday.setName("TEST4711");

		assertNotNull(camel);

		assertNotNull(camel.getRegistry().lookup("birthdayService"));

		ProducerTemplate template = camel.createProducerTemplate();

		Object out = template.sendBody("direct:start", ExchangePattern.InOut, "4711");

		assertNotNull(out);

		String body = camel.getTypeConverter().convertTo(String.class, out);

		assertTrue(body.startsWith("<html>"));
		assertTrue(body.contains("4711"));

		File file = new File("target/mail", "birthday-4711.html");

		assertTrue(file.exists());

		Thread.sleep(10 * 1000);

		assertEquals(1, box.size());
		assertEquals("Birthday retrieved", box.get(0).getSubject());
	}
}

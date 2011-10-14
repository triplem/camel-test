package org.javafreedom.camel.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import java.io.File;

import org.apache.cxf.jaxrs.client.ResponseReader;
import org.javafreedom.camel.model.Birthday;
import org.junit.Ignore;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/TEST-applicationContext-integration.xml" })
public class BirthdayResourceServiceIT {

	@Autowired
	@Qualifier("birthdayClient")
	private IBirthdayResourceService proxy;

	@Test
	@Ignore
	public void testGetBirthday() throws Exception {
		Mailbox box = Mailbox.get("birthday@mycompany.com");
		assertEquals(0, box.size());

		ResponseReader reader = new ResponseReader();
		reader.setEntityClass(Birthday.class);

		Response resp = proxy.getBirthday("4711");

		Birthday bday = (Birthday)resp.getEntity();

		assertNotNull(bday);
		assertEquals("4711", bday.getId());

		File logFile = new File("target", "bday-4711.txt");
		assertTrue(logFile.exists());

		File htmlFile = new File("target", "bday-4711.html");
		assertTrue(htmlFile.exists());

        // give the event driven consumer time to react
		Thread.sleep(10 * 1000);

		assertEquals(1, box.size());
		assertEquals("Birthday retrieved", box.get(0).getSubject());

	}

}

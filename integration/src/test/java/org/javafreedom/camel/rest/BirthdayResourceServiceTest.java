package org.javafreedom.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import java.io.File;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/TEST-applicationContext-integration.xml" })
public class BirthdayResourceServiceTest {

	@Autowired
	private IBirthdayService birthdayService;

	@Test
	public void testGetBirthday() throws Exception {
		ResponseReader reader = new ResponseReader();
		reader.setEntityClass(Birthday.class);

		BirthdayResourceService resourceService = new BirthdayResourceService(false);

		resourceService.setBirthdayService(birthdayService);

		Response resource = resourceService.getBirthday("4711");
		Birthday birthday = birthdayService.findById("4711");

		assertEquals(resource.getEntity().toString(), birthday.toString());
	}
}

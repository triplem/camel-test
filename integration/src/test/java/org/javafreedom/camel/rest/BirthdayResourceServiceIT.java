package org.javafreedom.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.ws.Response;

import org.javafreedom.camel.model.Birthday;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	public void testGetBirthday() {
		Birthday bday = proxy.getBirthday("4711");

		assertNotNull(bday);
		assertEquals("4711", bday.getId());
	}

}

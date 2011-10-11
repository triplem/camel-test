package org.javafreedom.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.xml.ws.Response;

import java.io.File;

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

		File logFile = new File("target", "bday-4711.txt");
		assertTrue(logFile.exists());

		File htmlFile = new File("target", "bday-4711.html");
		assertTrue(htmlFile.exists());
	}

}

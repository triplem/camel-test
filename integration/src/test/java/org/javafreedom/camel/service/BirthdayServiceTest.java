package org.javafreedom.camel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.javafreedom.camel.model.Birthday;
import org.junit.Test;

/**
 *
 */
public class BirthdayServiceTest {

	@Test
	public void testFindById() {
		BirthdayService service = new BirthdayService();

		Birthday birthday = service.findById("4711");
		assertNotNull(birthday);
		assertEquals("4711", birthday.getId());
	}

}

package org.javafreedom.camel.service;

import java.util.Date;

import org.javafreedom.camel.model.Birthday;

/**
 *
 */
public class BirthdayServiceMock implements IBirthdayService {

	public Birthday findById(String id) {
		Birthday bday = new Birthday();
		bday.setDate(new Date());
		bday.setId(id);
		bday.setName("TEST" + id);

		return bday;
	}
}

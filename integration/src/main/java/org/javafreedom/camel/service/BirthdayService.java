package org.javafreedom.camel.service;

import java.util.Date;

import org.javafreedom.camel.model.Birthday;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BirthdayService implements IBirthdayService {


	public Birthday findById(String id) {
		Birthday bday = new Birthday();
		bday.setDate(new Date());
		bday.setId(id);
		bday.setName("TEST" + id);

		return bday;
	}


}

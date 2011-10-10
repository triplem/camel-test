package org.javafreedom.camel.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javafreedom.camel.model.Birthday;
import org.javafreedom.camel.service.IBirthdayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("birthdayResourceService")
public class BirthdayResourceService implements IBirthdayResourceService {

	private static Logger log = LoggerFactory.getLogger(BirthdayResourceService.class);

	@Autowired
	private IBirthdayService birthdayService;

	public Birthday getBirthday(String id) {
		return this.birthdayService.findById(id);
	}

	// for testing purposes
	public IBirthdayService getBirthdayService() {
		return birthdayService;
	}

	public void setBirthdayService(IBirthdayService birthdayService) {
		this.birthdayService = birthdayService;
	}
}

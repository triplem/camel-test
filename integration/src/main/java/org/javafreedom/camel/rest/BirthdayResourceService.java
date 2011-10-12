package org.javafreedom.camel.rest;

import javax.ws.rs.core.Response;

import org.apache.camel.*;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.impl.DefaultCamelContext;

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

	private CamelContext camel;

	public BirthdayResourceService() throws Exception {
		camel = new DefaultCamelContext();

		camel.addRoutes(new BirthdayResourceRoute());

		camel.start();
	}

	public Response getBirthday(String id) {
		Birthday bday = this.birthdayService.findById(id);

		Object mailBody = camel.createProducerTemplate().sendBody("direct:start", ExchangePattern.InOptionalOut, bday);

		return Response.ok(bday).build();
	}

	// for testing purposes
	public IBirthdayService getBirthdayService() {
		return birthdayService;
	}

	public void setBirthdayService(IBirthdayService birthdayService) {
		this.birthdayService = birthdayService;
	}
}

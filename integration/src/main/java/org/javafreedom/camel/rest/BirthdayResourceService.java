package org.javafreedom.camel.rest;

import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
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

	@Autowired
	private CamelContext camel;

	public BirthdayResourceService() throws Exception {
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

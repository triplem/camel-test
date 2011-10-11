package org.javafreedom.camel.rest;

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
	private ProducerTemplate template;

	public BirthdayResourceService() throws Exception {
		camel = new DefaultCamelContext();
		// using template, and not defining any special components, see http://camel.apache.org/tutorial-example-reportincident-part2.html
		// (Reducing Code Lines)
		template = camel.createProducerTemplate();

		camel.start();
	}

	public Birthday getBirthday(String id) {
		Birthday bday = this.birthdayService.findById(id);

		sendToCamelLog(bday.toString());
		sendToCamelFile(bday.getId(), bday.toString());

		generateBodyAndStoreAsFile(bday);

		return bday;
	}

	private void sendToCamelLog(String name) {
		log.info("sendToCamelLog");
		template.sendBody("log:org.javafreedom.camel", name);
	}

	private void generateBodyAndStoreAsFile(Birthday birthday) {
		log.info("generateBodyAndStoreAsFile");
		Object response = template.sendBody("velocity:HTMLBody.vm", ExchangePattern.InOptionalOut, birthday);
		String fileName = "bday-" + birthday.getId() + ".html";
		template.sendBodyAndHeader("file://target", response, Exchange.FILE_NAME, fileName);
	}

	private void sendToCamelFile(String birthdayId, String name) {
		log.info("sendToCamelFile");
		String fileName = "bday-" + birthdayId + ".txt";
		template.sendBodyAndHeader("file://target", name, Exchange.FILE_NAME, fileName);
	}

	// for testing purposes
	public IBirthdayService getBirthdayService() {
		return birthdayService;
	}

	public void setBirthdayService(IBirthdayService birthdayService) {
		this.birthdayService = birthdayService;
	}
}

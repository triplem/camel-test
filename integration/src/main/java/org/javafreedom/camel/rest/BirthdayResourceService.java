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
	private ProducerTemplate template;

	public BirthdayResourceService() throws Exception {
		init(true);
	}

	/**
	 * This consturctor is mainly to improve testing of the Class with and/or without consumer.
	 *
	 * @param enableConsumer
	 * @throws Exception
	 */
	public BirthdayResourceService(boolean enableConsumer) throws Exception {
		init(enableConsumer);
	}

	private void init(boolean enableConsumer) throws Exception {
		camel = new DefaultCamelContext();
		// using template, and not defining any special components, see http://camel.apache.org/tutorial-example-reportincident-part2.html
		// (Reducing Code Lines)
		template = camel.createProducerTemplate();

		if (enableConsumer) {
			addMailSendConsumer();
		}

		camel.start();
	}

	public Response getBirthday(String id) {
		Birthday bday = this.birthdayService.findById(id);

		sendToCamelLog(bday.toString());
		sendToCamelFile(bday.getId(), bday.toString());

		generateBodyAndStoreAsFile(bday);

		return Response.ok(bday).build();
	}

	private void addMailSendConsumer() throws Exception {
		log.info("addMailSendConsumer");
  		// Grab the endpoint where we should consume. Option - the first poll starts after 2 seconds
        Endpoint endpoint = camel.getEndpoint("file://target/mail?consumer.initialDelay=2000");

        // create the event driven consumer
        // the Processor is the code what should happen when there is an event
        // (think it as the onMessage method)
        Consumer consumer = endpoint.createConsumer(new Processor() {
            public void process(Exchange exchange) throws Exception {
                // get the mail body as a String
                String mailBody = exchange.getIn().getBody(String.class);

                // okay now we are read to send it as an email
                log.info("Sending email...");
				sendEmail(mailBody);
				log.info("Email sent");
            }
        });

        // star the consumer, it will listen for files
        consumer.start();
	}

	private void sendToCamelLog(String name) {
		log.info("sendToCamelLog");
		template.sendBody("log:org.javafreedom.camel", name);
	}

	private void generateBodyAndStoreAsFile(Birthday birthday) {
		log.info("generateBodyAndStoreAsFile");
		Object response = template.sendBody("velocity:HTMLBody.vm", ExchangePattern.InOptionalOut, birthday);
		String fileName = "bday-" + birthday.getId() + ".html";
		template.sendBodyAndHeader("file://target/mail", response, Exchange.FILE_NAME, fileName);
	}

	private void sendToCamelFile(String birthdayId, String name) {
		log.info("sendToCamelFile");
		String fileName = "bday-" + birthdayId + ".txt";
		template.sendBodyAndHeader("file://target", name, Exchange.FILE_NAME, fileName);
	}

    private void sendEmail(String body) {
        // send the email to your mail server
        String url = "smtp://someone@localhost?password=secret&to=birthday@mycompany.com";
        template.sendBodyAndHeader(url, body, "subject", "Birthday retrieved");
    }

	// for testing purposes
	public IBirthdayService getBirthdayService() {
		return birthdayService;
	}

	public void setBirthdayService(IBirthdayService birthdayService) {
		this.birthdayService = birthdayService;
	}
}

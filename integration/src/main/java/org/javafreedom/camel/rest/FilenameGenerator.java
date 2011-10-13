package org.javafreedom.camel.rest;

import org.javafreedom.camel.model.Birthday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class FilenameGenerator {

	private static Logger log =  LoggerFactory.getLogger(FilenameGenerator.class);

	public String generateFilename(Birthday birthday) {
		log.info("generateFilename");

		return "birthday-" + birthday.getId() + ".html";
	}
}

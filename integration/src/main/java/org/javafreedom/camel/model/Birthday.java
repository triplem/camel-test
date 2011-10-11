package org.javafreedom.camel.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 */
@XmlRootElement(name = "birthday")
public class Birthday {

	private String id;

	private String name;

	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String toString() {
		return "id: " + id + " # name: " + name;
	}
}

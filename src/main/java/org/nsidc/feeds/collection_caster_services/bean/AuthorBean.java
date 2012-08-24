package org.nsidc.feeds.collection_caster_services.bean;

public class AuthorBean {
	private String name="";
	private String simpleUrl="";
	private String email="";
	
	public AuthorBean() {		
	}
	
	public AuthorBean(String name, String simpleUrl, String email) {	
		this.name = name;
		this.simpleUrl = simpleUrl;
		this.email = email;
	}
	
	public AuthorBean(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Name: ");
		s.append(name);
		s.append(", URL: ");
		s.append(simpleUrl);
		s.append(", Email: ");
		s.append(email);
		return s.toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimpleUrl() {
		return simpleUrl;
	}
	public void setSimpleUrl(String simpleUrl) {
		this.simpleUrl = simpleUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}

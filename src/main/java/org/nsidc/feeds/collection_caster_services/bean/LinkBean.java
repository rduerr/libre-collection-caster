package org.nsidc.feeds.collection_caster_services.bean;

public class LinkBean {
	private String href = "";
	private String rel = "";
	private String type = "";
	
	public LinkBean() {
		
	}
	
	public LinkBean(String href, String rel, String type) {
		this.href = href;
		this.rel = rel;
		this.type = type;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Href: ");
		s.append(href);
		s.append(", Rel: ");
		s.append(rel);
		s.append(", Type: ");
		s.append(type);
		return s.toString();
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	


}

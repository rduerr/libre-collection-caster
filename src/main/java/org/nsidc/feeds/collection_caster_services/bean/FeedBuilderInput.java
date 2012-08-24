package org.nsidc.feeds.collection_caster_services.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;



@XmlRootElement(namespace="", name="feed")
@XmlAccessorType(XmlAccessType.FIELD)
public class FeedBuilderInput {
	
	private String id= null;
	private String title = null;
	private List<AuthorBean> authors = new ArrayList<AuthorBean>();
	private String updated = null;
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Id: ");
		s.append(id);
		s.append(", Title: ");
		s.append(title);
		s.append(", Updated: ");
		s.append(updated);
		s.append(", Author: [");
		for( AuthorBean author: this.authors ) {
			s.append(", Author: ");
			s.append(author);
		}
		return s.toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<AuthorBean> getAuthors() {
		return authors;
	}
	public void setAuthors(List<AuthorBean> authors) {
		this.authors = authors;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
}

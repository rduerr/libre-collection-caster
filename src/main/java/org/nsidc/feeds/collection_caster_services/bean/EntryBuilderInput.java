package org.nsidc.feeds.collection_caster_services.bean;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(namespace = "", name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryBuilderInput {
	private String id = null;
	private String title = null;
	private String updated = null;
	private String summary = null;
	private List<LinkBean> links = new ArrayList<LinkBean>();
	private String geoRSSsouth;
	private String geoRSSnorth;
	private String geoRSSwest;
	private String geoRSSeast;
	private List<AuthorBean> authors = new ArrayList<AuthorBean>();
	private String startTime = null;
	private String endTime = null;

	public EntryBuilderInput() {

	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Id: ");
		s.append(id);
		s.append(", Title: ");
		s.append(title);
		s.append(", Updated: ");
		s.append(updated);
		s.append(", Summary: ");
		s.append(summary);
		s.append(", Author: [");
		if (this.authors != null) {
			for (AuthorBean author : this.authors) {
				s.append(", Author: ");
				s.append(author);
			}
		}
		s.append("]");
		s.append(", Links: [");
		if (this.links != null) {

			for (LinkBean link : this.links) {
				s.append(", Link: ");
				s.append(link);
			}
		}
		s.append("]");
		s.append(", South: ");
		s.append(geoRSSsouth);
		s.append(", North: ");
		s.append(geoRSSnorth);
		s.append(", West: ");
		s.append(geoRSSwest);
		s.append(", East: ");
		s.append(geoRSSeast);
		s.append(", Start Time: ");
		s.append(startTime);
		s.append(", End Time: ");
		s.append(endTime);
		return s.toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<LinkBean> getLinks() {
		return links;
	}

	public void setLinks(List<LinkBean> links) {
		this.links = links;
	}

	public String getGeoRSSsouth() {
		return geoRSSsouth;
	}

	public void setGeoRSSsouth(String geoRSSsouth) {
		this.geoRSSsouth = geoRSSsouth;
	}

	public String getGeoRSSnorth() {
		return geoRSSnorth;
	}

	public void setGeoRSSnorth(String geoRSSnorth) {
		this.geoRSSnorth = geoRSSnorth;
	}

	public String getGeoRSSwest() {
		return geoRSSwest;
	}

	public void setGeoRSSwest(String geoRSSwest) {
		this.geoRSSwest = geoRSSwest;
	}

	public String getGeoRSSeast() {
		return geoRSSeast;
	}

	public void setGeoRSSeast(String geoRSSeast) {
		this.geoRSSeast = geoRSSeast;
	}

	public List<AuthorBean> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorBean> authors) {
		this.authors = authors;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return endTime;
	}

}

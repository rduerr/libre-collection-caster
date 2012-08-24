package org.nsidc.feeds.collection_caster_services;

import java.util.Calendar;

import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.ext.geo.Box;
import org.apache.abdera.ext.geo.GeoHelper;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Person;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.LinkBean;
import org.nsidc.feeds.validator.EntryValidator;
import org.springframework.util.StringUtils;

public class EntryBuilder {

	private EntryValidator validator;

	public EntryBuilder() {
		EntryBuilderInput defaultEntry = initializeDefaultEntry();
		validator = new EntryValidator(defaultEntry);
	}

	private EntryBuilderInput initializeDefaultEntry() {
		EntryBuilderInput defaultEntry = new EntryBuilderInput();
		DateTimeFormatter isoDateFormatter = ISODateTimeFormat.dateTime();
		DateTime updated = new DateTime(Calendar.getInstance().getTime());
		defaultEntry.setUpdated(isoDateFormatter.print(updated));
		defaultEntry.setGeoRSSeast("180");
		defaultEntry.setGeoRSSnorth("90");
		defaultEntry.setGeoRSSsouth("-90");
		defaultEntry.setGeoRSSwest("-180");
		return defaultEntry;
	}

	public Entry createEntry(EntryBuilderInput inputEntry)
			throws InvalidParamsException {

		if (!validator.evaluate(inputEntry)) {
			throw new InvalidParamsException(validator.getMessage());
		}
		Entry entry = Abdera.getInstance().newEntry();
		setDefaultFields(inputEntry, entry);
		setEsipFields(inputEntry, entry);

		return entry;
	}

	private void setEsipFields(EntryBuilderInput inputEntry, Entry entry) {
		setBox(inputEntry, entry);

		QName start = new QName(
				"http://a9.com/-/opensearch/extensions/time/1.0/", "start",
				"time");
		QName end = new QName(
				"http://a9.com/-/opensearch/extensions/time/1.0/", "end",
				"time");
		setStartAndEndDateTime(entry, inputEntry.getStartTime(), start);
		setStartAndEndDateTime(entry, inputEntry.getEndTime(), end);
	}

	private void setDefaultFields(EntryBuilderInput inputEntry, Entry entry) throws InvalidParamsException {
		try {
			entry.setId(inputEntry.getId());
			entry.setTitle(inputEntry.getTitle());
			entry.setUpdated(inputEntry.getUpdated());
			entry.setSummary(inputEntry.getSummary());			
			for (LinkBean link : inputEntry.getLinks()) {
				Link entryLink = Abdera.getInstance().getFactory().newLink();
				entryLink.setHref(link.getHref());
				entryLink.setRel(link.getRel());
				entryLink.setMimeType(link.getType());		
				entry.addLink(entryLink);
			}			
			for (AuthorBean p : inputEntry.getAuthors()) {
				Person author = entry.addAuthor(p.getName());
				if ( p.getEmail() != null &&  !p.getEmail().isEmpty() ) {
					author.setEmail(p.getEmail());
				}
				if ( p.getSimpleUrl() != null &&  !p.getSimpleUrl().isEmpty() ) {
					author.setUri(p.getSimpleUrl());
				}
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidParamsException("Could not create Atom entry from input with ID=" + inputEntry.getId() + ".\n" + e.getMessage() );
		}
	}

	private void setBox(EntryBuilderInput inputEntry, Entry entry)
			throws IllegalArgumentException {

		String north = inputEntry.getGeoRSSnorth();
		String south = inputEntry.getGeoRSSsouth();
		String east = inputEntry.getGeoRSSeast();
		String west = inputEntry.getGeoRSSwest();

		String boxStr = StringUtils.arrayToDelimitedString(new String[] {
				south, west, north, east }, " ");

		try {
			Box box = new Box(boxStr);
			GeoHelper.addPosition(entry, box);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(
			// if we use the matchers we should never get here.
					"Bounding box coordinates out of range");
		}

	}

	private void setStartAndEndDateTime(Entry entry, String data, QName name) {

		Factory factory = Abdera.getNewFactory();
		Element e = factory.newElement(name);
		e.setText(data);
		entry.addExtension(e);

	}

	public EntryValidator getValidator() {
		return validator;
	}

	public void setValidator(EntryValidator validator) {
		this.validator = validator;
	}

}

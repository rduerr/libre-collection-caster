package org.nsidc.feeds.collection_caster_services;

import java.util.Calendar;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;
import org.nsidc.feeds.validator.FeedValidator;

public class FeedBuilder {

	private FeedBuilderInput defaultFeedValues = new FeedBuilderInput();
	private FeedValidator validator;

	public FeedBuilder() {
		DateTimeFormatter isoDateFormatter = ISODateTimeFormat.dateTime();
		DateTime updated = new DateTime(Calendar.getInstance().getTime());
		defaultFeedValues.setUpdated(isoDateFormatter.print(updated));
		validator = new FeedValidator(defaultFeedValues);
	}

	public Feed createFeed(FeedBuilderInput params)
			throws InvalidParamsException {
		
		if (!validator.evaluate(params)) {
			throw new InvalidParamsException(validator.getMessage());
		}

		Feed feed = Abdera.getInstance().newFeed();

		try {
			feed.setId(params.getId());
			feed.setTitle(params.getTitle());

			String updated = params.getUpdated();
			if (updated == null || updated.isEmpty()) {
				feed.setUpdated(Calendar.getInstance().getTime());
			} else {
				feed.setUpdated(updated);
			}

			for (AuthorBean p : params.getAuthors()) {
				Person author = feed.addAuthor(p.getName());
				if ( p.getEmail() != null &&  !p.getEmail().isEmpty() ) {
					author.setEmail(p.getEmail());
				}
				if ( p.getSimpleUrl() != null &&  !p.getSimpleUrl().isEmpty() ) {
					author.setUri(p.getSimpleUrl());
				}
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidParamsException("Could not create Atom Feed from input with ID=" + params.getId() + ".\n" + e.getMessage() );
		}
		return feed;

	}

}

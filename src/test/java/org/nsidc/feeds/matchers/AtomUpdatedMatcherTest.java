package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.AtomUpdatedMatcher.*;

import java.util.Date;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomUpdatedMatcherTest {

	private Entry entry;
	private EntryBuilderInput entryBuilderInput;
	private Feed feed;
	private FeedBuilderInput feedBuilderInput;
	
	@Before 
	public void setup() {
		entry = Abdera.getInstance().newEntry();
		entryBuilderInput = new EntryBuilderInput();
		feed = Abdera.getInstance().newFeed();
		feedBuilderInput = new FeedBuilderInput();
	}
	
	@Test
	public void matcher_is_true_when_Entry_updated_is_valid() {
		entry.setUpdated(new Date());
		assertThat(entry, validEntryUpdated());
	}
			
	@Test
	public void matcher_is_true_when_EntryBuilderInput_updated_is_valid_iso_timestamp() {
		entryBuilderInput.setUpdated("2009-01-01T12:01:01.000-06:00");		
		assertThat(entryBuilderInput, validEntryBuilderInputUpdated());		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_updated_is_valid_date() {
		entryBuilderInput.setUpdated("2009-10-10");		
		assertThat(entryBuilderInput, not(validEntryBuilderInputUpdated()));		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_updated_is_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputUpdated()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_updated_is_null() {
		assertThat(entry, not(validEntryUpdated()));
	}

	@Test
	public void matcher_is_false_when_entryBuilderInput_updated_is_empty() {
		entryBuilderInput.setUpdated("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputUpdated()));		
	}
	
	/*  This test would seem valid, but entry throws an exception 
	 *  if an invalid date is used for udpated value
	@Test
	public void matcher_is_false_when_Entry_updated_is_empty() {
		entry.setUpdated("");
		assertThat(entry, not(validEntryUpdated()));
	}
	*/
	
	@Test
	public void updated_date_matcher_should_provide_default_if_none_provided() {
		EntryBuilderInput defaultInput = new EntryBuilderInput();
		defaultInput.setUpdated(new Date().toString());
		
		assertThat(entryBuilderInput, validEntryBuilderInputUpdated(defaultInput));
	}
	
	@Test
	public void updated_date_matcher_should_return_false_if_default_does_not_provide_date() {
		EntryBuilderInput defaultInput = new EntryBuilderInput();		
		assertThat(entryBuilderInput, not(validEntryBuilderInputUpdated(defaultInput)));
	}
	
	@Test
	public void matcher_is_true_when_Feed_updated_is_valid() {
		feed.setUpdated(new Date());
		assertThat(feed, validFeedUpdated());
	}
		
	@Test
	public void matcher_is_true_when_FeedBuilderInput_updated_is_valid_iso_timestamp() {
		feedBuilderInput.setUpdated("2009-01-01T12:01:01.000Z");		
		assertThat(feedBuilderInput, validFeedBuilderInputUpdated());		
	}
	
	@Test
	public void matcher_is_false_when_FeedBuilderInput_updated_is_valid_date() {
		feedBuilderInput.setUpdated("2009-10-10");		
		assertThat(feedBuilderInput, not(validFeedBuilderInputUpdated()));		
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_updated_is_null() {
		assertThat(feedBuilderInput, not(validFeedBuilderInputUpdated()));		
	}
	
	@Test
	public void matcher_is_false_when_Feed_updated_is_null() {
		assertThat(feed, not(validFeedUpdated()));
	}

	@Test
	public void matcher_is_false_when_feedBuilderInput_updated_is_empty() {
		feedBuilderInput.setUpdated("");
		assertThat(feedBuilderInput, not(validFeedBuilderInputUpdated()));		
	}
	
	@Test
	public void updated_date_feedBuilderInput_matcher_should_provide_default_if_none_provided() {
		FeedBuilderInput defaultInput = new FeedBuilderInput();
		defaultInput.setUpdated(new Date().toString());
		
		assertThat(feedBuilderInput, validFeedBuilderInputUpdated(defaultInput));
	}
	
	@Test
	public void updated_date_feedBuilderInput_matcher_should_return_false_if_default_does_not_provide_date() {
		FeedBuilderInput defaultInput = new FeedBuilderInput();		
		assertThat(feedBuilderInput, not(validFeedBuilderInputUpdated(defaultInput)));
	}
	
}

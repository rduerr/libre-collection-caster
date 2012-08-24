package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.AtomTitleMatcher.*;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomTitleMatcherTest {

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
	public void matcher_is_true_when_Entry_title_is_valid() {
		entry.setTitle("Title");
		assertThat(entry, validEntryTitle());
	}
	
	@Test
	public void matcher_is_true_when_EntryBuilderInput_title_is_valid() {
		entryBuilderInput.setTitle("Title");
		assertThat(entryBuilderInput, validEntryBuilderInputTitle());		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_title_is_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputTitle()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_title_is_null() {
		assertThat(entry, not(validEntryTitle()));
	}

	@Test
	public void matcher_is_false_when_entryBuilderInput_title_is_empty() {
		entryBuilderInput.setTitle("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputTitle()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_title_is_empty() {
		entry.setTitle("");
		assertThat(entry, not(validEntryTitle()));
	}


	@Test
	public void matcher_is_true_when_Feed_title_is_valid() {
		feed.setTitle("Title");
		assertThat(feed, validFeedTitle());
	}
	
	@Test
	public void matcher_is_true_when_FeedBuilderInput_title_is_valid() {
		feedBuilderInput.setTitle("Title");
		assertThat(feedBuilderInput, validFeedBuilderInputTitle());		
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_title_is_null() {
		assertThat(feedBuilderInput, not(validFeedBuilderInputTitle()));		
	}
	
	@Test
	public void matcher_is_false_when_Feed_title_is_null() {
		assertThat(feed, not(validFeedTitle()));
	}

	@Test
	public void matcher_is_false_when_feedBuilderInput_title_is_empty() {
		feedBuilderInput.setTitle("");
		assertThat(feedBuilderInput, not(validFeedBuilderInputTitle()));		
	}
	
	@Test
	public void matcher_is_false_when_Feed_title_is_empty() {
		feed.setTitle("");
		assertThat(feed, not(validFeedTitle()));
	}

	
}

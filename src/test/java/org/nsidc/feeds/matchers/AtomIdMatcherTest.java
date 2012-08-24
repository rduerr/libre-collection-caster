package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.AtomIdMatcher.*;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomIdMatcherTest {

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
	public void matcher_is_true_when_Entry_id_is_valid() {
		entry.setId("http://id");
		assertThat(entry, validEntryId());
	}
	
	@Test
	public void matcher_is_true_when_EntryBuilderInput_id_is_valid() {
		entryBuilderInput.setId("http://id");
		assertThat(entryBuilderInput, validEntryBuilderInputId());		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_id_is_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputId()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_id_is_null() {
		assertThat(entry, not(validEntryId()));
	}

	@Test
	public void matcher_is_false_when_entryBuilderInput_id_is_empty() {
		entryBuilderInput.setId("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputId()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_id_is_empty() {
		entry.setId("");
		assertThat(entry, not(validEntryId()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_id_is_not_url() {
		entry.setId("id");
		assertThat(entry, not(validEntryId()));
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_id_is_not_url() {
		entryBuilderInput.setId("id");
		assertThat(entryBuilderInput, not(validEntryBuilderInputId()));
	}
	
	@Test
	public void matcher_is_true_when_Feed_id_is_valid() {
		feed.setId("http://id");
		assertThat(feed, validFeedId());
	}
	
	@Test
	public void matcher_is_true_when_FeedBuilderInput_id_is_valid() {
		feedBuilderInput.setId("http://id");
		assertThat(feedBuilderInput, validFeedBuilderInputId());		
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_id_is_null() {
		assertThat(feedBuilderInput, not(validFeedBuilderInputId()));		
	}
	
	@Test
	public void matcher_is_false_when_Feed_id_is_null() {
		assertThat(feed, not(validFeedId()));
	}

	@Test
	public void matcher_is_false_when_feedBuilderInput_id_is_empty() {
		feedBuilderInput.setId("");
		assertThat(feedBuilderInput, not(validFeedBuilderInputId()));		
	}
	
	@Test
	public void matcher_is_false_when_Feed_id_is_empty() {
		feed.setId("");
		assertThat(feed, not(validFeedId()));
	}
	
	@Test
	public void matcher_is_false_when_Feed_id_is_not_url() {
		feed.setId("id");
		assertThat(feed, not(validFeedId()));
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_id_is_not_url() {
		feedBuilderInput.setId("id");
		assertThat(feedBuilderInput, not(validFeedBuilderInputId()));		
	}
}

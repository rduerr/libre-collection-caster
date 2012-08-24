package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.AtomAuthorMatcher.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomAuthorMatcherTest {
	
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
	public void matcher_is_true_when_Entry_author_is_valid() {
		entry.addAuthor("Bob Smith");
		assertThat(entry, validEntryAuthor());
	}
	
	@Test
	public void matcher_is_true_when_EntryBuilderInput_author_is_valid() {
		entryBuilderInput.setAuthors(createAuthorList("Bob Smith"));
		assertThat(entryBuilderInput, validEntryBuilderInputAuthor());
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_author_is_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_Entry_author_is_null() {
		assertThat(entry, not(validEntryAuthor()));
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_author_list_is_empty() {
		entryBuilderInput.setAuthors(new ArrayList<AuthorBean>());
		assertThat(entryBuilderInput, not(validEntryBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_author_is_blank() {	
		entryBuilderInput.setAuthors(createAuthorList(""));
		assertThat(entryBuilderInput, not(validEntryBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_Entry_author_is_blank() {
		entry.addAuthor("");
		assertThat(entry, not(validEntryAuthor()));
	}


	@Test
	public void matcher_is_true_when_Feed_author_is_valid() {
		feed.addAuthor("Bob Smith");
		assertThat(feed, validFeedAuthor());
	}
	
	@Test
	public void matcher_is_true_when_FeedBuilderInput_author_is_valid() {
		feedBuilderInput.setAuthors(createAuthorList("Bob Smith"));
		assertThat(feedBuilderInput, validFeedBuilderInputAuthor());
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_author_is_null() {
		assertThat(feedBuilderInput, not(validFeedBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_Feed_author_is_null() {
		assertThat(feed, not(validFeedAuthor()));
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_author_list_is_empty() {
		feedBuilderInput.setAuthors(new ArrayList<AuthorBean>());
		assertThat(feedBuilderInput, not(validFeedBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_feedBuilderInput_author_is_blank() {	
		feedBuilderInput.setAuthors(createAuthorList(""));
		assertThat(feedBuilderInput, not(validFeedBuilderInputAuthor()));	
	}
	
	@Test
	public void matcher_is_false_when_Feed_author_is_blank() {
		feed.addAuthor("");
		assertThat(feed, not(validFeedAuthor()));
	}

	
	private List<AuthorBean> createAuthorList(String authorName) {
		AuthorBean author = new AuthorBean();
		author.setName(authorName);
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(author);
		return authors;
	}
}

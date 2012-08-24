package org.nsidc.feeds.collection_caster_services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class FeedBuilderTest {
	FeedBuilder builder = null;
	FeedBuilderInput params = null;
	Feed feed = null;
	
	@Test
	public void testSuccessfullBuildAFeed() throws InvalidParamsException {
		
		params = mock(FeedBuilderInput.class);
		when(params.getId()).thenReturn("http://myurl.org");
		when(params.getTitle()).thenReturn("feedTitle");
		when(params.getUpdated()).thenReturn("2011-05-25T14:01:54Z");
		
		//author
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		authors.add(new AuthorBean("name2"));
		when(params.getAuthors()).thenReturn(authors);
		
		builder = new FeedBuilder();
		
		Feed feed = builder.createFeed(params);
		
		assertEquals(feed.getId().toString(), "http://myurl.org");
		assertEquals(feed.getTitle(), "feedTitle");
		assertEquals(new DateTime(feed.getUpdated()), new DateTime("2011-05-25T14:01:54Z"));
		
		List<Person> p = feed.getAuthors();
		assertEquals(p.get(0).getName(),"name1");
		assertEquals(p.get(0).getUri().toString(),"url1");
		assertEquals(p.get(0).getEmail(),"email1");
		
		// Second author should only have name and not url or email
		assertEquals(p.get(1).getName(),"name2");
		assertThat(p.get(1).getUri(), is(nullValue()));
		assertThat(p.get(1).getEmail(), is(nullValue()));
		
	}
	
	@Test(expected=InvalidParamsException.class)
	public void testThrowAnExceptionWhenFeedIsNotValid() throws InvalidParamsException {
		params = mock(FeedBuilderInput.class);
		when(params.getId()).thenReturn(null);
		builder = new FeedBuilder();
		
		builder.createFeed(params);
	}
	
	@Test
	public void test_that_updated_is_set_and_valid() throws InvalidParamsException {
		FeedBuilderInput realInput = new FeedBuilderInput();
		realInput.setId("http://myurl.org");
		realInput.setTitle("Title");
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		realInput.setAuthors(authors);
		builder = new FeedBuilder();
		
		Feed feed = builder.createFeed(realInput);
		
		assertEquals(new DateTime(feed.getUpdated()), new DateTime(realInput.getUpdated()));
		
	}

	@Test
	public void test_that_exception_is_thrown_when_update_not_timestamp() throws InvalidParamsException {
		FeedBuilderInput realInput = new FeedBuilderInput();
		realInput.setId("http://myurl.org");
		realInput.setTitle("Title");
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		realInput.setAuthors(authors);
		realInput.setUpdated("2009-01-01T23:00:00.000Z");
		builder = new FeedBuilder();
		
		//execute
		try {
			builder.createFeed(realInput);
		} catch (InvalidParamsException e) {
			//verify
			assertThat(e.getMessage(), containsString("with ID=http://myurl.org"));
		}	
				
	}
	
	@Test
	public void test_that_exception_with_correct_error_message() throws InvalidParamsException {
		FeedBuilderInput realInput = new FeedBuilderInput();
		realInput.setId("http://myurl.org");
		realInput.setTitle("Title");
		realInput.setUpdated("2009-01-01T23:00:00.000Z");
		builder = new FeedBuilder();
		
		//execute
		try {
			builder.createFeed(realInput);
		} catch (InvalidParamsException e) {
			//verify
			assertThat(e.getMessage(), containsString("Feed input should contain at least one valid author."));
		}	
				
	}
	
}

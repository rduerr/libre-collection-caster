package org.nsidc.feeds.collection_caster_services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.abdera.ext.geo.Box;
import org.apache.abdera.ext.geo.GeoHelper;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Person;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.LinkBean;
import org.nsidc.feeds.validator.EntryValidator;

public class EntryBuilderTest {
	private EntryBuilder builder = null;
	private EntryBuilderInput params = null;
	
	@Before
	public void setup() {
		builder = new EntryBuilder();
		params = mock(EntryBuilderInput.class);
	}
		
	@Test
	public void test_builder_will_build_an_entry_if_validation_was_succefull() throws InvalidParamsException{
		params = mock(EntryBuilderInput.class);
		when(params.getId()).thenReturn("1");
		when(params.getGeoRSSnorth()).thenReturn("90");
		when(params.getGeoRSSsouth()).thenReturn("-90");
		when(params.getGeoRSSeast()).thenReturn("180");
		when(params.getGeoRSSwest()).thenReturn("-180");
		
		EntryValidator mockValidator = mock(EntryValidator.class);
		when(mockValidator.evaluate(params)).thenReturn(true);
		builder.setValidator(mockValidator);
		Entry entry = builder.createEntry(params);
		assertEquals(entry.getId().toString(), "1");
	}
	
	@Test
	public void test_entry_contains_the_same_fields_of_input() throws InvalidParamsException, ParseException, IllegalArgumentException
	{
		//setup, if we don't mock all the required fields the validator will throw an exception. 
		
		params = mock(EntryBuilderInput.class);	
		builder = new EntryBuilder();		
		//basic fields
		when(params.getId()).thenReturn("1");
		when(params.getTitle()).thenReturn("Title");
		when(params.getUpdated()).thenReturn("2011-05-25T14:01:54Z");
		when(params.getSummary()).thenReturn("summary");
		//links
		List<LinkBean> links = new ArrayList<LinkBean>(); 
		links.add(new LinkBean("http://myurl.org", "rel1", "text/html"));
		links.add(new LinkBean("http://myurl.org", "rel2", "application/xml"));
		when(params.getLinks()).thenReturn(links);
		//bbox
		when(params.getGeoRSSnorth()).thenReturn("90");
		when(params.getGeoRSSsouth()).thenReturn("-90");
		when(params.getGeoRSSeast()).thenReturn("180");
		when(params.getGeoRSSwest()).thenReturn("-180");
		//author
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		authors.add(new AuthorBean("name2"));
		when(params.getAuthors()).thenReturn(authors);
		
		//start & end time
		when(params.getStartTime()).thenReturn("2011-06-25T14:01:54Z");
		when(params.getEndTime()).thenReturn("2011-07-25T11:01:54Z");
				
		EntryValidator mockValidator = mock(EntryValidator.class);
		when(mockValidator.evaluate(params)).thenReturn(true);
		builder.setValidator(mockValidator);
		
		//execute
		Entry entry = builder.createEntry(params);
		
		//verify		
		assertEquals (entry.getId().toString(),"1");
		assertEquals (new DateTime(entry.getUpdated()), new DateTime("2011-05-25T14:01:54Z"));
		assertEquals (entry.getTitle(),"Title");
		assertEquals(entry.getSummary(), "summary");
		List<Link> entryLinks = entry.getLinks();
		
		assertEquals(entryLinks.get(0).getHref().toString(),"http://myurl.org");
		assertEquals(entryLinks.get(0).getRel().toString(),"rel1");
		assertEquals(entryLinks.get(0).getMimeType().toString(),"text/html");		
		assertEquals(entryLinks.get(1).getHref().toString(),"http://myurl.org");
		assertEquals(entryLinks.get(1).getRel().toString(),"rel2");
		assertEquals(entryLinks.get(1).getMimeType().toString(),"application/xml");
		assertEquals(entryLinks.size(),2);
		
		List<Person> p = entry.getAuthors();
		assertEquals(p.get(0).getName(),"name1");
		assertEquals(p.get(0).getUri().toString(),"url1");
		assertEquals(p.get(0).getEmail(),"email1");
				
		// Second author should only have name and not url or email
		assertEquals(p.get(1).getName(),"name2");
		assertThat(p.get(1).getUri(), is(nullValue()));
		assertThat(p.get(1).getEmail(), is(nullValue()));
		
		QName boxName = new QName("http://www.georss.org/georss", "box");
		assertEquals(entry.getExtension(boxName).getText(),"-90.0 -180.0 90.0 180.0");
		
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		assertEquals(entry.getExtension(start).getText(), "2011-06-25T14:01:54Z");
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		assertEquals(entry.getExtension(end).getText(), "2011-07-25T11:01:54Z");
		
	}
	
	
	@Test(expected=InvalidParamsException.class)
	public void test_exception_is_thrown_when_the_entry_is_not_valid() throws InvalidParamsException {	
		EntryValidator mockValidator = mock(EntryValidator.class);
		when(mockValidator.evaluate(params)).thenReturn(false);
		builder.setValidator(mockValidator);
		builder.createEntry(params);		
	}
	
	@Test
	public void test_that_default_values_are_generated() throws InvalidParamsException {
		//setup
		QName boxName = new QName("http://www.georss.org/georss", "box");
		
		EntryBuilderInput realEntry = new EntryBuilderInput();	
			
		realEntry.setId("http://myid.org");
		realEntry.setTitle("title");
		realEntry.setSummary("sumamry");		
		List<LinkBean> links = new ArrayList<LinkBean>(); 
		links.add(new LinkBean("http://href1", "alternate", "text/html"));
		links.add(new LinkBean("http://href2", "http://esipfed.org/ns/discovery/1.1/data#", "application/xml"));
		realEntry.setLinks(links);
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		realEntry.setAuthors(authors);
		realEntry.setStartTime("2009-01-01");
		realEntry.setEndTime("2009-01-02");
		
		//execute
		Entry entry = builder.createEntry(realEntry);		

		//verify
		assertEquals(entry.getExtension(boxName).getText(),"-90.0 -180.0 90.0 180.0");
		assertNotNull(entry.getUpdated());
		
	}
	
	@Test
	public void test_that_builder_succeeds_when_update_invalid() throws InvalidParamsException {
		//setup
		EntryBuilderInput realEntry = new EntryBuilderInput();	
			
		realEntry.setId("http://myid.org");
		realEntry.setTitle("title");
		realEntry.setSummary("sumamry");		
		List<LinkBean> links = new ArrayList<LinkBean>(); 
		links.add(new LinkBean("http://href1", "alternate", "text/html"));
		links.add(new LinkBean("http://href2", "http://esipfed.org/ns/discovery/1.1/data#", "application/xml"));
		realEntry.setLinks(links);
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		realEntry.setAuthors(authors);
		realEntry.setStartTime("2009-01-01");
		realEntry.setEndTime("2009-01-02");
		realEntry.setUpdated("2009-01-01T23:01:01.999-07:30");
		
		//execute
		try {
			builder.createEntry(realEntry);
		} catch (InvalidParamsException e) {
			//verify
			assertThat(e.getMessage(), containsString("with ID=http://myid.org"));
		}		
	}
	
}

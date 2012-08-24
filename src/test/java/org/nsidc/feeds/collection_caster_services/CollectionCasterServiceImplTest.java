package org.nsidc.feeds.collection_caster_services;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.abdera.model.Feed;
import org.junit.*;
import org.nsidc.feeds.collection_caster_services.CollectionCasterServiceImpl;

public class CollectionCasterServiceImplTest {
	CollectionCasterServiceImpl service;

	@Before
	public void setUp() {
		service = new CollectionCasterServiceImpl();
	}
	
	@Test
	public void example_should_be_read_from_xml_file() throws Exception {
		ResourceFileReader reader = mock(ResourceFileReader.class); 
		when(reader.readFileAsString(anyString())).thenReturn("file contents");
		service.setReader(reader);
				
		assertThat(service.example(), containsString("file contents"));
	}
	
	@Test
	public void example_should_return_string() {
		assertThat(service.example(), containsString("Example Feed"));
	}
	
	@Test
	public void example_string_should_be_xml() {	
		Feed feed = new StringToFeedConverter().convertToFeed(service.example());
		
		assertThat(feed.getTitle(), containsString("Example Feed"));
	}
	
	@Test
	public void help_should_be_read_from_help_html_resource() throws IOException {
		ResourceFileReader reader = mock(ResourceFileReader.class); 
		when(reader.readFileAsString(anyString())).thenReturn("file contents");
		service.setReader(reader);
				
		assertThat(service.help(), containsString("file contents"));
		verify(reader).readFileAsString("/help.html");
	}

}

package org.nsidc.feeds.collection_caster_services;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.apache.abdera.model.Entry;
import org.junit.Test;

public class StringToFeedConverterTest {

	@Test
	public void convertToFeed_should_return_feed() {
		StringToFeedConverter converter = new StringToFeedConverter();
		assertThat(converter.convertToFeed(SIMPLE_FEED).getTitle(),
				is("Example Feed"));
	}

	@Test
	public void convertToEntry_should_return_entry() {
		StringToFeedConverter converter = new StringToFeedConverter();
		assertThat(converter.convertToEntry(SIMPLE_ENTRY).getTitle(),
				is("Atom-Powered Robots Run Amok"));
	}

	private static final String SIMPLE_FEED = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<feed xmlns=\"http://www.w3.org/2005/Atom\">"
			+ "<title>Example Feed</title> "
			+ "<link href=\"http://example.org/\"/>"
			+ "<updated>2003-12-13T18:30:02Z</updated>"
			+ "<author> "
			+ " <name>John Doe</name>"
			+ "</author>"
			+ "<id>urn:uuid:60a76c80-d399-11d9-b93C-0003939e0af6</id>"
			+ " <entry>"
			+ "  <title>Atom-Powered Robots Run Amok</title>"
			+ "  <link href=\"http://example.org/2003/12/13/atom03\"/>"
			+ "  <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>"
			+ "  <updated>2003-12-13T18:30:02Z</updated>"
			+ "  <summary>Some text.</summary>" + " </entry>" + "</feed>";

	private static final String SIMPLE_ENTRY = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<entry xmlns=\"http://www.w3.org/2005/Atom\">"
			+ "  <title>Atom-Powered Robots Run Amok</title>"
			+ "  <link href=\"http://example.org/2003/12/13/atom03\"/>"
			+ "  <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>"
			+ "  <updated>2003-12-13T18:30:02Z</updated>"
			+ "  <summary>Some text.</summary>" + " </entry>";

	private static final String CASTER_FEED_DEFECT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:time=\" http://a9.com/-/opensearch/extensions/time/1.0/\">"
			+ "<title>Example Feed</title> "
			+ "<link href=\"http://example.org/\"/>"
			+ "<updated>2003-12-13T18:30:02Z</updated>"
			+ "<author> "
			+ " <name>John Doe</name>"
			+ "</author>"
			+ "<id>urn:uuid:60a76c80-d399-11d9-b93C-0003939e0af6</id>"
			+ " <entry>"
			+ "  <title>Atom-Powered Robots Run Amok</title>"
			+ "  <link href=\"http://example.org/2003/12/13/atom03\"/>"
			+ "  <id>urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a</id>"
			+ "  <updated>2003-12-13T18:30:02Z</updated>"
			+ "  <summary>Some text.</summary>"
			+ "  <time:start>This is an example start time</time:start>"
			+ "  <time:end>END TIME!!!</time:end>" + " </entry>" + "</feed>";

}

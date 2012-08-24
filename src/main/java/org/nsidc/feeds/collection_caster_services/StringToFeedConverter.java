package org.nsidc.feeds.collection_caster_services;

import java.io.ByteArrayInputStream;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Entry;
import org.apache.abdera.parser.Parser;

public class StringToFeedConverter {

	public Feed convertToFeed(String xmlString) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlString.getBytes());

		Parser parser = Abdera.getNewParser();
		Document<Feed> doc = parser.parse(inputStream);

		Feed feed = doc.getRoot();
		return feed;
	}

	public Entry convertToEntry(String xmlString) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlString.getBytes());

		Parser parser = Abdera.getNewParser();
		Document<Entry> doc = parser.parse(inputStream);

		Entry entry = doc.getRoot();
		return entry;
	}

}

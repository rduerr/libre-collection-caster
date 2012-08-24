package org.nsidc.feeds.validator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.nsidc.feeds.validator.Validators.validAtomEntry;
import static org.nsidc.feeds.validator.Validators.validESIPEntry;

import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.ext.geo.Box;
import org.apache.abdera.ext.geo.GeoHelper;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {

	private Entry entry;

	@Before
	public void setup() {
		entry = Abdera.getInstance().newEntry();
				
	}
	
	@Test
	public void atom_matcher_should_return_false_if_bad_entry() {
		assertThat(entry, is(not(validAtomEntry())));
	}
	
	@Test
	public void atom_matcher_should_return_false_if_invalid_id() {
		entry.addAuthor("Bob Smith");
		entry.setTitle("Title");
		entry.setId("id"); // this id is invalid
		entry.setUpdated(new Date());
		assertThat(entry, is(not(validAtomEntry())));
	}
	
	@Test
	public void atom_matcher_should_return_true_if_good_entry() {
		entry.addAuthor("Bob Smith");
		entry.setTitle("Title");
		entry.setId("http://myurl.org");
		entry.setUpdated(new Date());
		assertThat(entry, is(validAtomEntry()));
	}
	
	@Test
	public void atom_matcher_should_return_true_if_esip_entry() {
		entry.addAuthor("Bob Smith");
		entry.setTitle("Title");
		entry.setId("http://myurl.org");
		entry.setUpdated(new Date());
		Box box = new Box("-90 -180 90 180");
		GeoHelper.addPosition(entry, box);
		
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		
		addStartAndEnd(entry, "2009-10-10", start);
		addStartAndEnd(entry, "2009-10-11", end);
		Link link = Abdera.getNewFactory().newLink();
		
		link.setHref("http://url.org");
		link.setRel("alternate");
		link.setMimeType("text/plain");
		entry.addLink(link);
		Link esipLink = Abdera.getNewFactory().newLink();
		esipLink.setHref("http://url.org");
		esipLink.setRel("http://esipfed.org/ns/discovery/1.1/data#");
		esipLink.setMimeType("text/plain");
		entry.addLink(esipLink);
		
		assertThat(entry, is(validESIPEntry()));
	}
	
	private void addStartAndEnd(Entry entry, String data, QName name) {
		Element e = Abdera.getNewFactory().newElement(name);
		e.setText(data);
		entry.addExtension(e);
	}
}

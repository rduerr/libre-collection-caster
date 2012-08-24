package org.nsidc.feeds.matchers;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.EsipStartDateMatcher.*;

import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EsipStartDateMatcherTest {

	private Entry entry;
	private EntryBuilderInput entryBuilderInput;
	
	@Before 
	public void setup() {
		entry = Abdera.getInstance().newEntry();
		entryBuilderInput = new EntryBuilderInput();
	}

	@Test
	public void matcher_is_true_when_entryBuilderInput_has_valid_iso_date() {
		entryBuilderInput.setStartTime("2009-10-10T01:01:01.000Z");
		assertThat(entryBuilderInput, validEntryBuilderInputStart());	
	}

	@Test
	public void matcher_is_true_when_entryBuilderInput_has_valid_date_only() {
		entryBuilderInput.setStartTime("2009-10-10");
		assertThat(entryBuilderInput, validEntryBuilderInputStart());	
	}
	
	@Test
	public void matcher_is_true_when_Entry_has_valid_iso_date() {
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		addExtension(entry, "2009-10-10T01:01:01.000Z", start);
		assertThat(entry, validEntryStart());
	}
	
	@Test
	public void matcher_is_true_when_Entry_has_valid_date_only() {
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		addExtension(entry, "2009-10-10", start);
		assertThat(entry, validEntryStart());
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_is_null() {
		entryBuilderInput = null;
		assertThat(entryBuilderInput, not(validEntryBuilderInputStart()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_startTime_is_null() {
		entryBuilderInput.setStartTime(null);
		assertThat(entryBuilderInput, not(validEntryBuilderInputStart()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_startTime_is_empty() {
		entryBuilderInput.setStartTime("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputStart()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_startTime_is_invalid_date() {
		entryBuilderInput.setStartTime("2009-10-10a");
		assertThat(entryBuilderInput, not(validEntryBuilderInputStart()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_is_null() {
		entry = null;
		assertThat(entry, not(validEntryStart()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_startTime_is_null() {		
		assertThat(entry, not(validEntryStart()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_startTime_extension_is_null() {		
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		addExtension(entry, null, start);
		assertThat(entry, not(validEntryStart()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_startTime_extension_is_empty() {		
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		addExtension(entry, "", start);
		assertThat(entry, not(validEntryStart()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_startTime_extension_is_not_date() {		
		QName start = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "start", "time");
		addExtension(entry, "2009-10-10a", start);
		assertThat(entry, not(validEntryStart()));
	}
	
	
	private void addExtension(Entry entry, String data, QName name) {
		Factory factory = Abdera.getNewFactory();
		Element e = factory.newElement(name);
		e.setText(data);
		entry.addExtension(e);
	}
}

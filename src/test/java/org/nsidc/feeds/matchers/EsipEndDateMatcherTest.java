package org.nsidc.feeds.matchers;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.EsipEndDateMatcher.*;

import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EsipEndDateMatcherTest {

	private Entry entry;
	private EntryBuilderInput entryBuilderInput;
	
	@Before 
	public void setup() {
		entry = Abdera.getInstance().newEntry();
		entryBuilderInput = new EntryBuilderInput();
	}

	@Test
	public void matcher_is_true_when_entryBuilderInput_has_valid_iso_date() {
		entryBuilderInput.setEndTime("2009-10-10T01:01:01.000Z");
		assertThat(entryBuilderInput, validEntryBuilderInputEnd());	
	}

	@Test
	public void matcher_is_true_when_entryBuilderInput_has_valid_date_only() {
		entryBuilderInput.setEndTime("2009-10-10");
		assertThat(entryBuilderInput, validEntryBuilderInputEnd());	
	}
	
	@Test
	public void matcher_is_true_when_Entry_has_valid_iso_date() {
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		addExtension(entry, "2009-10-10T01:01:01.000Z", end);
		assertThat(entry, validEntryEnd());
	}
	
	@Test
	public void matcher_is_true_when_Entry_has_valid_date_only() {
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		addExtension(entry, "2009-10-10", end);
		assertThat(entry, validEntryEnd());
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_is_null() {
		entryBuilderInput = null;
		assertThat(entryBuilderInput, not(validEntryBuilderInputEnd()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_endTime_is_null() {
		entryBuilderInput.setEndTime(null);
		assertThat(entryBuilderInput, not(validEntryBuilderInputEnd()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_endTime_is_empty() {
		entryBuilderInput.setEndTime("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputEnd()));		
	}
	
	@Test
	public void matcher_is_false_when_EntryBuilderInput_endTime_is_invalid_date() {
		entryBuilderInput.setEndTime("2009-10-10a");
		assertThat(entryBuilderInput, not(validEntryBuilderInputEnd()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_is_null() {
		entry = null;
		assertThat(entry, not(validEntryEnd()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_endTime_is_null() {		
		assertThat(entry, not(validEntryEnd()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_endTime_extension_is_null() {		
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		addExtension(entry, null, end);
		assertThat(entry, not(validEntryEnd()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_endTime_extension_is_empty() {		
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		addExtension(entry, "", end);
		assertThat(entry, not(validEntryEnd()));
	}
	
	@Test
	public void matcher_is_false_when_Entry_endTime_extension_is_not_date() {		
		QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
		addExtension(entry, "2009-10-10a", end);
		assertThat(entry, not(validEntryEnd()));
	}
		
	private void addExtension(Entry entry, String data, QName name) {
		Factory factory = Abdera.getNewFactory();
		Element e = factory.newElement(name);
		e.setText(data);
		entry.addExtension(e);
	}
}

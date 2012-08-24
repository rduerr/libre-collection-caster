package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.EsipBboxMatcher.*;

import javax.xml.namespace.QName;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EsipBboxMatcherTest {

	private Entry entry;
	private EntryBuilderInput entryBuilderInput;
	
	@Before 
	public void setup() {
		entry = Abdera.getInstance().newEntry();
		entryBuilderInput = new EntryBuilderInput();
	}
	
	@Test
	public void esip_bbox_matcher_should_return_true_if_entryBuilderInput_is_valid() {
		entryBuilderInput.setGeoRSSeast("42");
		entryBuilderInput.setGeoRSSnorth("37");
		entryBuilderInput.setGeoRSSsouth("36");
		entryBuilderInput.setGeoRSSwest("22");
		
		assertThat(entryBuilderInput, validEntryBuilderInputBbox());
	}
	
	@Test
	public void esip_bbox_matcher_should_return_true_if_Entry_is_valid() {
		QName boxName = new QName("http://www.georss.org/georss", "box");		
		entry.addExtension(boxName);		
		assertThat(entry, validEntryBbox());
	}
	
	@Test
	public void esip_bbox_matcher_should_return_true_if_entryBuilderInput_is_null_and_default_is_valid() {
		EntryBuilderInput defaultInput = new EntryBuilderInput();
		defaultInput.setGeoRSSeast("42");
		defaultInput.setGeoRSSnorth("37");
		defaultInput.setGeoRSSsouth("36");
		defaultInput.setGeoRSSwest("22");				
		assertThat(entryBuilderInput, validEntryBuilderInputBbox(defaultInput));
	}
		
	@Test
	public void esip_bbox_matcher_should_return_false_if_entryBuilderInput_and_default_are_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox()));		
	}
	
	@Test
	public void esip_bbox_matcher_should_return_false_if_Entry_is_null() {
		assertThat(entry, not(validEntryBbox()));
	}
	
	@Test
	public void esip_bbox_matcher_should_return_false_if_entryBuilderInput_and_default_are_empty() {
		entryBuilderInput.setGeoRSSeast("");
		entryBuilderInput.setGeoRSSnorth("12");
		entryBuilderInput.setGeoRSSsouth("12");
		entryBuilderInput.setGeoRSSwest("12");
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox()));		
	}
	
	/* This would seem like a good test, addExtension throws an 
	 * exception when the value is empty	 
	@Test
	public void esip_bbox_matcher_should_return_false_if_Entry_is_empty() {
		QName boxName = new QName("http://www.georss.org/georss", "");
		entry.addExtension(boxName);
		assertThat(entry, not(validEntryBbox()));
	}
	*/
	
	@Test
	public void esip_bbox_matcher_should_return_false_if_input_is_null_and_default_is_out_of_bounds() {
		EntryBuilderInput outOfBoundInputoutOfBoundInput = new EntryBuilderInput();
		outOfBoundInputoutOfBoundInput.setGeoRSSeast("200");
		outOfBoundInputoutOfBoundInput.setGeoRSSnorth("37");
		outOfBoundInputoutOfBoundInput.setGeoRSSsouth("36");
		outOfBoundInputoutOfBoundInput.setGeoRSSwest("-40000");
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox(outOfBoundInputoutOfBoundInput)));
	}
		
	@Test
	public void esip_bbox_matcher_should_return_false_if_input_is_out_of_bounds_even_if_there_is_a_default() {
		EntryBuilderInput outOfBoundInput = new EntryBuilderInput();
		outOfBoundInput.setGeoRSSeast("42");
		outOfBoundInput.setGeoRSSnorth("37");
		outOfBoundInput.setGeoRSSsouth("36");
		outOfBoundInput.setGeoRSSwest("22");
		
		entryBuilderInput.setGeoRSSeast("200");
		entryBuilderInput.setGeoRSSnorth("37");
		entryBuilderInput.setGeoRSSsouth("36");
		entryBuilderInput.setGeoRSSwest("22");
		
		
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox(outOfBoundInput)));
	}
	
	@Test
	public void esip_bbox_matcher_should_return_false_if_entryBuilderInput_is_out_of_bounds_and_no_default() {
		entryBuilderInput.setGeoRSSeast("200");
		entryBuilderInput.setGeoRSSnorth("37");
		entryBuilderInput.setGeoRSSsouth("36");
		entryBuilderInput.setGeoRSSwest("22");
		
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox()));
	}
	
	@Test
	public void esip_bbox_matcher_should_return_false_if_input_bbox_is_missing_and_default_exists_but_no_bbox() {
		EntryBuilderInput defaultInput = new EntryBuilderInput();		
		assertThat(entryBuilderInput, not(validEntryBuilderInputBbox(defaultInput)));
	}
	
	
	
}

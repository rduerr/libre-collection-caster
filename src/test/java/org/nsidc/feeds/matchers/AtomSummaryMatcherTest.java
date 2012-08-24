package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.nsidc.feeds.matchers.AtomSummaryMatcher.validEntryBuilderInputSummary;

import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class AtomSummaryMatcherTest {

	
	private EntryBuilderInput entryBuilderInput;
	
	
	@Before 
	public void setup() {
		entryBuilderInput = new EntryBuilderInput();
	}
	
	@Test
	public void matcher_is_true_when_EntryBuilderInput_summary_is_valid() {
		entryBuilderInput.setSummary("Summary");
		assertThat(entryBuilderInput, validEntryBuilderInputSummary());		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_summary_is_null() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputSummary()));		
	}

	@Test
	public void matcher_is_false_when_entryBuilderInput_summary_is_empty() {
		entryBuilderInput.setSummary("");
		assertThat(entryBuilderInput, not(validEntryBuilderInputSummary()));		
	}
	


	
}

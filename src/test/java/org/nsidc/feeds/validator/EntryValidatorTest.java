package org.nsidc.feeds.validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.LinkBean;

public class EntryValidatorTest {
	@Test
	public void test_that_evaluate_returns_true_when_valid() {

		//setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		
		//exercise
		boolean valid = validator.evaluate(input);
		//verify
		assertThat(valid, is(true));
	}

	@Test
	public void test_that_evaluate_returns_false_when_invalid() {
		Validator validator = new EntryValidator();
		EntryBuilderInput input;
		input = new EntryBuilderInput();
		necessarySetUp(input);

		//exercise
		input.setTitle(null);
		boolean valid = validator.evaluate(input);
		//verify
		assertThat(valid, is(false));
	}

	@Test
	public void test_that_default_value_are_applied() {
		//setup
		EntryBuilderInput defaultEntry;
		defaultEntry = new EntryBuilderInput();
		Validator validator = new EntryValidator(defaultEntry);
		
		boolean valid;
		valid = validator.evaluate(defaultEntry);
		
		assertThat(valid, is(false));
		
		necessarySetUp(defaultEntry);
		defaultSettings(defaultEntry);

		//exercise
		valid = validator.evaluate(defaultEntry);
		//verify
		assertThat(valid, is(true));	
	}

	@Test
	public void test_that_message_does_not_display_when_input_is_valid() {
		//setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		
		//exercise
		validator.evaluate(input);
		
		//verify
		assertThat(validator.getMessage().isEmpty(),is(true));
	}
		
	
	@Test
	public void message_informs_that_author_is_missing() {
		// setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		input.setAuthors(null);
		
		//exercise
		validator.evaluate(input);		
		
		//verify
		assertThat(validator.getMessage(),containsString("Author tag is missing"));
	}
	
	@Test
	public void message_informs_that_title_is_missing() {
		// setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		input.setTitle(null);
		
		//exercise
		validator.evaluate(input);	
		
		//verify
		assertThat(validator.getMessage(),containsString("Title tag is missing"));
	}
	
	@Test
	public void message_informs_that_id_is_missing() {
		// setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		input.setId(null);
		
		//exercise
		validator.evaluate(input);	
		
		//verify
		assertThat(validator.getMessage(),containsString("should have valid ID"));
	}
	
	@Test
	@Ignore
	public void message_informs_that_updated_is_missing() {
		// This is ignored for now until we decide whether
		// EntryBuilderInput can be missing an updated date
		
		// setup
		Validator validator = new EntryValidator();
		EntryBuilderInput input = createInput();
		input.setUpdated(null);
		
		//exercise
		validator.evaluate(input);	
		
		//verify
		assertThat(validator.getMessage(),containsString("Updated tag is missing"));
	}
	
	/////////////////////////////////////////////

	private EntryBuilderInput createInput() {
		EntryBuilderInput input;
		input = new EntryBuilderInput();
		necessarySetUp(input);
		defaultSettings(input);
		return input;
	}
	
	public void defaultSettings(EntryBuilderInput input) {
		input.setUpdated("2011-05-25T14:01:54Z");
		input.setGeoRSSeast("-115");
		input.setGeoRSSnorth("42");
		input.setGeoRSSsouth("40");
		input.setGeoRSSwest("-116");
		input.setLinks(Arrays.asList(new LinkBean("http://href1", "alternate", "text/html"), new LinkBean("http://href2", "http://esipfed.org/ns/discovery/1.1/data#", "application/xml")));
	}

	public void necessarySetUp(EntryBuilderInput input) {
		input.setId("http://myid.org");
		input.setTitle("Title");
		input.setSummary("summary");
		input.setAuthors(Arrays.asList(new AuthorBean("Author","http://colorado.edu/Author","author@colorado.edu")));
		input.setStartTime("2011-06-25T14:01:54Z");
		input.setEndTime("2011-07-25T11:01:54Z");
	}

	
};

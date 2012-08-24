package org.nsidc.feeds.validator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;
import org.nsidc.feeds.validator.FeedValidator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FeedValidatorTest {
	
	FeedValidator validator = null;
	FeedBuilderInput input = null;
	
	@Before
	public void setup() {
		FeedBuilderInput defaultFeedValues= new FeedBuilderInput();
		defaultFeedValues.setUpdated("1990-12-31T12:00:00Z");
		validator = new FeedValidator(defaultFeedValues);
	}
	
	@Test
	public void testFeedValidatorReturnTrueWhenFeedIsValid() {
		input = mock(FeedBuilderInput.class);
		when(input.getId()).thenReturn("http://myid.org");
		when(input.getTitle()).thenReturn("title");
		when(input.getUpdated()).thenReturn("1990-12-31T12:00:00.999Z");										     
		List<AuthorBean> authors = new ArrayList<AuthorBean>();
		authors.add(new AuthorBean("name1", "url1", "email1"));
		when(input.getAuthors()).thenReturn(authors);
		
		
		assertThat(validator.evaluate(input), is(true));
	}
	
	@Test
	public void testFeedValidatorReturnFalseWhenFeedIsNotValid() {
		input = mock(FeedBuilderInput.class);
		when(input.getId()).thenReturn(null);
		assertThat(validator.evaluate(input), is(false));
	}
	
	
	@Test
	public void test_validator_will_use_default_updated_if_input_updated_is_null() {
		FeedBuilderInput realInput = new FeedBuilderInput();
		validator.evaluate(realInput);
		String updated = realInput.getUpdated();		
		assertNotNull(updated);
		
	}
}

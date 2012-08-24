package org.nsidc.feeds.matchers;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class DateStringMatcherTest {
	
	private DateStringMatcher matcher;


	@Before 
	public void setup() {
		matcher = new DateStringMatcher();
	}

	@Test
	public void matcher_is_true_when_string_is_valid_iso_date() {
		assertThat("2009-10-10T01:01:01.000Z", matcher);	
	}

	@Test
	public void matcher_is_true_when_string_is_valid_date_only() {
		assertThat("2009-10-10", matcher);			
	}
	
	@Test
	public void matcher_is_false_when_string_is_null() {
		assertThat(null, not(matcher));		
	}
	
	@Test
	public void matcher_is_false_when_string_is_empty() {
		assertThat("", not(matcher));		
	}
	
	@Test
	public void matcher_is_false_when_string_is_invalid() {
		assertThat("2009-01-01a", not(matcher));		
	}

}

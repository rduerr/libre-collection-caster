package org.nsidc.feeds.validator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class GroupedMatcherTest {

	@Test
	public void test_that_group_matcher_returns_true() {

		// Arrange
		GroupedMatcher<Integer> groupMatch = new GroupedMatcher<Integer>(
				"a list", twelve(), even());

		// Act
		boolean match = groupMatch.matchesSafely(12);

		// Assert
		assertThat(match, is(true));
	}

	@Test
	public void test_that_group_matcher_returns_false() {

		// Arrange
		GroupedMatcher<Integer> groupMatch = new GroupedMatcher<Integer>(
				"twelve and even", twelve(), even());

		// Act
		boolean match = groupMatch.matchesSafely(13);

		// Assert
		assertThat(match, is(false));
	}

	@Test
	public void test_that_group_matcher_returns_false_when_matchers_differ() {

		// Arrange
		GroupedMatcher<Integer> groupMatch = new GroupedMatcher<Integer>(
				"twelve and odd", twelve(), odd());

		// Act
		boolean match = groupMatch.matchesSafely(13);

		// Assert
		assertThat(match, is(false));
	}
	
	@Test
	public void test_that_group_matcher_description_makes_sense() {

		// Arrange
		GroupedMatcher<Integer> groupMatch = new GroupedMatcher<Integer>(
				"twelve and odd", twelve(), odd());
		groupMatch.matchesSafely(13);
		StringDescription description = new StringDescription();
		
		// Act
		groupMatch.describeTo(description);
		String outputMessage = description.toString();
		
		// Assert
		assertThat(outputMessage, is("twelve and odd\nExpected: twelve\n     got: <13>\n"));
	}
	
	@Test
	public void test_that_group_matcher_description_makes_sense_for_multiple_failures() {

		// Arrange
		GroupedMatcher<Integer> groupMatch = new GroupedMatcher<Integer>(
				"twelve and even", twelve(), even());
		groupMatch.matchesSafely(13);
		StringDescription description = new StringDescription();
		
		// Act
		groupMatch.describeTo(description);
		String outputMessage = description.toString();
		
		// Assert
		assertThat(outputMessage, is("twelve and even\nExpected: twelve\n     got: <13>\n\nExpected: an even number\n     got: <13>\n"));
	}

	@Factory
	public static <T> Matcher<Integer> even() {
		return new TypeSafeMatcher<Integer>() {

			String description = "an even number";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Integer number) {
				return number % 2 == 0;
			}
		};

	}

	@Factory
	public static <T> Matcher<Integer> twelve() {
		return new TypeSafeMatcher<Integer>() {

			String description = "twelve";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Integer number) {
				return number == 12;
			}
		};

	}

	@Factory
	public static <T> Matcher<Integer> odd() {
		return new TypeSafeMatcher<Integer>() {

			String description = "an odd number";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Integer number) {
				return number % 2 == 1;
			}
		};
	}
}

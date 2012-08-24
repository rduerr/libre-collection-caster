package org.nsidc.feeds.validator;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;

public class GroupedMatcher<T> extends TypeSafeMatcher<T> {

	Description description;
	private List<Matcher<T>> matchers;

	public GroupedMatcher(String description, Matcher<T>... matchers) {
		this.description = new StringDescription();
		this.description.appendText(description);
		
		this.matchers = Arrays.asList(matchers);
	}

	public void describeTo(Description description) {
		description.appendText(this.description.toString());
	}

	@Override
	public boolean matchesSafely(T value) {
		boolean isValid = true;

		for (Matcher<T> matcher : matchers) {
			if (!match(value, matcher)) {				
				isValid = false;
			}
		}
		return isValid;
	}

	private boolean match(T actual, Matcher<T> matcher) {
		if (!matcher.matches(actual)) {			
			description.appendText("\nExpected: ");
			description.appendDescriptionOf(matcher);
			description.appendText("\n     got: ");
			description.appendValue(actual);
			description.appendText("\n");	
			return false;
		} else {
			return true;
		}
	}

}

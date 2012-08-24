package org.nsidc.feeds.matchers;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class AtomSummaryMatcher {

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputSummary() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "Entry input should have valid summary";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				boolean isValid = true;
		

				if (entry.getSummary() == null || entry.getSummary().isEmpty()) {
					description += " Summary tag is missing.";
					isValid = false;
				}

				return isValid;
			}
		};
	}
	
}

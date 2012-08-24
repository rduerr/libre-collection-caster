package org.nsidc.feeds.matchers;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomTitleMatcher {
	
	@Factory
	public static <T> Matcher<Feed> validFeedTitle() {
		return new TypeSafeMatcher<Feed>() {

			String description = "Atom Feed should have valid title";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Feed feed) {
				boolean isValid = true;
				
				if (feed.getTitle() == null || feed.getTitle().isEmpty()) {
					description += " Title tag is missing.";
					isValid = false;
				}

				return isValid;
			}
		};
	}
	
	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInputTitle() {
		return new TypeSafeMatcher<FeedBuilderInput>() {

			String description = "Feed input should have valid title";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(FeedBuilderInput feed) {
				boolean isValid = true;
		

				if (feed.getTitle() == null || feed.getTitle().isEmpty()) {
					description += " Title tag is missing.";
					isValid = false;
				}

				return isValid;
			}
		};
	}

	@Factory
	public static <T> Matcher<Entry> validEntryTitle() {
		return new TypeSafeMatcher<Entry>() {

			String description = "Atom Entry should have valid title";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;
				
				if (entry.getTitle() == null || entry.getTitle().isEmpty()) {
					description += " Title tag is missing.";
					isValid = false;
				}

				return isValid;
			}
		};

	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputTitle() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "Entry input should have valid title";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				boolean isValid = true;
		

				if (entry.getTitle() == null || entry.getTitle().isEmpty()) {
					description += " Title tag is missing.";
					isValid = false;
				}

				return isValid;
			}
		};
	}
	
}

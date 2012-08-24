package org.nsidc.feeds.matchers;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomAuthorMatcher {
	
	@Factory 
	public static <T> Matcher<Feed> validFeedAuthor() {
		return new TypeSafeMatcher<Feed>() {

			String description = "Atom Feed should contain valid author(s).";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Feed feed) {
				boolean isValid = true;
				if (feed.getAuthor() == null) {
					description += " Author tag is missing.";
					isValid = false;
				} else {
					if (feed.getAuthor().getName() == null || feed.getAuthor().getName().isEmpty()) {
						description += " Author tag is missing an author name.";
						return false;
					}
				}

				return isValid;
			}
		};
	}
	
	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInputAuthor() {
		return new TypeSafeMatcher<FeedBuilderInput>() {

			String description = "Feed input should contain at least one valid author.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(FeedBuilderInput input) {
				boolean isValid = true;
				if (input.getAuthors() == null) {
					description += " Author tag is missing.";
					isValid = false;
				} else {
					if (input.getAuthors().size() == 0) {
						description += " Author tag exists but does not have any authors.";
						isValid = false;
					} else {
						for (AuthorBean a : input.getAuthors()) {
							if (a.getName() == null || a.getName().isEmpty()) {
								description += " Author tag is missing an author name.";
								isValid = false;
							}
						}
					}
				}

				return isValid;
			}
		};
	}
	
	
	@Factory
	public static <T> Matcher<Entry> validEntryAuthor() {
		return new TypeSafeMatcher<Entry>() {

			String description = "Atom Entry should contain valid author(s).";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;
				if (entry.getAuthor() == null) {
					description += " Author tag is missing.";
					isValid = false;
				} else {
					if (entry.getAuthor().getName() == null || entry.getAuthor().getName().isEmpty() ) {
						description += " Author tag is missing an author name.";
						return false;
					}
				}

				return isValid;
			}
		};

	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputAuthor() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "EntryBuilderInput should contain at least one valid author.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				boolean isValid = true;
				if (entry.getAuthors() == null) {
					description += " Author tag is missing.";
					isValid = false;
				} else {
					if (entry.getAuthors().size() == 0) {
						description += " Author tag exists but does not have any authors.";
						isValid = false;
					} else {
						for (AuthorBean a : entry.getAuthors()) {
							if (a.getName() == null || a.getName().isEmpty() ) {
								description += " Author tag is missing an author name.";
								isValid = false;
							}
						}
					}
				}

				return isValid;
			}
		};
	}

}

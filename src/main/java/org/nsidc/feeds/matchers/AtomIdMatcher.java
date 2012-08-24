package org.nsidc.feeds.matchers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomIdMatcher {

	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInputId() {
		return new TypeSafeMatcher<FeedBuilderInput>() {

			String description = "Feed input should have valid ID. Atom ids must be a valid URI.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(FeedBuilderInput input) {
				return validURL(input.getId(), description);
			}

		};
	}

	@Factory
	public static <T> Matcher<Feed> validFeedId() {
		return new TypeSafeMatcher<Feed>() {

			String description = "Atom Feed should have valid ID. Atom ids must be a valid URI.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Feed feed) {
				return validURL(feed.getId(), description);
			}
		};
	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputId() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "Entry input should have valid ID.  Atom ids must be a valid URI.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				return validURL(entry.getId(), description);
			}
		};
	}

	@Factory
	public static <T> Matcher<Entry> validEntryId() {
		return new TypeSafeMatcher<Entry>() {

			String description = "Atom Entry should have valid ID.  Atom ids must be a valid URI.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				return validURL(entry.getId(), description);
			}

		};
	}

	protected static boolean validURL(IRI url, String description) {
		boolean isValid = true;
		if (url == null) {
			description += " Id tag is missing.";
			isValid = false;
		} else {
			try {
				isValid = validURL(url.toURI().toString(), description);
			} catch (URISyntaxException e) {
				description += " Id tag is is not valid URL. " + e.getMessage();
				isValid = false;
			}
		}
		return isValid;
	}

	protected static boolean validURL(String url, String description) {
		boolean isValid = true;
		if (url == null || url.toString().isEmpty()) {
			description += " Id tag is missing.";
			isValid = false;
		} else {
			try {
				URL tempURL = new URL(url);

			} catch (MalformedURLException e) {
				description += " Id tag is is not valid URL. " + e.getMessage();
				isValid = false;
			}
		}
		return isValid;
	}
}

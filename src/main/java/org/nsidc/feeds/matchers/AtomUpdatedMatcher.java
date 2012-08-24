package org.nsidc.feeds.matchers;

import java.util.regex.Pattern;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

public class AtomUpdatedMatcher {

	@Factory
	public static <T> Matcher<Feed> validFeedUpdated() {
		return new TypeSafeMatcher<Feed>() {

			String description = "Atom Feed should have valid updated date.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Feed feed) {
				boolean isValid = true;

				if (feed.getUpdated() == null) {
					description += " Updated tag is missing.";
					isValid = false;
				} 

				return isValid;
			}
		};

	}

	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInputUpdated() {
		return validFeedBuilderInputUpdated(null);
	}

	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInputUpdated(
			final FeedBuilderInput defaultInput) {
		return new TypeSafeMatcher<FeedBuilderInput>() {
			FeedBuilderInput defaultFeedInput = defaultInput;

			String description = "Feed input should have valid updated date.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(FeedBuilderInput input) {
				boolean isValid = true;

				if (input.getUpdated() == null || input.getUpdated().isEmpty()) {

					if (defaultFeedInput == null) {
						isValid = false;
						description += " The input does not have a valid date and the default input is null";
					} else {
						if (defaultFeedInput.getUpdated() == null
								|| defaultFeedInput.getUpdated().length() == 0) {
							isValid = false;
							description += " Neither the input nor the default have a valid updated date.";
						} else {
							input.setUpdated(defaultFeedInput.getUpdated());
						}
					}
				} else if (!isValidTimestamp(input.getUpdated())) {
					description += " Updated tag is not valid ISO timestamp.";
					isValid = false;
				}

				return isValid;
			}
		};
	}

	@Factory
	public static <T> Matcher<Entry> validEntryUpdated() {
		return new TypeSafeMatcher<Entry>() {

			String description = "Atom Entry should have valid updated date.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;

				if (entry.getUpdated() == null) {
					description += " Updated tag is missing.";
					isValid = false;
				} 
				return isValid;
			}
		};

	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputUpdated() {
		return validEntryBuilderInputUpdated(null);
	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputUpdated(
			final EntryBuilderInput defaultInput) {
		return new TypeSafeMatcher<EntryBuilderInput>() {
			EntryBuilderInput defaultvalues = defaultInput;
			String description = "EntryBuilderInput or defaultInput should have valid updated date.";

			@Override
			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(EntryBuilderInput item) {
				boolean isValid = true;

				if (item.getUpdated() == null || item.getUpdated().isEmpty()) {

					if (defaultvalues == null) {
						isValid = false;
						description += " The input does not have a valid date and the default input is null";
					} else {
						if (defaultInput.getUpdated() == null) {
							isValid = false;
							description += " Neither the input nor the default have a valid updated date.";
						} else {
							item.setUpdated(defaultInput.getUpdated());
						}
					}
				} else if (!isValidTimestamp(item.getUpdated())) {
					description += " Updated tag is not valid ISO timestamp.";
					isValid = false;
				} 

				return isValid;
			}

		};
	}

	protected static boolean isValidTimestamp(String queryTime) {
		Pattern p = Pattern
				.compile("^(\\d{4})((-)(0[1-9]|1[0-2])((-)([12]\\d|0[1-9]|3[01])((T?)([01]\\d|2[0-3])(:?)([0-5]\\d)((:[0-5]\\d)(\\.|,)?(\\d+)?)?([zZ]|((-|\\+))(0\\d|1[12])(:[0-5]\\d)?(:[0-5]\\d)?)+)+)+)+$");
		java.util.regex.Matcher m = p.matcher(queryTime);
		if (m.find()) {
			return true;
		}
		return false;
	}
}

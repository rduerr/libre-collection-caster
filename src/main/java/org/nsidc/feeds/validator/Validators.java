package org.nsidc.feeds.validator;

import static org.nsidc.feeds.matchers.AtomAuthorMatcher.*;
import static org.nsidc.feeds.matchers.AtomIdMatcher.*;
import static org.nsidc.feeds.matchers.AtomTitleMatcher.*;
import static org.nsidc.feeds.matchers.AtomSummaryMatcher.*;
import static org.nsidc.feeds.matchers.AtomUpdatedMatcher.*;
import static org.nsidc.feeds.matchers.EsipBboxMatcher.*;
import static org.nsidc.feeds.matchers.EsipStartDateMatcher.*;
import static org.nsidc.feeds.matchers.EsipEndDateMatcher.*;
import static org.nsidc.feeds.matchers.EsipLinkMatcher.*;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

@SuppressWarnings("unchecked")
public class Validators {

	@Factory
	public static <T> Matcher<Feed> validAtomFeed() {
		return new GroupedMatcher<Feed>("a valid Atom Feed.",
				validFeedAuthor(), validFeedTitle(), validFeedId(),
				validFeedUpdated());
	}

	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInput() {
		return validFeedBuilderInput(null);
	}

	@Factory
	public static <T> Matcher<FeedBuilderInput> validFeedBuilderInput(
			FeedBuilderInput defaultFeedInput) {
		return new GroupedMatcher<FeedBuilderInput>("a valid feed input.",
				validFeedBuilderInputAuthor(), validFeedBuilderInputTitle(),
				validFeedBuilderInputId(),
				validFeedBuilderInputUpdated(defaultFeedInput));
	}

	@Factory
	public static <T> Matcher<Entry> validAtomEntry() {
		return new GroupedMatcher<Entry>("a valid Atom Entry.",
				validEntryAuthor(), validEntryTitle(), validEntryId(),
				validEntryUpdated());
	}

	@Factory
	public static <T> Matcher<Entry> validESIPEntry() {
		return new GroupedMatcher<Entry>("a valid ESIP Entry.",
				validAtomEntry(), validEntryBbox(), validEntryStart(),
				validEntryEnd(), validEntryLinks());
	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInput() {
		return validEntryBuilderInput(null);
	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInput(
			EntryBuilderInput defaultEntryValues) {
		return new GroupedMatcher<EntryBuilderInput>("a valid entry input.",
				validEntryBuilderInputAuthor(), validEntryBuilderInputTitle(),
				validEntryBuilderInputId(), validEntryBuilderInputStart(),
				validEntryBuilderInputEnd(),
				validEntryBuilderInputBbox(defaultEntryValues),
				validEntryBuilderInputUpdated(defaultEntryValues),
				validEntryBuilderInputSummary(), validEntryBuilderInputLinks());
	}
}

package org.nsidc.feeds.matchers;

import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EsipEndDateMatcher {

	@Factory
	public static <T> Matcher<Entry> validEntryEnd() {
		return new TypeSafeMatcher<Entry>() {

			String description = "ESIP Entry should have valid end date.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;
				QName end = new QName("http://a9.com/-/opensearch/extensions/time/1.0/", "end", "time");
				Element e = entry.getExtension(end);								
				if (e == null ) {
					description += " End date is missing.";
					isValid = false;
				}
				else {
					isValid = new DateStringMatcher().matchesSafely(e.getText());
				}
				return isValid;
			}
			
		};

	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputEnd() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "Entry input should have valid end date.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				return new DateStringMatcher().matchesSafely(entry.getEndTime());
				
			}
		};
	}
}

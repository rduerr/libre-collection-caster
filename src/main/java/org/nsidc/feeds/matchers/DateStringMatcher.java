package org.nsidc.feeds.matchers;

import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class DateStringMatcher extends TypeSafeMatcher<String> 
{
	String description = "valid date.";

	public void describeTo(Description description) {
		description.appendText(this.description);
	}

	@Override
	public boolean matchesSafely(String dateString) {
		boolean isValid = true;
		if (dateString == null || dateString.isEmpty() ) {
			description += " empty date string.";
			isValid = false;
		}
		else if (!isValidDate( dateString )) {
			description += " not a valid ISO date (1950-01-01T23:12:15.987-06:00 or 1950-01-01).";
			isValid = false;
		}
		return isValid;
	}
	
	private boolean isValidDate(String queryTime) {
		Pattern p = Pattern.compile("^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$");
		
		java.util.regex.Matcher m = p.matcher(queryTime);
		if(m.find()) {
			return true;
		}
		return false;
	}
}
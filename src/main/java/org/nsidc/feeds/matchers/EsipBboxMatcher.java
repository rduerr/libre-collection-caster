package org.nsidc.feeds.matchers;

import javax.xml.namespace.QName;

import org.apache.abdera.model.Entry;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EsipBboxMatcher {
	
	@Factory
	public static <T> Matcher<Entry> validEntryBbox() {
		return new TypeSafeMatcher<Entry>() {

			String description = "ESIP Entry should have valid bounding box.";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;
				QName boxName = new QName("http://www.georss.org/georss", "box");

				if (entry.getExtension(boxName) == null) {
					description += " Bounding box is missing.";
					isValid = false;
				}
				
				return isValid;
			}
		};

	}

	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputBbox() {
		return validEntryBuilderInputBbox(null);

	}

	
	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputBbox(final EntryBuilderInput defaultInput) {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "EntryBuilderInput should have valid bounding box.";
				
			@Override
			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				boolean isValid = true;
				
				if (entry.getGeoRSSeast() != null && isOutOfBounds(entry.getGeoRSSeast(), "east")) {
					isValid = false;
				}
				
				if (entry.getGeoRSSeast() == null) {
					if (defaultInput == null || defaultInput.getGeoRSSeast() == null){
						description += " East coordinate is missing or invalid in both input and default1.";
						isValid = false;
					} else if (isOutOfBounds(defaultInput.getGeoRSSeast(), "east")){
						description += " East coordinate is missing or invalid in both input and default2.";
						isValid = false;
					}
					else {
						entry.setGeoRSSeast(defaultInput.getGeoRSSeast());
					}
				}
				
				if (entry.getGeoRSSnorth() != null && isOutOfBounds(entry.getGeoRSSnorth(), "north")) {
					isValid = false;
				}
				
				if (entry.getGeoRSSnorth() == null) {
					if (defaultInput == null || defaultInput.getGeoRSSnorth() == null){
						description += " North coordinate is missing or invalid in both input and default.";
						isValid = false;
					} else if (isOutOfBounds(defaultInput.getGeoRSSnorth(), "north")){
						description += " North coordinate is missing or invalid in both input and default.";
						isValid = false;
					}
					else {
						entry.setGeoRSSnorth(defaultInput.getGeoRSSnorth());
					}
				}
				
				if (entry.getGeoRSSsouth() != null && isOutOfBounds(entry.getGeoRSSsouth(), "south")) {
					isValid = false;
				}
				
				if (entry.getGeoRSSsouth() == null) {
					if (defaultInput == null || defaultInput.getGeoRSSsouth() == null){
						description += " South coordinate is missing or invalid in both input and default.";
						isValid = false;
					} else if (isOutOfBounds(defaultInput.getGeoRSSsouth(), "south")){
						description += " South coordinate is missing or invalid in both input and default.";
						isValid = false;
					}
					else {
						entry.setGeoRSSsouth(defaultInput.getGeoRSSsouth());
					}
				}
				
				if (entry.getGeoRSSwest() != null && isOutOfBounds(entry.getGeoRSSwest(), "west")) {
					isValid = false;
				}
				
				if (entry.getGeoRSSwest() == null) {
					if (defaultInput == null || defaultInput.getGeoRSSwest() == null){
						description += " West coordinate is missing or invalid in both input and default.";
						isValid = false;
					} else if (isOutOfBounds(defaultInput.getGeoRSSwest(), "west")){
						description += " West coordinate is missing or invalid in both input and default.";
						isValid = false;
					}
					else {
						entry.setGeoRSSwest(defaultInput.getGeoRSSwest());
					}
				}
				

				return isValid;
			}

			private boolean isOutOfBounds(String coordinate, String direction) {
				int min = 0, max = 0;
				boolean isValid = false;
				
				if (direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("west")) {
					min = -180;
					max = 180;
				} else if (direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("south")) {
					min = -90;
					max = 90;
				} else {
					description += " invalid direction";
					return true;
				}
				
				try{
					int coord = Integer.parseInt(coordinate);	

					if (coord < min  || coord > max ){
						description += " " + direction + " coordinate is out of bounds -- it is " + coord;
						isValid = true;
					}
				}catch(Exception e){
					description += " " + direction + " coordinate is not a valid numeric value.";
					isValid = true;
				}

				return isValid;
			}
		};
	}

}

package org.nsidc.feeds.matchers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.LinkBean;

public class EsipLinkMatcher {
	
	@Factory
	public static <T> Matcher<Entry> validEntryLinks() {
		return new TypeSafeMatcher<Entry>() {

			String description = "Atom Entry should have valid links";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}

			@Override
			public boolean matchesSafely(Entry entry) {
				boolean isValid = true;		
				boolean foundESIPLink = false;
				boolean foundAlternateLink = false;
				
				List<Link> links = entry.getLinks();
				if (links == null || links.isEmpty()) {
					description += " Links tag is missing.";
					isValid = false;
				} else {
					for ( Link link: links ) {
						if ( link.getHref() == null ) {
							isValid = false;
							description += " The href attribute is empty.";
						} else {
							isValid = validURL(link.getHref().toString(), " The href <" + link.getHref() + " is invald.");
						}
						if ( isEmpty(link.getRel()) ) {
							isValid = false;
							description += " The rel attribute is missing.";
						} else {
							if ( link.getRel().contains("esipfed.org")) {
								foundESIPLink = true;
							}
							if ( link.getRel().equals("alternate")) {
								foundAlternateLink = true;
							}
						}
					}
				}
				
				if ( !foundESIPLink ) {
					isValid = false;
					description += " At least one link must be supplied with an ESIP Discover Cast rel attribute.";
				}
				if ( !foundAlternateLink ) {
					isValid = false;
					description += " At least one link must be supplied a rel attribute of \"alternate\".";
				}

				return isValid;
			}
		};

	}
	
	@Factory
	public static <T> Matcher<EntryBuilderInput> validEntryBuilderInputLinks() {
		return new TypeSafeMatcher<EntryBuilderInput>() {

			String description = "EntryBuilderInput should have valid links";

			public void describeTo(Description description) {
				description.appendText(this.description);
			}
			
			@Override
			public boolean matchesSafely(EntryBuilderInput entry) {
				boolean isValid = true;
				boolean foundESIPLink = false;
				boolean foundAlternateLink = false;
				
				List<LinkBean> links = entry.getLinks();
				if (links == null || links.isEmpty() ) {
					description += " Links tag is missing.";
					isValid = false;
				} else {
					for ( LinkBean link: links ) {
						if ( link.getHref() == null ) {
							isValid = false;
							description += " The href attribute is empty.";
						} else {
							isValid = validURL(link.getHref().toString(), " The href <" + link.getHref() + " is invald.");							
						}
						if ( isEmpty(link.getRel()) ) {
							isValid = false;
							description += " The rel attribute is missing.";
						} else {
							if ( link.getRel().contains("esipfed.org")) {
								foundESIPLink = true;
							}
							if ( link.getRel().equals("alternate")) {
								foundAlternateLink = true;
							}
						}
					}
				}

				if ( !foundESIPLink ) {
					isValid = false;
					description += " At least one link must be supplied with an ESIP Discover Cast rel attribute.";
				}
				if ( !foundAlternateLink ) {
					isValid = false;
					description += " At least one link must be supplied a rel attribute of \"alternate\".";
				}
				
				return isValid; 
			}
		};
	}
	
	protected static boolean isEmpty(String value ) {
		return value == null || value.isEmpty();
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

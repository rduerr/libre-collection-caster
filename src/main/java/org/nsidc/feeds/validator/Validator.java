package org.nsidc.feeds.validator;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public abstract class Validator<T>{

	abstract boolean evaluate(T input);
	
	private Description description;	
	protected T defaultValue;

	public Validator(T defaultValue) {
		description= new StringDescription();
		this.defaultValue= defaultValue;
	}

	public Validator() {
		description= new StringDescription();		
	}
		
	public String getMessage() {
		return description.toString();
	}

	protected boolean match(T actual, Matcher<T> matcher) {
		if (!matcher.matches(actual)) {
			description.appendText("\nExpected: ");
			description.appendDescriptionOf(matcher);						
			return false;
		} else {
			return true;
		}
	}

}

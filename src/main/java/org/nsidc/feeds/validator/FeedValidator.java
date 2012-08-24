package org.nsidc.feeds.validator;

import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;
import static org.nsidc.feeds.validator.Validators.validFeedBuilderInput;

public class FeedValidator extends Validator<FeedBuilderInput>{
	
	public FeedValidator() {
		super();
	}
	
	public FeedValidator(FeedBuilderInput defaultFeed) {
		super(defaultFeed);
	}	
	
	public boolean evaluate(FeedBuilderInput input) {
		return match(input, validFeedBuilderInput(defaultValue));		
	}
	
}

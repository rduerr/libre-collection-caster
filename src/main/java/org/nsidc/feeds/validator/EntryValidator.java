package org.nsidc.feeds.validator;

import static org.nsidc.feeds.validator.Validators.*;

import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

public class EntryValidator extends Validator<EntryBuilderInput> {

	public EntryValidator() {
		super();
	}
	
	public EntryValidator(EntryBuilderInput defaultEntry) {
		super(defaultEntry);
	}	

	@Override
	public boolean evaluate(EntryBuilderInput inputEntry) {
		return match(inputEntry, validEntryBuilderInput(defaultValue));		
	}

}

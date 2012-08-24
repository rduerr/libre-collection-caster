package org.nsidc.feeds.collection_caster_services;

public class InvalidParamsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidParamsException(Exception error) {
		super(error);
	}
	
	public InvalidParamsException(String error) {
		super(error);
	}

}

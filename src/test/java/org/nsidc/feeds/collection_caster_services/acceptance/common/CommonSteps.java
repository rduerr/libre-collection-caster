package org.nsidc.feeds.collection_caster_services.acceptance.common;

import org.apache.abdera.model.Feed;
import org.nsidc.feeds.collection_caster_services.CollectionCasterServiceImpl;
import org.nsidc.feeds.collection_caster_services.acceptance.EnvironmentSpecificSteps;

import cucumber.api.java.Before;


public class CommonSteps {

	private CollectionCasterServiceImpl service;
	private String serviceResponse;
	private EnvironmentSpecificSteps environmentSteps;
	private Feed responseFeed;
	
	public CommonSteps(EnvironmentSpecificSteps environmentSteps) {
		this.environmentSteps = environmentSteps;	
	}
	
	@Before
	public void setup() {
		this.service = new CollectionCasterServiceImpl();	
	}

	public CollectionCasterServiceImpl getService() {
		return service;
	}

	public void setService(CollectionCasterServiceImpl service) {
		this.service = service;
	}

	public EnvironmentSpecificSteps getEnvironmentSteps() {
		return environmentSteps;
	}

	public void setEnvironmentSteps(EnvironmentSpecificSteps environmentSteps) {
		this.environmentSteps = environmentSteps;
	}

	public String getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public Feed getResponseFeed() {
		return responseFeed;
	}

	public void setResponseFeed(Feed responseFeed) {
		this.responseFeed = responseFeed;
	}
}

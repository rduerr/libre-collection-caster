package org.nsidc.feeds.collection_caster_services.acceptance.web;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.abdera.model.Entry;
import org.apache.http.client.ClientProtocolException;
import org.nsidc.feeds.collection_caster_services.CollectionCasterService;
import org.nsidc.feeds.collection_caster_services.StringToFeedConverter;
import org.nsidc.feeds.collection_caster_services.acceptance.EnvironmentSpecificSteps;
import org.nsidc.feeds.collection_caster_services.acceptance.HttpResourceHelper;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class WebEnvironmentSteps implements EnvironmentSpecificSteps {

	private HttpResourceHelper httpHelper;
	private String inputFileContent;
	private String inputFileContentType;
	private Entry entry;
	
	@Before
	public void setup() {
		httpHelper = new HttpResourceHelper();
	}
	
	@Override
	public HttpResourceHelper getHttpResourceHelper() {
		return httpHelper;
	}
	
	@Given("^an execution environment$")
	public void given_an_execution_environment() throws IOException {		
		httpHelper.setServerRoot(System.getProperty("test.env.host"));
	}
	
	@When("^I post to the casting service at (.*)$")
	public void when_I_post_to_the_casting_service(String resource) throws URISyntaxException,
			ClientProtocolException, IOException {
		
		httpHelper.post("/" + CollectionCasterService.SERVICE_VERSION_NUMBER + resource, inputFileContent, inputFileContentType);
		if ( httpHelper.getStatus() == 200 ) {
			StringToFeedConverter converter = new StringToFeedConverter();
			entry = converter.convertToEntry(httpHelper.getBody());
		} 		
	}

	public String getInputFileContent() {
		return inputFileContent;
	}

	public void setInputFileContent(String inputFileContent) {
		this.inputFileContent = inputFileContent;
	}

	public String getInputFileContentType() {
		return inputFileContentType;
	}

	public void setInputFileContentType(String inputFileContentType) {
		this.inputFileContentType = inputFileContentType;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}



}

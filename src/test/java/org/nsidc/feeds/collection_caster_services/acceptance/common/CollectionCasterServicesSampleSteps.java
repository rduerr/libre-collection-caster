package org.nsidc.feeds.collection_caster_services.acceptance.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.abdera.model.Feed;
import org.apache.http.client.ClientProtocolException;
import org.nsidc.feeds.collection_caster_services.CollectionCasterService;
import org.nsidc.feeds.collection_caster_services.StringToFeedConverter;
import org.nsidc.feeds.collection_caster_services.acceptance.EnvironmentSpecificSteps;
import org.nsidc.feeds.validator.Validators;

import static org.nsidc.feeds.validator.Validators.*;
import cuke4duke.annotation.I18n.EN.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CollectionCasterServicesSampleSteps {
	private String pageContent;
	private CommonSteps steps;
	private EnvironmentSpecificSteps environmentSteps;
	
	public CollectionCasterServicesSampleSteps(CommonSteps commonSteps) {
		environmentSteps = commonSteps.getEnvironmentSteps();		
		this.steps = commonSteps;
	}

	@When("^I get the sample page$")
	public void iGetTheSamplePage() {
		String exampleCollection = steps.getService().example();
		Feed feed = new StringToFeedConverter().convertToFeed(exampleCollection);
		steps.setServiceResponse(exampleCollection);
		steps.setResponseFeed(feed);
	}

	@When("^I get the help page$")
	public void when_I_get_the_help_page() {
		pageContent = steps.getService().help();
	}

	@When("^I access (.*)$")
	public void when_I_access(String resource) throws URISyntaxException,
			ClientProtocolException, IOException {
		environmentSteps.getHttpResourceHelper().accessResource("/" + CollectionCasterService.SERVICE_VERSION_NUMBER + resource);
	}

	@Then("^a valid ESIP XML document is returned$")
	public void then_a_Valid_ESIP_XML_Document_Is_Returned() throws FileNotFoundException {
		assertThat(steps.getResponseFeed(), is(validAtomFeed()));		
	}

	@Then("^the document will contain a link to the ESIP specification$")	
	public void then_theDocumentWillContainALinkToTheESIPSpecification() {
		assertThat(steps.getServiceResponse(), containsString("http://wiki.esipfed.org"));
	}

	@Then("^every element in the document will have a descriptive comment$")
	public void then_everyElementnTheDocumentWillHaveADescriptiveComment() {
		// This is a non-functional feature. This test only checks
		// to confirm that the XML contains at least one comment.
		assertThat(steps.getServiceResponse(),containsString("<!--"));
	}

	@Then("^the document will contain how to get to the sample page$")
	public void then_the_document_will_contain_how_to_get_to_the_sample_page()
			throws IOException {
		assertThat(pageContent, 
				containsString("example ESIP-compliant XML"));
	}

	@Then("^the document will contain information about what ESIP is$")
	public void then_the_document_will_contain_information_about_what_ESIP_is() {
		assertThat(pageContent,
				containsString("More information on ESIP can be found"));
	}

	@Then("^the TEMP response code will be a 200$")
	public void then_the_TEMP_response_code_will_be_a_200() {
		assertThat(environmentSteps.getHttpResourceHelper().getStatus(), is(equalTo(200)));
	}
}

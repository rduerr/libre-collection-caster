package org.nsidc.feeds.collection_caster_services.smoketest;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SmokeTestSteps {
	
	private org.nsidc.feeds.collection_caster_services.acceptance.HttpResourceHelper httpResourceHelper; 
	
	@Before
	public void setUp() {
		this.httpResourceHelper = new org.nsidc.feeds.collection_caster_services.acceptance.HttpResourceHelper();
	}
	
	@Given("^the server is (.*)$")
	public void given_the_server_name(String serverName) {
		httpResourceHelper.setServerRoot(serverName);
	}

	@When("^I download the (.*) page$")
	public void when_I_download_the_help_page(String page)
			throws URISyntaxException, ClientProtocolException, IOException {
		httpResourceHelper.accessResource(page);
	}

	@Then("^the response code will be a ([1-5]\\d{2})$")
	public void then_the_response_code_will_be(String responseCode) {
		Integer code = Integer.parseInt(responseCode);
		assertThat(httpResourceHelper.getStatus(), is(code));
	}

	@Then("^the content length will be greater than zero$")
	public void then_the_content_length_will_be_greater_than_zero() throws UnsupportedEncodingException, IllegalStateException, IOException {
		assertThat(httpResourceHelper.getBody().length(), is(greaterThan(0)));
	}
}

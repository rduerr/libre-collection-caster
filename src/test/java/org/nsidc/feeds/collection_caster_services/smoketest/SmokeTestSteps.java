package org.nsidc.feeds.collection_caster_services.smoketest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import cuke4duke.annotation.*;
import cuke4duke.annotation.I18n.EN.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

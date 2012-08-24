package org.nsidc.feeds.collection_caster_services.acceptance.common;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.nsidc.feeds.validator.Validators.validAtomFeed;
import static org.nsidc.feeds.validator.Validators.validESIPEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.log4j.Logger;
import org.nsidc.feeds.collection_caster_services.InvalidParamsException;
import org.nsidc.feeds.collection_caster_services.acceptance.HttpResourceHelper;
import org.nsidc.feeds.collection_caster_services.bean.AuthorBean;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.LinkBean;

import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;

public class CollectionCasterServicesSteps {
	private UriInfo uriInfo;
	private HttpServletRequest request;
	private CommonSteps steps;
	private EntryBuilderInput inputParams;
	private FeedBuilderInput feedParams;

	private static final Logger logger = Logger
			.getLogger(CollectionCasterServicesSteps.class);

	public CollectionCasterServicesSteps(CommonSteps commonSteps) {
		this.steps = commonSteps;
	}

	@Given("^metadata for a collection of data with no input parameters$")
	public void given_No_Input_Parameters() throws WebApplicationException,
			InvalidParamsException {
		mockParameters();
		inputParams = new EntryBuilderInput();
	}

	@Given("^metadata for a collection of data with some of the required input parameters$")
	public void given_Some_Of_The_Required_Input_Parameters()
			throws InvalidParamsException {
		mockParameters();
		inputParams = new EntryBuilderInput();
	}

	@Given("^metadata for a collection of data with the minimum set of required input fields$")
	public void given_The_Minimum_Set_Of_Required_Input_Fields()
			throws InvalidParamsException {
		mockRequiredParameters();
	}

	@Given("^metadata announcing a set of one or more data collections$")
	public void given_metadata_Announcing_A_Set_Of_One_Or_More_Data_Collections() {
		mockFeedRequiredParameters();
	}

	@Given("^a file in (.*) format$")
	public void given_a_file_in_format(String format) {
		String content = "", contentType = "";
		if (format.equalsIgnoreCase("xml")) {
			content = INPUT_XML;
			contentType = "application/xml";
		}
		if (format.equalsIgnoreCase("json")) {
			content = INPUT_JSON;
			contentType = "application/json";
		}
		steps.getEnvironmentSteps().setInputFileContent(content);
		steps.getEnvironmentSteps().setInputFileContentType(contentType);
	}

	@Given("^an updated field is specified$")
	public void given_An_Updated_Field_Is_Specified() {
		inputParams.setUpdated("2011-05-25T14:01:54Z");
	}

	@Given("^a bounding box is specified$")
	public void given_a_bounding_box_Is_Specified() {
		inputParams.setGeoRSSeast("-115");
		inputParams.setGeoRSSnorth("42");
		inputParams.setGeoRSSsouth("40");
		inputParams.setGeoRSSwest("-116");
	}
	
	@When("^the casting service is called$")
	public void when_The_Casting_Service_Is_Called() {
		try {
			Entry response = steps.getService().castFromBinding(inputParams,
					uriInfo, request);
			steps.getEnvironmentSteps().setEntry(response);
		} catch (WebApplicationException e) {
			steps.setServiceResponse(e.getResponse().getEntity().toString());
		}

	}

	@When("^the feed service is called$")
	public void when_the_Feed_Service_Is_Called() {
		try {
			Feed feed = steps.getService().castFeedFromBinding(feedParams,
					uriInfo, request);
			steps.setResponseFeed(feed);
		} catch (Exception e) {
			steps.setServiceResponse(e.getMessage());
		}
	}

	@When("^the feed header service is called$")
	public void when_the_Feed_Header_Service_Is_Called() {
		try {
			String header = steps.getService().castFeedHeaderFromBinding(
					feedParams, uriInfo, request);
			steps.setServiceResponse(header);
		} catch (Exception e) {
			steps.setServiceResponse(e.getMessage());
		}
	}

	@When("^the feed footer service is called$")
	public void when_the_Feed_Footer_Service_Is_Called() {
		try {
			String footer = steps.getService().castFeedFooter();
			steps.setServiceResponse(footer);
		} catch (Exception e) {
			steps.setServiceResponse(e.getMessage());
		}
	}

	@Then("^a valid ESIP XML Atom Entry is returned$")
	public void then_a_valid_esip_xml_atom_entry_is_returned() {
		HttpResourceHelper httpHelper = steps.getEnvironmentSteps()
				.getHttpResourceHelper();
		if (httpHelper != null && httpHelper.getStatus() != null
				&& httpHelper.getStatus() != 200) {
			fail(httpHelper.getBody());
		} else {
			Entry entry = steps.getEnvironmentSteps().getEntry();
			assertThat(entry, is(validESIPEntry()));
		}
	}

	@Then("^the user should be given a meaningful error with a link to the help page$")
	public void then_The_User_Should_Be_Given_A_Meaningful_Error_With_A_Link_To_The_Help_Page() {
		String error = steps.getServiceResponse();
		assertThat(error, containsString("Expected"));
		assertThat(error, containsString("/help"));
	}

	@Then("^a valid Atom feed is returned with no entries$")
	public void then_a_Valid_ESIP_XML_Shell_Feed_Is_Returned() {
		assertThat(steps.getResponseFeed(), is(validAtomFeed()));
	}

	@Then("^a valid Atom header is returned with no entries$")
	public void then_a_Valid_ESIP_XML_Shell_Header_Is_Returned() {
		String response = steps.getServiceResponse();
		assertThat(response,
				containsString("<feed xmlns=\"http://www.w3.org/2005/Atom\">"));
		assertThat(response, endsWith(">"));
		assertThat(response, not(endsWith("</feed>")));
		assertThat(response, containsString("<?xml version"));
	}

	@Then("^a valid Atom footer is returned with no entries$")
	public void then_a_Valid_ESIP_XML_Shell_Footer_Is_Returned() {
		assertThat(steps.getServiceResponse(), containsString("</feed>"));
	}

	private void mockFeedRequiredParameters() {
		mockParameters();
		feedParams = new FeedBuilderInput();
		feedParams.setId("http://myurl.org");
		feedParams.setTitle("FeedTitle");
		feedParams.setUpdated("2011-05-25T14:01:54Z");
		feedParams.setAuthors(Arrays.asList(new AuthorBean("FeedAuthor",
				"http://colorado.edu/Author", "author@colorado.edu")));
	}

	private void mockRequiredParameters() {
		mockParameters();

		inputParams = new EntryBuilderInput();
		inputParams.setId("http://myurl.org");
		inputParams.setTitle("Title");
		// Do not set updated here
		inputParams.setSummary("summary");
		inputParams.setAuthors(Arrays.asList(new AuthorBean("Author",
				"http://colorado.edu/Author", "author@colorado.edu")));
		inputParams.setStartTime("2011-06-25T14:01:54Z");
		inputParams.setEndTime("2011-07-25T11:01:54Z");
		// do not set geo bounds
		List<LinkBean> links = new ArrayList<LinkBean>(); 
		links.add(new LinkBean("http://href1", "alternate", "text/html"));
		links.add(new LinkBean("http://href2", "http://esipfed.org/ns/discovery/1.1/data#", "application/xml"));
		inputParams.setLinks(links);
	}

	private void mockParameters() {
		uriInfo = mock(UriInfo.class);
		request = mock(HttpServletRequest.class);

		when(request.getRequestURL()).thenReturn(
				new StringBuffer("http://myurl.org/test"));
	}

	private static final String INPUT_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<entry><id>http://myurl.org</id><title>Important research in speech "
			+ "development</title><authors><name>Miao Liu</name></authors>"
			+ "<startTime>2009-01-01T01:01:01.999Z</startTime><endTime>2009-01-02"
			+ "</endTime><summary>Summary</summary><links><href>http://url.com</href>"
			+ "<rel>alternate</rel><type>text/html</type></links><links><href>http://"
			+ "url.com</href><rel>http://esipfed.org/ns/discovery/1.1/data#</rel><type>text/html</type>"
			+ "</links></entry>"

	;

	private static final String INPUT_JSON = ""
			+ "{entry:{id: \"http://myurl.org\", title:\"Important research in speech development\", authors:[{name:\"Miao Liu\"}],startTime:\"2009-01-01T01:01:01.999Z\",endTime:\"2009-01-02\",summary:\"Summary\",links:[{href:\"http://myurl.org\",rel=\"alternate\",type=\"text/html\"},{href:\"http://myurl.org\",rel=\"http://esipfed.org/ns/discovery/1.1/data#\",type=\"text/html\"}]}}";

}

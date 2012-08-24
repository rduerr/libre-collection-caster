package org.nsidc.feeds.collection_caster_services.acceptance.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import org.apache.abdera.model.Entry;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.nsidc.feeds.collection_caster_services.CollectionCasterServiceImpl;
import org.nsidc.feeds.collection_caster_services.acceptance.EnvironmentSpecificSteps;
import org.nsidc.feeds.collection_caster_services.acceptance.HttpResourceHelper;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;

public class MockedEnvironmentSteps implements EnvironmentSpecificSteps {

	private CollectionCasterServiceImpl service;
	private HttpResourceHelper httpHelper;
	private String inputFileContent;
	private String inputFileContentType;
	private Entry entry;

	@Before
	public void setup() {
		this.service = new CollectionCasterServiceImpl();
	}

	@Override
	public HttpResourceHelper getHttpResourceHelper() {
		return httpHelper;
	}

	@Given("^an execution environment$")
	public void given_an_execution_environment() {
		httpHelper = mock(HttpResourceHelper.class);
		when(httpHelper.getStatus()).thenReturn(200);
		when(httpHelper.getBody()).thenReturn("");
	}

	@When("^I post to the casting service at (.*)$")
	public void when_I_post_to_the_casting_service(String resource)
			throws Exception {
		try {
			UriInfo uriInfo = mock(UriInfo.class);
			HttpServletRequest request = mock(HttpServletRequest.class);

			EntryBuilderInput entryBuilderInput = unmarshallInputFileContent();
			entry = service.castFromBinding(entryBuilderInput, uriInfo, request);

		} catch (WebApplicationException e) {
			// TODO steps.setServiceResponse(e.getMessage());
		}
	}

	private EntryBuilderInput unmarshallInputFileContent() throws Exception {
		JAXBContext jc = JAXBContext.newInstance(EntryBuilderInput.class);
		Unmarshaller u = jc.createUnmarshaller();
		EntryBuilderInput entryInput = null;
		
		if (inputFileContentType.contains("json")) {
			JSONObject obj = new JSONObject(inputFileContent);
			Configuration config = new Configuration();
			
			MappedNamespaceConvention con = new MappedNamespaceConvention(
					config);
			XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(obj,
					con);
			
			entryInput = (EntryBuilderInput) u.unmarshal(xmlStreamReader);
		} else {
			InputStream is = new ByteArrayInputStream(inputFileContent.getBytes());
			entryInput = (EntryBuilderInput) u.unmarshal(is);
		}

		return entryInput;
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

package org.nsidc.feeds.collection_caster_services.acceptance;

import java.io.IOException;

import org.apache.abdera.model.Entry;

public interface EnvironmentSpecificSteps {

	HttpResourceHelper getHttpResourceHelper();
	public void given_an_execution_environment() throws IOException;
	public void when_I_post_to_the_casting_service(String resource) throws Exception;
	void setInputFileContent(String content);
	void setInputFileContentType(String contentType);
	public Entry getEntry();
	public void setEntry(Entry entry);
}

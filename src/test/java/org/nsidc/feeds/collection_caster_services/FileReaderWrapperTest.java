package org.nsidc.feeds.collection_caster_services;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.abdera.model.Feed;
import org.junit.*;
import org.nsidc.feeds.collection_caster_services.CollectionCasterServiceImpl;

public class FileReaderWrapperTest {
	
	@Test(expected=IOException.class)
	public void FileReaderWrapper_should_throw_an_exception_when_file_does_not_exist() throws IOException {
		ResourceFileReader fileReader = new ResourceFileReader();
		fileReader.readFileAsString("ASDASsasdFQE@WDWr2ewd2wedad3qews");
	}	
}

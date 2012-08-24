package org.nsidc.feeds.collection_caster_services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class ResourceFileReader {

	public String readFileAsString(String fileName) throws IOException {		
		File file = getFile(fileName);
		String content = convertFileToString(file);

		return content;
	}

	private String convertFileToString(File file) throws IOException {
		FileReader reader = new FileReader(file);
		StringWriter writer = new StringWriter();
		IOUtils.copy(reader, writer);

		String content = writer.toString();
		return content;
	}

	private File getFile(String fileName) throws IOException {
		try {
			return openResourceFile(fileName);
		} catch (NullPointerException e) {
			throw new IOException(e);
		}
	}

	private File openResourceFile(String fileName) {
		String filePath = getFullPathForResourceFile(fileName);
		return new File(filePath);
	}

	private String getFullPathForResourceFile(String fileName) {
		URL resource = getClass().getResource(fileName);
		String filePath = resource.getFile();
		return filePath;
	}
}

package org.nsidc.feeds.collection_caster_services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.util.XmlUtil.XMLVersion;
import org.apache.log4j.Logger;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;
import org.springframework.http.HttpStatus;

public class CollectionCasterServiceImpl implements CollectionCasterService {
	private ResourceFileReader reader;
	private static final Logger logger = Logger.getLogger(CollectionCasterServiceImpl.class);
		
	public CollectionCasterServiceImpl() {
		reader = new ResourceFileReader();	
	}
	
	public String example() {
		return outputContentsOfResourceFile("CollectionCasterExample.xml");
	}
	
	public String help() {
		return outputContentsOfResourceFile("help.html");
	}

	private String outputContentsOfResourceFile(String filename) {
		String exampleXmlString;
		try {
			exampleXmlString = reader.readFileAsString("/" + filename);
		} catch (IOException e) {
			logger.error("Error outputting contents of resource file " + filename + ".", e);
			throw new WebApplicationException(e);
		}
		return exampleXmlString;
	}
	
	public void setReader(ResourceFileReader reader) {
		this.reader = reader;
	}
	
	@Override
	public Entry castFromBinding(EntryBuilderInput input, UriInfo ui,
			HttpServletRequest request) {
		EntryBuilder entryBuilder = new EntryBuilder();
		Entry entry = null;
		try {
			entry = entryBuilder.createEntry(input);
		} catch (InvalidParamsException e) {			
			throw createWebApplicationException(request, e);					
		}
		return entry;
	}

	@Override
	public Feed castFeedFromBinding(FeedBuilderInput feedParams,
			UriInfo uriInfo, HttpServletRequest request) {
		
		FeedBuilder feedBuilder = new FeedBuilder();
		Feed feed = null;
		
		try {
			feed = feedBuilder.createFeed(feedParams);
		} catch (InvalidParamsException e) {
			throw createWebApplicationException(request, e);
		}
		
		return feed;
	}

	public String castFeedHeaderFromBinding(FeedBuilderInput feedParams,
			UriInfo uriInfo, HttpServletRequest request) {
		FeedBuilder feedBuilder = new FeedBuilder();
		Feed feed = null;
		
		try {
			feed = feedBuilder.createFeed(feedParams);
		} catch (InvalidParamsException e) {
			throw createWebApplicationException(request, e);
		}
		
		StringWriter writer = new StringWriter();
		try {
			feed.writeTo(writer);
		} catch (IOException e) {
			throw new WebApplicationException(e);
		}
		
		String feedText = writer.getBuffer().toString();
		int closingTagIndex = feedText.indexOf("</feed>");
		String feedHeader = feedText.substring(0, closingTagIndex);
		return "<?xml version=\"1.0\" ?>\n" + feedHeader;
	}

	public String castFeedFooter() {
		return "</feed>";
	}

	private WebApplicationException createWebApplicationException(
			HttpServletRequest request, InvalidParamsException e) {
		String helpURL = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")) + "/help\n";			
		String errorMessage = e.getMessage() + "\nHelp can be found at: " + helpURL;
		
		WebApplicationException ex = new WebApplicationException(
				Response.status(HttpStatus.BAD_REQUEST.value())
				.entity(errorMessage)
				.type(MediaType.TEXT_PLAIN)
				.build());
		return ex;
	}	

	
}

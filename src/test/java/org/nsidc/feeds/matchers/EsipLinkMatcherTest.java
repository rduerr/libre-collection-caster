package org.nsidc.feeds.matchers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.nsidc.feeds.matchers.EsipLinkMatcher.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;

import org.apache.abdera.model.Link;
import org.junit.Before;
import org.junit.Test;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;

import org.nsidc.feeds.collection_caster_services.bean.LinkBean;

public class EsipLinkMatcherTest {

	private Entry entry;
	private EntryBuilderInput entryBuilderInput;
		
	@Before 
	public void setup() {
		entry = Abdera.getInstance().newEntry();
		entryBuilderInput = new EntryBuilderInput();		
	}
	
	@Test
	public void matcher_is_true_when_Entry_links_are_valid() {
		Link link = Abdera.getNewFactory().newLink();
		link.setHref("http://url.org");
		link.setRel("alternate");
		link.setMimeType("text/plain");
		entry.addLink(link);
		Link esipLink = Abdera.getNewFactory().newLink();
		esipLink.setHref("http://url.org");
		esipLink.setRel("http://esipfed.org/ns/discovery/1.1/data#");
		esipLink.setMimeType("text/plain");
		entry.addLink(esipLink);
		assertThat(entry, validEntryLinks());
	}
	
	@Test
	public void matcher_is_true_when_EntryBuilderInput_links_are_valid() {
		List<LinkBean> links = new ArrayList<LinkBean>();
		links.add(new LinkBean("http://url.org","alternate","text/plain"));
		links.add(new LinkBean("http://url.org","http://esipfed.org/ns/discovery/1.1/data#","text/plain"));
		entryBuilderInput.setLinks(links);
		assertThat(entryBuilderInput, validEntryBuilderInputLinks());		
	}
	
	@Test
	public void matcher_is_false_when_Entry_links_are_empty() {
		assertThat(entry, not(validEntryLinks()));		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_links_are_empty() {
		assertThat(entryBuilderInput, not(validEntryBuilderInputLinks()));		
	}
	
	@Test
	public void matcher_is_false_when_Entry_links_are_blank() {
		Link link = Abdera.getNewFactory().newLink();
		link.setHref("");
		link.setRel("");
		link.setMimeType("text/plain");
		entry.addLink(link);
		assertThat(entry, not(validEntryLinks()));		
	}
	
	@Test
	public void matcher_is_false_when_entryBuilderInput_links_are_blank() {
		List<LinkBean> links = new ArrayList<LinkBean>();
		links.add(new LinkBean("","",""));
		entryBuilderInput.setLinks(links);
		assertThat(entryBuilderInput, not(validEntryBuilderInputLinks()));		
	}
	
}

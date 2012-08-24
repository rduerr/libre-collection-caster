package org.nsidc.feeds.collection_caster_services.bean;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _EntryBuilderInput_QNAME = new QName("", "entry");
	
	public ObjectFactory() {
	}
	
	public EntryBuilderInput createEntryBuilderInput() {
		return new EntryBuilderInput();
	}

	public FeedBuilderInput createFeedBuilderInput() {
		return new FeedBuilderInput();
	}
	
	public LinkBean createLinkBean() {
		return new LinkBean();
	}
	
	public AuthorBean createAuthorBean() {
		return new AuthorBean();
	}
	
	@XmlElementDecl(namespace = "", name = "entry")
    public JAXBElement<EntryBuilderInput> createEntryBuilderInput(EntryBuilderInput value) {
        return new JAXBElement<EntryBuilderInput>(_EntryBuilderInput_QNAME, EntryBuilderInput.class, null, value);
    }

}

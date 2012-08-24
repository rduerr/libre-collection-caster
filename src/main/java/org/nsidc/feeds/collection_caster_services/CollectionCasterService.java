package org.nsidc.feeds.collection_caster_services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.nsidc.feeds.collection_caster_services.bean.EntryBuilderInput;
import org.nsidc.feeds.collection_caster_services.bean.FeedBuilderInput;

@Path("/"+CollectionCasterService.SERVICE_VERSION_NUMBER)
public interface CollectionCasterService {
	
	public static final String SERVICE_VERSION_NUMBER = "1.0";
	
	@GET
	@Path("example")
	@Produces("application/atom+xml")
	public String example();

	@GET
	@Path("help")
	@Produces("text/html")
	public String help();
	
	@POST
	@Path("entry")
	@Consumes({"application/xml","text/xml","application/json"})
	@Produces({"application/atom+xml","text/xml","application/atom+xml;type=entry"})
	public Entry castFromBinding(EntryBuilderInput input, @Context UriInfo ui, @Context HttpServletRequest request);

	@POST
	@Path("feed")
	@Consumes({"application/xml","text/xml","application/json"})
	@Produces({"application/atom+xml","text/xml","application/atom+xml;type=feed"})
	public Feed castFeedFromBinding(FeedBuilderInput feedParams, @Context UriInfo uriInfo,
			@Context HttpServletRequest request);
	
	@POST
	@Path("feed/header")
	@Consumes({"application/xml","text/xml","application/json"})
	@Produces("text/plain")
	public String castFeedHeaderFromBinding(FeedBuilderInput feedParams, @Context UriInfo uriInfo,
			@Context HttpServletRequest request);
	
	@GET
	@Path("feed/footer")
	@Produces("text/plain")
	public String castFeedFooter();	
	
}

package org.acme.resource;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CreateUrlRequest;
import org.acme.dto.ShortUrlResponse;
import org.acme.dto.UpdateUrlRequest;
import org.acme.dto.UrlStatistics;
import org.acme.service.UrlShortenerService;

import java.util.List;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UrlShortenerResource {

    @Inject
    UrlShortenerService urlShortenerService;

    @POST
    @Path("/shorten")
    public Response createShortUrl(@Valid CreateUrlRequest request) {
        ShortUrlResponse response = urlShortenerService.createShortUrl(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/urls/{shortCode}")
    public Response getUrlDetails(@PathParam("shortCode") String shortCode) {
        ShortUrlResponse response = urlShortenerService.getShortUrlDetails(shortCode);
        return Response.ok(response).build();
    }

    @GET
    @Path("/urls/{shortCode}/stats")
    public Response getStatistics(@PathParam("shortCode") String shortCode) {
        UrlStatistics stats = urlShortenerService.getStatistics(shortCode);
        return Response.ok(stats).build();
    }

    @PUT
    @Path("/urls/{shortCode}")
    public Response updateShortUrl(
            @PathParam("shortCode") String shortCode,
            @Valid UpdateUrlRequest request) {
        ShortUrlResponse response = urlShortenerService.updateShortUrl(shortCode, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/urls/{shortCode}")
    public Response deleteShortUrl(@PathParam("shortCode") String shortCode) {
        urlShortenerService.deleteShortUrl(shortCode);
        return Response.noContent().build();
    }

    @GET
    @Path("/urls")
    public Response listUrls(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        List<ShortUrlResponse> urls = urlShortenerService.listAllUrls(page, size);
        return Response.ok(urls).build();
    }
}
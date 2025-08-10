package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.acme.service.UrlShortenerService;

import java.net.URI;

@Path("/s")
public class RedirectResource {

    @Inject
    UrlShortenerService urlShortenerService;

    @GET
    @Path("/{shortCode}")
    public Response redirect(@PathParam("shortCode") String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        URI targetUri = UriBuilder.fromUri(originalUrl).build();
        return Response.temporaryRedirect(targetUri).build();
    }
}

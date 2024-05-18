package org.example.service.a.resources;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.a.domain.services.BookService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 */
@LoggingInterceptor
@Path("/book")
@OpenAPIDefinition(info = @Info(title = "Library endpoint", version = "1.0"))
public class BookResource {

    @Inject
    private BookService bookService;

    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Book List",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(
                                    ref = "Book"))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No book found for library.")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response showBookList() {
        return Response
                .status(Response.Status.OK)
                .entity(bookService.getList())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{bookId}")
    public Response showBook(@PathParam("bookId") String bookId) {
        try {
            var book = bookService.getById(bookId);
            return Response
                    .status(Response.Status.OK)
                    .entity(book)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

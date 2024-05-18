package org.example.service.b.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.logging.interceptor.LoggingInterceptor;
import org.example.service.b.domain.services.BookBorrowTxService;

@LoggingInterceptor
@Path("/book/borrow/tx")
public class BookBorrowTxResource {

    @Inject
    private BookBorrowTxService bookBorrowTxService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add/{bookId}/{email}/{txType}")
    public Response add(@PathParam("bookId") String bookId,
                        @PathParam("email") String email,
                        @PathParam("txType") boolean txType) {
        try {
            bookBorrowTxService.create(bookId, email, txType);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

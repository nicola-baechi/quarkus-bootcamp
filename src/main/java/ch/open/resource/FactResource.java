package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.service.FactService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.core.Response.Status;

@Path("/facts")
public class FactResource {

    @Inject
    FactService factService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FactResult> getFacts() {
        return factService.getFacts();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public FactResult addFact(NewFact newFact) {
        return factService.addFact(newFact);
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFactById(@PathParam("id") long id){
        if(factService.deleteFactById(id)){
            return Response.status(Status.OK).build();
        }
        return Response.status(Status.NOT_FOUND).build();

    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFactById(@PathParam("id") long id) {
        return factService.getFactById(id)
            .map(f -> Response.ok(f).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}

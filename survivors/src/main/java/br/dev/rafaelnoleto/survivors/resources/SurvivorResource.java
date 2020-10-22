package br.dev.rafaelnoleto.survivors.resources;

import br.dev.rafaelnoleto.survivors.model.service.SurvivorService;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("survivors")
public class SurvivorResource {
    
    private final SurvivorService service = new SurvivorService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(HashMap<String, Object> data) {
        return Response.ok(data).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") Integer id) {
        return Response.ok("GetOneSurvivor").build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok("GetAllSurvivors").build();
    }
    
    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportData() {
        return Response.ok("GetReportData").build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Integer id, HashMap<String, Object> data) {
        return Response.ok(data).build();
    }
    
    @PATCH
    @Path("/change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putExchange(HashMap<String, Object> data) {
        return Response.ok(data).build();
    }

}

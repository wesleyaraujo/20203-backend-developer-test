package br.dev.rafaelnoleto.survivors.resources;

import br.dev.rafaelnoleto.survivors.model.service.ItemService;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("items")
public class ItemResource {
    
    private final ItemService service = new ItemService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(HashMap<String, Object> data) {
        return Response.ok(data).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok("GetAllItems").build();
    }
    
}

package br.dev.rafaelnoleto.survivors.resources;

import br.dev.rafaelnoleto.survivors.model.service.SurvivorService;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("survivors")
public class SurvivorResource {

    @Inject
    private SurvivorService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(LinkedHashMap<String, Object> data) {
        List<String> errors = this.service.validate(data);

        if (!errors.isEmpty()) {
            return Utils.responseError(errors);
        }

        final Integer id = this.service.create(data);

        return Utils.response(Utils.parseIdResponse(id), Status.CREATED);
    }

    @POST
    @Path("/{survivorId}/{survivorNotifierId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(@PathParam("survivorId") Integer survivorId, @PathParam("survivorNotifierId") Integer survivorNotifierId) {
        List<String> errors = this.service.validateCreateNotification(survivorId, survivorNotifierId);

        if (!errors.isEmpty()) {
            return Utils.responseError(errors);
        }

        final Integer idNotification = this.service.createNotification(survivorId, survivorNotifierId);

        return Utils.response(Utils.parseIdResponse(idNotification), Status.CREATED);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") Integer id) {
        LinkedHashMap<String, Object> data = this.service.readOne(id);
        return Utils.response(data);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<LinkedHashMap<String, Object>> data = this.service.readAll();
        return Utils.response(data);
    }

    @PUT
    @Path("/{id}/location")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putLocation(@PathParam("id") Integer id, LinkedHashMap<String, Object> data) {
        List<String> errors = this.service.validateUpdateLocation(id, data);

        if (!errors.isEmpty()) {
            return Utils.responseError(errors);
        }

        this.service.updateLocation(id, data);

        return Utils.response(Status.OK);
    }

    @PUT
    @Path("/exchange")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putExchange(LinkedHashMap<String, Object> data) {
        List<String> errors = this.service.validateExchange(data);

        if (!errors.isEmpty()) {
            return Utils.responseError(errors);
        }

        this.service.executeExchange(data);

        return Utils.response(Status.OK);
    }

}

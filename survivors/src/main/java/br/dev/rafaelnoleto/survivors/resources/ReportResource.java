package br.dev.rafaelnoleto.survivors.resources;

import br.dev.rafaelnoleto.survivors.model.service.ReportService;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.LinkedHashMap;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reports")
public class ReportResource {

    @Inject
    private ReportService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGeneral() {
        LinkedHashMap<String, Object> data = this.service.readGeneralReport();
        return Utils.response(data);
    }

}

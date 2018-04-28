package com.login.report.api;

import com.login.report.service.MedicalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * JAX-RS resource class for Medical Report.
 *
 * @author : Jemin
 */
@Component
@Path("report")
public class MedicalApi {

    private final MedicalReportService medicalReportService;

    @Autowired
    public MedicalApi(MedicalReportService medicalReportService) {
        this.medicalReportService = medicalReportService;
    }

    /**
     * Return report.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport() {
        return Response.ok(medicalReportService.getReports()).build();
    }
}

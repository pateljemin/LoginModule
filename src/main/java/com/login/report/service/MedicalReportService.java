package com.login.report.service;

import org.springframework.stereotype.Service;

/**
 * Service which provides operations for Medical reports.
 *
 * @author Jemin
 */
@Service
public class MedicalReportService {

    public String getReports() {
        return "My Report";
    }
}

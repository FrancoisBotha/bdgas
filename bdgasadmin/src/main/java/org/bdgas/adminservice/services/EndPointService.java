package org.bdgas.adminservice.services;

import lombok.extern.slf4j.Slf4j;
import org.bdgas.adminservice.domain.model.JobServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndPointService {

    @Autowired
    JobServerService jobServerService;

    //Spark Job Server
    private static final String SJS_BINARIES = "binaries";
    private static final String SJS_CONTEXTS = "contexts";
    private static final String SJS_JOBS = "jobs";

    public String getSjsBinariesEP() {
        return this.SjsMerge(this.SJS_BINARIES);
    }

    public String getSjsContextsEP() {
        return this.SjsMerge(this.SJS_CONTEXTS);
    }

    public String getSjsJobsEP() {
        return this.SjsMerge(this.SJS_JOBS);
    }

    private String SjsMerge(String endpoint) {

        JobServer jobServer = jobServerService.getJobServerActive();

        return jobServer.getJobServerURL()
                + "/"
                + endpoint;
    }

}

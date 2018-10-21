package org.bdgas.adminservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndPointService {

    @Value("${sjs.domain}")
    private String sjsDomain;

    @Value("${sjs.protocol}")
    private String sjsProtocol;

    @Value("${sjs.port}")
    private String sjsPort;


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
        return this.sjsProtocol
                + this.sjsDomain + ":"
                + this.sjsPort + "/"
                + endpoint;
    }

}

package io.francoisbotha.bdgasadmin.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndPointService {

    //Spark Job Server
    private static final String SJS_DOMAIN = "ec2-54-211-6-190.compute-1.amazonaws.com";
    private static final String SJS_PROTOCOL = "http://";
    private static final String SJS_PORT = "8090";

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
        return this.SJS_PROTOCOL
                + this.SJS_DOMAIN + ":"
                + this.SJS_PORT + "/"
                + endpoint;
    }

}

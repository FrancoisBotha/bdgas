package io.francoisbotha.bdgaswebproxy.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndPointService {

    private static final String PROTOCOL = "http://";
    private static final String DOMAIN = "localhost";
    private static final String PORT = "19000";
    private static final String BASE_PATH = "api";
    private static final String VERSION = "v1";

    private static final String HELPTEXT = "helptext";
    private static final String TEAM = "team";

    public String getHelpTextEP() {
        return this.merge(this.HELPTEXT);
    }

    public String getTeamEP() {
        return this.merge(this.TEAM);
    }

    private String merge(String endpoint) {
        return this.PROTOCOL
                + this.DOMAIN + ":"
                + this.PORT + "/"
                + this.BASE_PATH + "/"
                + this.VERSION + "/"
                + endpoint;
    }

}

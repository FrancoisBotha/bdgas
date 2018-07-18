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
    private static final String TEAMPROJECTS = "project/team";
    private static final String PROJECT = "project";
    private static final String WORKINGPAPER = "workingpaper";
    private static final String PROJECTWORKINGPAPERS = "workingpaper/project";

    public String getHelpTextEP() {
        return this.merge(this.HELPTEXT);
    }

    public String getTeamEP() {
        return this.merge(this.TEAM);
    }

    public String getProjectEP() {
        return this.merge(this.PROJECT);
    }

    public String getTeamProjectsEP() {
        return this.merge(this.TEAMPROJECTS);
    }

    public String getWorkingPaperEP() {
        return this.merge(this.WORKINGPAPER);
    }
    public String getProjectWorkingPapersEP() {
        return this.merge(this.PROJECTWORKINGPAPERS);
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

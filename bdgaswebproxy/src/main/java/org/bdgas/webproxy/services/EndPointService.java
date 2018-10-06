package org.bdgas.webproxy.services;


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
    private static final String HELPTEXTFORNAME = "helptext/name";
    private static final String CODETYPE = "codetype";
    private static final String CODETABLE = "codetable";
    private static final String CODETABLESNR = "codetable/codetypenr";
    private static final String CODETYPETABLES = "codetable/codetype";
    private static final String TASK = "task";
    private static final String LOCALDATASOURCES = "localdatasource";
    private static final String TEAM = "team";
    private static final String USER = "user";
    private static final String TEAMUSER = "teamuser";
    private static final String TEAMPROJECTS = "project/team";
    private static final String PROJECT = "project";
    private static final String WORKINGPAPER = "workingpaper";
    private static final String WPLINE = "wpline";
    private static final String WORKINGPAPERLINES = "wpline/workingpaper";
    private static final String PROJECTWORKINGPAPERS = "workingpaper/project";
    private static final String SIGNEDURL = "s3/signedurl";
    private static final String DATASOURCE = "datasource";
    private static final String TEAMDATASOURCE = "datasource/team";

    public String getHelpTextEP() {
        return this.merge(this.HELPTEXT);
    }
    public String getHelpTextForNameEP() {
        return this.merge(this.HELPTEXTFORNAME);
    }

    public String getCodeTypeEP() {
        return this.merge(this.CODETYPE);
    }

    public String getCodeTableEP() {
        return this.merge(this.CODETABLE);
    }
    public String getCodeTypeTablesEP() {
        return this.merge(this.CODETYPETABLES );
    }
    public String getCodeTablesByNrEP() {
        return this.merge(this.CODETABLESNR );
    }

    public String getTaskEP() {
        return this.merge(this.TASK);
    }

    public String getLocalDataSourcesEP() {
        return this.merge(this.LOCALDATASOURCES);
    }

    public String getTeamEP() {
        return this.merge(this.TEAM);
    }

    public String getUserEP() {
        return this.merge(this.USER);
    }

    public String getTeamUserEP() {
        return this.merge(this.TEAMUSER);
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

    public String getWpLineEP() {
        return this.merge(this.WPLINE);
    }
    public String getWorkingPaperLinesEP() {
        return this.merge(this.WORKINGPAPERLINES);
    }

    public String getSignedUrlEP() {
        return this.merge(this.SIGNEDURL);
    }

    public String getDataSourceEP() {
        return this.merge(this.DATASOURCE);
    }

    public String getTeamDataSourcesEP() {
        return this.merge(this.TEAMDATASOURCE);
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

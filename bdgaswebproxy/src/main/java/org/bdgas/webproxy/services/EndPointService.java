package org.bdgas.webproxy.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndPointService {

    @Value("${endpoint.adminserver.protocol}")
    private String protocol;

    @Value("${endpoint.adminserver.domain}")
    private String domain;

    @Value("${endpoint.adminserver.port}")
    private String port;

    @Value("${endpoint.adminserver.basepath}")
    private String basepath;

    @Value("${endpoint.adminserver.version}")
    private String version;


    private final String HELPTEXT = "helptext";
    private final String HELPTEXTFORNAME = "helptext/name";
    private final String CODETYPE = "codetype";
    private final String CODETABLE = "codetable";
    private final String CODETABLESNR = "codetable/codetypenr";
    private final String CODETYPETABLES = "codetable/codetype";
    private final String TASK = "task";
    private final String LOCALDATASOURCES = "localdatasource";
    private final String TEAM = "team";
    private final String TEAMTEAMS = "team/user";
    private final String USER = "user";
    private final String TEAMUSER = "teamuser";
    private final String TEAMUSERUSERS = "teamuser/team";
    private final String TEAMPROJECTS = "project/team";
    private final String PROJECT = "project";
    private final String WORKINGPAPER = "workingpaper";
    private final String WPLINE = "wpline";
    private final String WORKINGPAPERLINES = "wpline/workingpaper";
    private final String PROJECTWORKINGPAPERS = "workingpaper/project";
    private final String SIGNEDURL = "s3/signedurl";
    private final String DATASOURCE = "datasource";
    private final String TEAMDATASOURCE = "datasource/team";
    private final String JOBSERVER = "jobserver";

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

    public String getUserTeamsEP() {
        return this.merge(this.TEAMTEAMS);
    }

    public String getTeamUserUsersEP() {
        return this.merge(this.TEAMUSERUSERS);
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

    public String getJobServerEP() {
        return this.merge(this.JOBSERVER);
    }

    private String merge(String endpoint) {
        String  merged = protocol
                + domain + ":"
                + port + "/"
                + basepath + "/"
                + version + "/"
                + endpoint;

        return merged;
    }

}

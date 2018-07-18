/*****************************************************************************
 * Copyright 2018 Francois Botha                                             *
 *                                                                           *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 *  http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *                                                                           *
 *****************************************************************************/
package io.francoisbotha.bdgaswebproxy.controller;

import io.francoisbotha.bdgaswebproxy.domain.dto.HelpTextDto;
import io.francoisbotha.bdgaswebproxy.domain.dto.ProjectDto;
import io.francoisbotha.bdgaswebproxy.domain.dto.TeamDto;
import io.francoisbotha.bdgaswebproxy.services.ProjectService;
import io.francoisbotha.bdgaswebproxy.services.TeamService;
import io.francoisbotha.bdgaswebproxy.services.WorkingPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Controller
public class WelcomeController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WorkingPaperService workingPaperService;

    private static final String BASE_PATH = "/pages/ui/welcome/";
    private static final String WELCOME_VIEW_NAME = BASE_PATH + "welcome";

    /* Key which identifies team payload in Model */
    public static final String TEAM_MODEL_KEY = "selectedTeam";
    public static final String PROJECT_MODEL_KEY = "selectedProject";
    public static final String TEAM_FORMMODEL_KEY = "teamForm";
    public static final String PROJECT_FORMMODEL_KEY = "projectForm";
    private static final String TEAMLIST_MODEL_KEY = "teams";
    private static final String PROJECTLIST_MODEL_KEY = "projects";
    private static final String WORKINGPAPERLIST_MODEL_KEY = "workingPapers";


    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/ui/welcome", method = RequestMethod.GET)
    public String ShowWelcomePage(Model model) {

        try {

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

            TeamDto teamForm = new TeamDto();
            model.addAttribute(TEAM_FORMMODEL_KEY, teamForm );

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.WELCOME_VIEW_NAME;
    }

    @RequestMapping(value = "/ui/welcome/{teamId}", method = RequestMethod.GET)
    public String ShowWelcomePageTeam(Model model, @PathVariable("teamId") String teamId) {

        try {

            TeamDto team = teamService.getOne(teamId);
            model.addAttribute(TEAM_MODEL_KEY, team);

            log.debug(team.toString());

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

            List projects = projectService.getTeamProjects(teamId);
            model.addAttribute(PROJECTLIST_MODEL_KEY, projects);

            TeamDto teamForm = new TeamDto();
            model.addAttribute(TEAM_FORMMODEL_KEY, teamForm );

            ProjectDto projectForm = new ProjectDto();
            projectForm.setTeamId(teamId);
            model.addAttribute(PROJECT_FORMMODEL_KEY, projectForm );

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.WELCOME_VIEW_NAME;
    }

    @RequestMapping(value = "/ui/welcome/{teamId}/{projectId}", method = RequestMethod.GET)
    public String ShowWelcomePageTeam(Model model,
                                      @PathVariable("teamId") String teamId,
                                      @PathVariable("projectId") String projectId) {

        try {

            TeamDto team = teamService.getOne(teamId);
            model.addAttribute(TEAM_MODEL_KEY, team);

            log.debug(team.toString());

            ProjectDto project = projectService.getOne(projectId);
            model.addAttribute(PROJECT_MODEL_KEY, project);

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

            List projects = projectService.getTeamProjects(teamId);
            model.addAttribute(PROJECTLIST_MODEL_KEY, projects);

            List workingPapers = workingPaperService.getProjectWorkingPapers(projectId);
            model.addAttribute(WORKINGPAPERLIST_MODEL_KEY, workingPapers);

            TeamDto teamForm = new TeamDto();
            model.addAttribute(TEAM_FORMMODEL_KEY, teamForm );

            ProjectDto projectForm = new ProjectDto();
            projectForm.setTeamId(teamId);
            model.addAttribute(PROJECT_FORMMODEL_KEY, projectForm );

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.WELCOME_VIEW_NAME;
    }

    @RequestMapping(value = "/ui/welcome", method = RequestMethod.POST)
    public String UpdateSelectedTeamOne(@ModelAttribute(TEAM_FORMMODEL_KEY) TeamDto teamDto, Model model) {

        if (teamDto.getId() == null) {
            return "redirect:/ui/welcome";
        } else {
            return "redirect:/ui/welcome/" + teamDto.getId();
        }

    }

    @RequestMapping(value = "/ui/welcome/{teamId}", method = RequestMethod.POST)
    public String UpdateSelectedTeamTwo(Model model, @PathVariable("teamId") String teamId) {

        try {

            TeamDto team = teamService.getOne(teamId);
            model.addAttribute(TEAM_MODEL_KEY, team);

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.WELCOME_VIEW_NAME;
    }

    @RequestMapping(value = "/ui/welcome/{teamId}/{projectId}", method = RequestMethod.POST)
    public String UpdateSelectedTeamTwo(Model model,
                                        @ModelAttribute(PROJECT_FORMMODEL_KEY) ProjectDto projectDto,
                                        @PathVariable("teamId") String teamId,
                                        @PathVariable("projectId") String projectId) {

        return "redirect:/ui/welcome/" + teamId + "/" + projectDto.getId();

    }

}



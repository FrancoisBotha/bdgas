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
package org.bdgas.webproxy.controller.ui;

import org.bdgas.webproxy.domain.dto.ProjectDto;
import org.bdgas.webproxy.domain.dto.TeamDto;
import org.bdgas.webproxy.services.ProjectService;
import org.bdgas.webproxy.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;

@Slf4j
@Controller
public class ProjectController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamService teamService;

    private static final String BASE_PATH = "pages/ui/project/";
    private static final String NEW_PROJECT_VIEW_NAME = BASE_PATH + "project_new";


    /* Key which identifies project payload in Model */
    public static final String TEAM_MODEL_KEY = "team";
    public static final String PROJECT_MODEL_KEY = "project";

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/ui/project/new/{teamId}", method = RequestMethod.GET)
    public String ShowProjectNewPage(ModelMap model, @PathVariable("teamId") String teamId) {

        try {

            TeamDto teamDto = teamService.getOne(teamId);

            ProjectDto projectDto = new ProjectDto();
            projectDto.setTeamId(teamDto.getId());
            model.addAttribute(this.PROJECT_MODEL_KEY, projectDto);
            model.addAttribute(this.TEAM_MODEL_KEY, teamDto);

            return this.NEW_PROJECT_VIEW_NAME;

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_PROJECT_VIEW_NAME;
        }

    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/ui/project", method = RequestMethod.POST)
    public String ProjectPost(@ModelAttribute(PROJECT_MODEL_KEY) @Valid ProjectDto projectDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return this.NEW_PROJECT_VIEW_NAME;
        }

        try {

            projectService.create(projectDto);
            return "redirect:/ui/welcome/" + projectDto.getTeamId();

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_PROJECT_VIEW_NAME;
        }

    }

}

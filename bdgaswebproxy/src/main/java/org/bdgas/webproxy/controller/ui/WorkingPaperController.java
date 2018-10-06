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
import org.bdgas.webproxy.domain.dto.WorkingPaperDto;
import org.bdgas.webproxy.domain.dto.TeamDto;
import org.bdgas.webproxy.services.ProjectService;
import org.bdgas.webproxy.services.WorkingPaperService;
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
public class WorkingPaperController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private WorkingPaperService workingPaperService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ProjectService projectService;

    private static final String BASE_PATH = "pages/ui/workingpaper/";
    private static final String NEW_WORKINGPAPERVIEW_NAME = BASE_PATH + "workingpaper_new";


    /* Key which identifies workingPaper payload in Model */
    public static final String PROJECT_MODEL_KEY = "project";
    public static final String TEAM_MODEL_KEY = "team";
    public static final String WORKINGPAPERMODEL_KEY = "workingPaper";

//    /**************
//     * SHOW SPA   *
//     * ************/
//    @RequestMapping(value = "/ui/wp", method = RequestMethod.GET)
//    public ModelAndView ShowSPA(Model model) {
//
//
//
//    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/ui/workingpaper/new/{teamId}/{projectId}", method = RequestMethod.GET)
    public String ShowWorkingPaperNewPage(ModelMap model,
                                          @PathVariable("teamId") String teamId,
                                          @PathVariable("projectId") String projectId) {

        try {

            TeamDto teamDto = teamService.getOne(teamId);
            model.addAttribute(this.TEAM_MODEL_KEY, teamDto);

            ProjectDto projectDto = projectService.getOne(projectId);
            model.addAttribute(this.PROJECT_MODEL_KEY, projectDto);

            WorkingPaperDto workingPaperDto = new WorkingPaperDto();
            workingPaperDto.setProjectId(projectDto.getId());
            model.addAttribute(this.WORKINGPAPERMODEL_KEY, workingPaperDto);

            return this.NEW_WORKINGPAPERVIEW_NAME;

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_WORKINGPAPERVIEW_NAME;
        }

    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/ui/workingpaper/{teamId}/{projectId}", method = RequestMethod.POST)
    public String WorkingPaperPost(@ModelAttribute(WORKINGPAPERMODEL_KEY) @Valid WorkingPaperDto workingPaperDto
            , BindingResult bindingResult, ModelMap model,
                                   @PathVariable("teamId") String teamId,
                                   @PathVariable("projectId") String projectId) {

        if (bindingResult.hasErrors()) {
            ProjectDto projectDto = projectService.getOne(projectId);
            workingPaperDto.setProjectId(projectDto.getId());
            model.addAttribute(this.PROJECT_MODEL_KEY, projectDto);

            return this.NEW_WORKINGPAPERVIEW_NAME;
        }

        try {
            workingPaperService.create(workingPaperDto);
            return "redirect:/ui/welcome/" + teamId + "/" + projectId;
        } catch (RestClientException ex) {
            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_WORKINGPAPERVIEW_NAME;
        }

    }

}
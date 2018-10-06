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
package org.bdgas.webproxy.controller.admin;

import org.bdgas.webproxy.domain.dto.CodeTableDto;
import org.bdgas.webproxy.domain.dto.TeamDto;
import org.bdgas.webproxy.services.CodeTableService;
import org.bdgas.webproxy.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.bdgas.webproxy.services.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class TeamController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamUserService teamUserService;

    private static final String BASE_PATH = "pages/admin/teams/";
    private static final String TEAMS_VIEW_NAME = BASE_PATH + "teams";
    private static final String VIEW_TEAM_VIEW_NAME = BASE_PATH + "teams_view";
    private static final String NEW_TEAM_VIEW_NAME = BASE_PATH + "teams_new";
    private static final String MOD_TEAM_VIEW_NAME = BASE_PATH + "teams_mod";

    private static final String TEAMUSERS_VIEW_NAME = BASE_PATH + "team_users";
    private static final String NEW_TEAMUSERS_VIEW_NAME = BASE_PATH + "team_users_new";


    /* Key which identifies helpText payload in Model */
    public static final String TEAM_MODEL_KEY = "team";
    private static final String TEAMLIST_MODEL_KEY = "teams";

    public static final String TEAMUSER_MODEL_KEY = "teamUser";
    private static final String TEAMUSERLIST_MODEL_KEY = "teamUsers";


    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/team", method = RequestMethod.GET)
    public String ShowTeamsPage(Model model) {

        try {

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.TEAMS_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/team/{id}", method = RequestMethod.GET)
    public String ViewVendor(Model model,
                             @PathVariable("id") String id) {
        try {

            TeamDto teamDto = teamService.getOne(id);
            model.addAttribute(TEAM_MODEL_KEY, teamDto);


        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_TEAM_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/team/new", method = RequestMethod.GET)
    public String ShowTeamNewPage(ModelMap model) {
        TeamDto teamDto = new TeamDto();
        model.addAttribute(this.TEAM_MODEL_KEY , teamDto);

        return this.NEW_TEAM_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/team", method = RequestMethod.POST)
    public String HelpTextPost(@ModelAttribute(TEAMLIST_MODEL_KEY) @Valid TeamDto teamDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return this.NEW_TEAM_VIEW_NAME;
        }

        try {

            teamService.create(teamDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_TEAM_VIEW_NAME;
        }

        return "redirect:/admin/team";
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/team/mod/{id}", method = RequestMethod.GET)
    public String modHelpText(Model model,
                              @PathVariable("id") String id) {

        try {

            TeamDto teamDto = teamService.getOne(id);
            model.addAttribute(TEAM_MODEL_KEY, teamDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_TEAM_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/team/mod/{id}", method = RequestMethod.POST)
    public String HelpTextModSave(@ModelAttribute(TEAM_MODEL_KEY) @Valid TeamDto teamDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return this.MOD_TEAM_VIEW_NAME;
        }

        try {

            teamService.modify(teamDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.MOD_TEAM_VIEW_NAME;
        }

        return "redirect:/admin/team/" + id;
    }
    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/team/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteHelpText(Model model,
                                             @PathVariable("id") String id) {

        try {

            teamService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }

    /***************
     * TEAM USERS  *
     ***************/

    /***********
     * LIST    *
     ***********/
    @RequestMapping(value = "/admin/team/users/{id}", method = RequestMethod.GET)
    public String ShowCodeTablePage(Model model,
                                    @PathVariable("id") String id) {

        try {

            TeamDto teamDto = teamService.getOne(id);
            model.addAttribute(TEAM_MODEL_KEY, teamDto);

            List teamUsers = teamUserService.getUsersForTeam(id);
            model.addAttribute(TEAMUSERLIST_MODEL_KEY, teamUsers);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);

        }

        return this.TEAMUSERS_VIEW_NAME;

    }
//
//    /***********
//     * VIEW    *
//     * *********/
//    @RequestMapping(value = "/admin/codetable/{tableId}", method = RequestMethod.GET)
//    public String ViewCodeTable(Model model,
//                                @PathVariable("tableId") String tableId) {
//        try {
//
//            CodeTableDto teamUserDto = teamUserService.getOne(tableId);
//            model.addAttribute(TEAMUSER_MODEL_KEY, teamUserDto);
//
//        } catch (RestClientException ex) {
//
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//        }
//
//        return this.VIEW_TEAMUSER_VIEW_NAME;
//
//    }
//
//    /***************
//     * NEW-FORM    *
//     * *************/
//    @RequestMapping(value = "/admin/team/codetable/{id}/new", method = RequestMethod.GET)
//    public String ShowCodeTableNewPage(ModelMap model,
//                                       @PathVariable("id") String id) {
//
//        TeamDto teamDto = teamService.getOne(id);
//        model.addAttribute(TEAM_MODEL_KEY, teamDto);
//
//        log.info(teamDto.toString());
//
//        CodeTableDto teamUserDto = new CodeTableDto();
//        teamUserDto.setCdeTypeId(id);
//        model.addAttribute(this.TEAMUSER_MODEL_KEY , teamUserDto);
//
//        return this.NEW_TEAMUSER_VIEW_NAME;
//    }
//
//    /***************
//     * NEW: SAVE   *
//     * *************/
//    @RequestMapping(value = "/admin/team/codetable", method = RequestMethod.POST)
//    public String CodeTablePost(@ModelAttribute(TEAMUSER_MODEL_KEY) @Valid CodeTableDto teamUserDto
//            , BindingResult bindingResult, ModelMap model) {
//
//        if (bindingResult.hasErrors()) {
//            return this.NEW_TEAMUSER_VIEW_NAME;
//        }
//
//        try {
//
//            teamUserService.create(teamUserDto);
//
//        } catch (RestClientException ex) {
//
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//            return this.NEW_TEAMUSER_VIEW_NAME;
//        }
//
//        return "redirect:/admin/team/codetable/" + teamUserDto.getCdeTypeId();
//    }
//
//    /***************
//     * MOD-FORM    *
//     * *************/
//    @RequestMapping(value = "/admin/codetable/mod/{id}", method = RequestMethod.GET)
//    public String modCodeTable(Model model,
//                               @PathVariable("id") String id) {
//        try {
//
//            CodeTableDto teamUserDto = teamUserService.getOne(id);
//            model.addAttribute(TEAMUSER_MODEL_KEY, teamUserDto);
//
//        } catch (RestClientException ex) {
//
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//        }
//
//        return this.MOD_TEAMUSER_VIEW_NAME;
//
//    }
//
//    /***************
//     * MOD: SAVE   *
//     * *************/
//    @RequestMapping(value = "/admin/codetable/mod/{id}", method = RequestMethod.POST)
//    public String ModCodeTableSave(@ModelAttribute(TEAMUSER_MODEL_KEY) @Valid CodeTableDto teamUserDto
//            , BindingResult bindingResult, ModelMap model,
//                                   @PathVariable("id") String id) {
//
//        if (bindingResult.hasErrors()) {
//            return this.MOD_TEAMUSER_VIEW_NAME;
//        }
//
//        try {
//
//            teamUserService.modify(teamUserDto);
//
//        } catch (RestClientException ex) {
//
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//            return this.MOD_TEAMUSER_VIEW_NAME;
//        }
//
//        return "redirect:/admin/codetable/" + id;
//    }
//
//    /***************
//     * DELETE      *
//     * *************/
//    //Used response body because ajax used to delete
//    @RequestMapping(value = "/admin/codetable/{id}", method = RequestMethod.DELETE)
//    public @ResponseBody void DeleteCodeTable(Model model,
//                                              @PathVariable("id") String id) {
//
//        try {
//
//            teamUserService.delete(id);
//
//        } catch (RestClientException ex) {
//
//            //TODO: some error handling here...
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//        }
//
//    }

}
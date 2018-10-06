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

import org.bdgas.webproxy.domain.dto.TeamDto;
import org.bdgas.webproxy.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;

//@Slf4j
//@Controller
//public class TeamController {
//
//    @Value("${restservice.error.defaultmsg}")
//    private String RestServiceErrorMsg;
//
//    @Autowired
//    private TeamService teamService;
//
//    private static final String BASE_PATH = "pages/ui/team/";
//    private static final String NEW_TEAM_VIEW_NAME = BASE_PATH + "team_new";
//
////    private static final String HELPTEXT_VIEW_NAME = BASE_PATH + "helptext";
////    private static final String VIEW_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_view";
////    private static final String MOD_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_mod";
//
//    /* Key which identifies team payload in Model */
//    public static final String TEAM_MODEL_KEY = "team";
//    private static final String TEAMLIST_MODEL_KEY = "teams";
//
//    /***************
//     * NEW-FORM    *
//     * *************/
//    @RequestMapping(value = "/ui/team/new", method = RequestMethod.GET)
//    public String ShowHelpTextNEwPage(ModelMap model) {
//        TeamDto teanDto = new TeamDto();
//        model.addAttribute(this.TEAM_MODEL_KEY, teanDto);
//
//        return this.NEW_TEAM_VIEW_NAME;
//    }
//
//    /***************
//     * NEW: SAVE   *
//     * *************/
//    @RequestMapping(value = "/ui/team", method = RequestMethod.POST)
//    public String HelpTextPost(@ModelAttribute(TEAM_MODEL_KEY) @Valid TeamDto teamDto
//            , BindingResult bindingResult, ModelMap model) {
//
//        if (bindingResult.hasErrors()) {
//            return this.NEW_TEAM_VIEW_NAME;
//        }
//
//        try {
//
//            teamService.create(teamDto);
//            return "redirect:/ui/welcome";
//
//        } catch (RestClientException ex) {
//
//            model.addAttribute("errMsg", RestServiceErrorMsg);
//            return this.NEW_TEAM_VIEW_NAME;
//        }
//
//    }
//
//}

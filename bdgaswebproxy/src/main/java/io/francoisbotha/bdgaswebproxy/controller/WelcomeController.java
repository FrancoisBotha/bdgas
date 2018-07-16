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
import io.francoisbotha.bdgaswebproxy.domain.dto.TeamDto;
import io.francoisbotha.bdgaswebproxy.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Controller
public class WelcomeController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private TeamService teamService;

    private static final String BASE_PATH = "/pages/ui/welcome/";
    private static final String WELCOME_VIEW_NAME = BASE_PATH + "welcome";

//    private static final String HELPTEXT_VIEW_NAME = BASE_PATH + "helptext";
//    private static final String VIEW_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_view";
//    private static final String MOD_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_mod";

    /* Key which identifies team payload in Model */
    public static final String TEAM_MODEL_KEY = "selectedTeam";
    private static final String TEAMLIST_MODEL_KEY = "teams";


    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/ui/welcome", method = RequestMethod.GET)
    public String ShowWelcomePage(Model model) {

        try {

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

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

            List teams = teamService.getAll();
            model.addAttribute(TEAMLIST_MODEL_KEY, teams);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.WELCOME_VIEW_NAME;
    }


}



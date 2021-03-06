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

import org.bdgas.webproxy.domain.dto.HelpTextDto;
import org.bdgas.webproxy.services.HelpTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import java.security.Principal;

@Slf4j
@Controller
public class AdminController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private HelpTextService helpTextService;

    private static final String BASE_PATH = "pages/admin/help/";
    private static final String HELP_VIEW_NAME = BASE_PATH + "help";

    private static final String HELPTEXT_MODEL_KEY = "helpText";

    private static final String USERNAME_MODEL_KEY = "userName";

    @RequestMapping(value = "/admin/help", method = RequestMethod.GET)
    public String ShowAdminPage(Principal principal,
                                Model model) {

        try {

            HelpTextDto helpTextDto = helpTextService.getOneByName("SystemAdmin");
            model.addAttribute(HELPTEXT_MODEL_KEY, helpTextDto);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);


        } catch (RestClientException ex) {
            log.debug("Error in AminController: " + ex);
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.HELP_VIEW_NAME;
    }

}


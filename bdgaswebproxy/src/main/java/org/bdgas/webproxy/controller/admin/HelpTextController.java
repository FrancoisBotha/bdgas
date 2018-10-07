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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class HelpTextController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private HelpTextService helpTextService;

    private static final String BASE_PATH = "pages/admin/helptext/";
    private static final String HELPTEXT_VIEW_NAME = BASE_PATH + "helptext";
    private static final String VIEW_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_view";
    private static final String NEW_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_new";
    private static final String MOD_HELPTEXT_VIEW_NAME = BASE_PATH + "helptext_mod";

    /* Key which identifies helpText payload in Model */
    public static final String HELPTEXT_MODEL_KEY = "helpText";
    private static final String HELPTEXTLIST_MODEL_KEY = "helpTexts";

    private static final String USERNAME_MODEL_KEY = "userName";

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/helptext", method = RequestMethod.GET)
    public String ShowHelpTextPage(Principal principal,
                                   Model model) {

        try {

            List helpTexts = helpTextService.getAll();
            model.addAttribute(HELPTEXTLIST_MODEL_KEY, helpTexts);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.HELPTEXT_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/helptext/{id}", method = RequestMethod.GET)
    public String ViewVendor(Principal principal,
                             Model model,
                             @PathVariable("id") String id) {

        try {

            HelpTextDto helpText = helpTextService.getOne(id);
            model.addAttribute(HELPTEXT_MODEL_KEY, helpText);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_HELPTEXT_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/helptext/new", method = RequestMethod.GET)
    public String ShowHelpTextNEwPage(Principal principal,
                                      ModelMap model) {
        HelpTextDto helptTextDto = new HelpTextDto();
        model.addAttribute(this.HELPTEXT_MODEL_KEY , helptTextDto);

        String userName = principal.getName();
        model.addAttribute(USERNAME_MODEL_KEY, userName);

        return this.NEW_HELPTEXT_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/helptext", method = RequestMethod.POST)
    public String HelpTextPost(Principal principal,
                               @ModelAttribute(HELPTEXT_MODEL_KEY) @Valid HelpTextDto helpTextDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return HelpTextController.NEW_HELPTEXT_VIEW_NAME;
        }

        try {

            helpTextService.create(helpTextDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return HelpTextController.NEW_HELPTEXT_VIEW_NAME;
        }

        return "redirect:/admin/helptext";
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/helptext/mod/{id}", method = RequestMethod.GET)
    public String modHelpText(Principal principal,
                              Model model,
                            @PathVariable("id") String id) {

        try {

            HelpTextDto helpText = helpTextService.getOne(id);
            model.addAttribute(HELPTEXT_MODEL_KEY, helpText);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_HELPTEXT_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/helptext/mod/{id}", method = RequestMethod.POST)
    public String HelpTextModSave(@ModelAttribute(HELPTEXT_MODEL_KEY) @Valid HelpTextDto helpTextDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return HelpTextController.MOD_HELPTEXT_VIEW_NAME;
        }

        try {

            helpTextService.modify(helpTextDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return HelpTextController.MOD_HELPTEXT_VIEW_NAME;
        }

        return "redirect:/admin/helptext/" + id;
    }
    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/helptext/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteHelpText(Model model,
                      @PathVariable("id") String id) {

        try {

            helpTextService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}
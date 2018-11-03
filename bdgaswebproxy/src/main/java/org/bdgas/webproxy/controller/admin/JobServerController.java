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

import org.bdgas.webproxy.domain.dto.JobServerDto;
import org.bdgas.webproxy.services.JobServerService;
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
public class JobServerController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private JobServerService jobServerService;

    private static final String BASE_PATH = "pages/admin/jobserver/";
    private static final String JOBSERVER_VIEW_NAME = BASE_PATH + "jobserver";
    private static final String VIEW_JOBSERVER_VIEW_NAME = BASE_PATH + "jobserver_view";
    private static final String NEW_JOBSERVER_VIEW_NAME = BASE_PATH + "jobserver_new";
    private static final String MOD_JOBSERVER_VIEW_NAME = BASE_PATH + "jobserver_mod";

    /* Key which identifies jobServer payload in Model */
    public static final String JOBSERVER_MODEL_KEY = "jobServer";
    private static final String JOBSERVERLIST_MODEL_KEY = "jobServers";

    private static final String USERNAME_MODEL_KEY = "userName";

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/jobserver", method = RequestMethod.GET)
    public String ShowJobServerPage(Principal principal,
                                   Model model) {

        try {

            List jobServers = jobServerService.getAll();
            model.addAttribute(JOBSERVERLIST_MODEL_KEY, jobServers);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.JOBSERVER_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/jobserver/{id}", method = RequestMethod.GET)
    public String ViewVendor(Principal principal,
                             Model model,
                             @PathVariable("id") String id) {

        try {

            JobServerDto jobServer = jobServerService.getOne(id);
            model.addAttribute(JOBSERVER_MODEL_KEY, jobServer);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_JOBSERVER_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/jobserver/new", method = RequestMethod.GET)
    public String ShowJobServerNEwPage(Principal principal,
                                      ModelMap model) {
        JobServerDto helptTextDto = new JobServerDto();
        model.addAttribute(this.JOBSERVER_MODEL_KEY , helptTextDto);

        String userName = principal.getName();
        model.addAttribute(USERNAME_MODEL_KEY, userName);

        return this.NEW_JOBSERVER_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/jobserver", method = RequestMethod.POST)
    public String JobServerPost(Principal principal,
                               @ModelAttribute(JOBSERVER_MODEL_KEY) @Valid JobServerDto jobServerDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);
            return JobServerController.NEW_JOBSERVER_VIEW_NAME;
        }

        try {

            jobServerService.create(jobServerDto);

        } catch (RestClientException ex) {
            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return JobServerController.NEW_JOBSERVER_VIEW_NAME;
        }

        return "redirect:/admin/jobserver";
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/jobserver/mod/{id}", method = RequestMethod.GET)
    public String modJobServer(Principal principal,
                              Model model,
                              @PathVariable("id") String id) {

        try {

            JobServerDto jobServer = jobServerService.getOne(id);
            model.addAttribute(JOBSERVER_MODEL_KEY, jobServer);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_JOBSERVER_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/jobserver/mod/{id}", method = RequestMethod.POST)
    public String JobServerModSave(@ModelAttribute(JOBSERVER_MODEL_KEY) @Valid JobServerDto jobServerDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return JobServerController.MOD_JOBSERVER_VIEW_NAME;
        }

        try {

            jobServerService.modify(jobServerDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return JobServerController.MOD_JOBSERVER_VIEW_NAME;
        }

        return "redirect:/admin/jobserver/" + id;
    }
    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/jobserver/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteJobServer(Model model,
                                             @PathVariable("id") String id) {

        try {

            jobServerService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}

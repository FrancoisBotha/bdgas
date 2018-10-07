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

import org.bdgas.webproxy.domain.dto.TaskDto;
import org.bdgas.webproxy.services.TaskService;
import org.bdgas.webproxy.services.CodeTableService;
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
public class TaskController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CodeTableService codeTableService;

    private static final String BASE_PATH = "pages/admin/task/";
    private static final String TASK_VIEW_NAME = BASE_PATH + "task";
    private static final String VIEW_TASK_VIEW_NAME = BASE_PATH + "task_view";
    private static final String NEW_TASK_VIEW_NAME = BASE_PATH + "task_new";
    private static final String MOD_TASK_VIEW_NAME = BASE_PATH + "task_mod";

    /* Key which identifies helpText payload in Model */
    public static final String TASK_MODEL_KEY = "task";
    private static final String TASKLIST_MODEL_KEY = "tasks";

    private static final String TASKTYPES_MODEL_KEY = "taskTypes";

    private static final String CODETABLE_TASKTYPES = "01";

    private static final String USERNAME_MODEL_KEY = "userName";


    /***********
     * LIST    *
     ***********/
    @RequestMapping(value = "/admin/task", method = RequestMethod.GET)
    public String ShowTaskPage(Principal principal,
                               Model model) {

        try {

            List tasks = taskService.getAll();
            model.addAttribute(TASKLIST_MODEL_KEY, tasks);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.TASK_VIEW_NAME;

    }

    /***********
     * VIEW    *
     ***********/
    @RequestMapping(value = "/admin/task/{id}", method = RequestMethod.GET)
    public String ViewVendor(Principal principal,
                             Model model,
                             @PathVariable("id") String id) {

        try {

            TaskDto taskDto = taskService.getOne(id);
            model.addAttribute(TASK_MODEL_KEY, taskDto);

            List taskTypes = codeTableService.getCodeTablesForNr(CODETABLE_TASKTYPES);
            model.addAttribute(TASKTYPES_MODEL_KEY, taskTypes);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_TASK_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     ***************/
    @RequestMapping(value = "/admin/task/new", method = RequestMethod.GET)
    public String ShowTaskNEwPage(Principal principal,
                                  ModelMap model) {
        TaskDto taskDto = new TaskDto();
        model.addAttribute(this.TASK_MODEL_KEY , taskDto);

        List taskTypes = codeTableService.getCodeTablesForNr(CODETABLE_TASKTYPES);
        model.addAttribute(TASKTYPES_MODEL_KEY, taskTypes);

        String userName = principal.getName();
        model.addAttribute(USERNAME_MODEL_KEY, userName);

        return this.NEW_TASK_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     ***************/
    @RequestMapping(value = "/admin/task", method = RequestMethod.POST)
    public String TaskPost(@ModelAttribute(TASK_MODEL_KEY) @Valid TaskDto taskDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return TaskController.NEW_TASK_VIEW_NAME;
        }

        try {

            taskService.create(taskDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return TaskController.NEW_TASK_VIEW_NAME;
        }

        return "redirect:/admin/task";
    }

    /***************
     * MOD-FORM    *
     ***************/
    @RequestMapping(value = "/admin/task/mod/{id}", method = RequestMethod.GET)
    public String modTask(Principal principal,
                          Model model,
                              @PathVariable("id") String id) {

        try {

            List taskTypes = codeTableService.getCodeTablesForNr(CODETABLE_TASKTYPES);
            model.addAttribute(TASKTYPES_MODEL_KEY, taskTypes);

            TaskDto helpText = taskService.getOne(id);
            model.addAttribute(TASK_MODEL_KEY, helpText);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_TASK_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     ***************/
    @RequestMapping(value = "/admin/task/mod/{id}", method = RequestMethod.POST)
    public String TaskModSave(@ModelAttribute(TASK_MODEL_KEY) @Valid TaskDto helpTextDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return TaskController.MOD_TASK_VIEW_NAME;
        }

        try {

            taskService.modify(helpTextDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return TaskController.MOD_TASK_VIEW_NAME;
        }

        return "redirect:/admin/task/" + id;
    }
    
    /***************
     * DELETE      *
     ***************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/task/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteTask(Model model,
                                             @PathVariable("id") String id) {

        try {

            taskService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}
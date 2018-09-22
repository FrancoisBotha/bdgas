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

import io.francoisbotha.bdgaswebproxy.domain.dto.LocalDataSourceDto;
import io.francoisbotha.bdgaswebproxy.services.LocalDataSourceService;
import io.francoisbotha.bdgaswebproxy.services.CodeTableService;
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
import java.util.List;

@Slf4j
@Controller
public class LocalDataSourcesController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private LocalDataSourceService localDataSourceService;

    @Autowired
    private CodeTableService codeTableService;

    private static final String BASE_PATH = "pages/admin/localdatasource/";
    private static final String LOCALDATASOURCE_VIEW_NAME = BASE_PATH + "localdatasource";
    private static final String NEW_LOCALDATASOURCE_VIEW_NAME = BASE_PATH + "localdatasource_new";

    /* Key which identifies helpText payload in Model */
    public static final String LOCALDATASOURCE_MODEL_KEY = "localdatasource";
    private static final String LOCALDATASOURCELIST_MODEL_KEY = "localdatasources";

    /***********
     * LIST    *
     ***********/
    @RequestMapping(value = "/admin/localdatasource", method = RequestMethod.GET)
    public String ShowTaskPage(Model model) {

        try {

            List localdatasources = localDataSourceService.getAll();
            model.addAttribute(LOCALDATASOURCELIST_MODEL_KEY, localdatasources);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.LOCALDATASOURCE_VIEW_NAME;

    }


    /***************
     * NEW-FORM    *
     ***************/
    @RequestMapping(value = "/admin/localdatasource/new", method = RequestMethod.GET)
    public String ShowTaskNEwPage(ModelMap model) {
        LocalDataSourceDto localdatasourceDto = new LocalDataSourceDto();
        model.addAttribute(this.LOCALDATASOURCE_MODEL_KEY , localdatasourceDto);


        return this.NEW_LOCALDATASOURCE_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     ***************/
    @RequestMapping(value = "/admin/localdatasource", method = RequestMethod.POST)
    public String TaskPost(@ModelAttribute(LOCALDATASOURCE_MODEL_KEY) @Valid LocalDataSourceDto localdatasourceDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return this.NEW_LOCALDATASOURCE_VIEW_NAME;
        }

        try {

            localDataSourceService.create(localdatasourceDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_LOCALDATASOURCE_VIEW_NAME;
        }

        return "redirect:/admin/localdatasource";
    }

    /***************
     * DELETE      *
     ***************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/localdatasource/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteTask(Model model,
                                         @PathVariable("id") String id) {

        try {

            localDataSourceService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}

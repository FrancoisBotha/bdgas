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
import org.bdgas.webproxy.domain.dto.CodeTypeDto;
import org.bdgas.webproxy.services.CodeTableService;
import org.bdgas.webproxy.services.CodeTypeService;
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
public class CodeTableController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private CodeTypeService codeTypeService;

    @Autowired
    private CodeTableService codeTableService;

    private static final String BASE_PATH = "pages/admin/codes/";
    private static final String CODETYPES_VIEW_NAME = BASE_PATH + "codetypes";
    private static final String VIEW_CODETYPE_VIEW_NAME = BASE_PATH + "codetypes_view";
    private static final String NEW_CODETYPE_VIEW_NAME = BASE_PATH + "codetypes_new";
    private static final String MOD_CODETYPE_VIEW_NAME = BASE_PATH + "codetypes_mod";

    private static final String CODETABLES_VIEW_NAME = BASE_PATH + "codetables";
    private static final String VIEW_CODETABLE_VIEW_NAME = BASE_PATH + "codetables_view";
    private static final String NEW_CODETABLE_VIEW_NAME = BASE_PATH + "codetables_new";
    private static final String MOD_CODETABLE_VIEW_NAME = BASE_PATH + "codetables_mod";


    /* Key which identifies helpText payload in Model */
    public static final String CODETYPE_MODEL_KEY = "codeType";
    private static final String CODETYPELIST_MODEL_KEY = "codeTypes";

    public static final String CODETABLE_MODEL_KEY = "codeTable";
    private static final String CODETABLELIST_MODEL_KEY = "codeTables";

    private static final String USERNAME_MODEL_KEY = "userName";


    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/codetype", method = RequestMethod.GET)
    public String ShowCodeTypesPage(Principal principal,
                                    Model model) {

        try {

            List codeTypes = codeTypeService.getAll();
            model.addAttribute(CODETYPELIST_MODEL_KEY, codeTypes);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.CODETYPES_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/codetype/{id}", method = RequestMethod.GET)
    public String ViewVendor(Principal principal,
                             Model model,
                             @PathVariable("id") String id) {
        try {

            CodeTypeDto codeTypeDto = codeTypeService.getOne(id);
            model.addAttribute(CODETYPE_MODEL_KEY, codeTypeDto);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);


        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_CODETYPE_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/codetype/new", method = RequestMethod.GET)
    public String ShowCodeTypeNewPage(Principal principal,
                                      ModelMap model) {
        CodeTypeDto codeTypeDto = new CodeTypeDto();
        model.addAttribute(this.CODETYPE_MODEL_KEY , codeTypeDto);

        String userName = principal.getName();
        model.addAttribute(USERNAME_MODEL_KEY, userName);

        return this.NEW_CODETYPE_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/codetype", method = RequestMethod.POST)
    public String HelpTextPost(Principal principal,
                               @ModelAttribute(CODETYPELIST_MODEL_KEY) @Valid CodeTypeDto codeTypeDto
                               , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return this.NEW_CODETYPE_VIEW_NAME;
        }

        try {

            codeTypeService.create(codeTypeDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_CODETYPE_VIEW_NAME;
        }

        return "redirect:/admin/codetype";
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/codetype/mod/{id}", method = RequestMethod.GET)
    public String modHelpText(Principal principal,
                              Model model,
                              @PathVariable("id") String id) {

        try {

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

            CodeTypeDto codeTypeDto = codeTypeService.getOne(id);
            model.addAttribute(CODETYPE_MODEL_KEY, codeTypeDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_CODETYPE_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/codetype/mod/{id}", method = RequestMethod.POST)
    public String HelpTextModSave(@ModelAttribute(CODETYPE_MODEL_KEY) @Valid CodeTypeDto codeTypeDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return this.MOD_CODETYPE_VIEW_NAME;
        }

        try {

            codeTypeService.modify(codeTypeDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.MOD_CODETYPE_VIEW_NAME;
        }

        return "redirect:/admin/codetype/" + id;
    }
    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/codetype/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteHelpText(Model model,
                                             @PathVariable("id") String id) {

        try {

            codeTypeService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }

    /***************
     * CODE TABLES *
     ***************/

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/codetype/codetable/{id}", method = RequestMethod.GET)
    public String ShowCodeTablePage(Principal principal,
                                    Model model,
                                    @PathVariable("id") String id) {

        try {

            CodeTypeDto codeTypeDto = codeTypeService.getOne(id);
            model.addAttribute(CODETYPE_MODEL_KEY, codeTypeDto);

            List codeTables = codeTableService.getCodeTablesForType(id);
            model.addAttribute(CODETABLELIST_MODEL_KEY, codeTables);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);

        }

        return this.CODETABLES_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/codetable/{tableId}", method = RequestMethod.GET)
    public String ViewCodeTable(Principal principal,
                                Model model,
                             @PathVariable("tableId") String tableId) {
        try {

            CodeTableDto codeTableDto = codeTableService.getOne(tableId);
            model.addAttribute(CODETABLE_MODEL_KEY, codeTableDto);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_CODETABLE_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/codetype/codetable/{id}/new", method = RequestMethod.GET)
    public String ShowCodeTableNewPage(Principal principal,
                                       ModelMap model,
                                       @PathVariable("id") String id) {

        CodeTypeDto codeTypeDto = codeTypeService.getOne(id);
        model.addAttribute(CODETYPE_MODEL_KEY, codeTypeDto);

        String userName = principal.getName();
        model.addAttribute(USERNAME_MODEL_KEY, userName);

        log.info(codeTypeDto.toString());

        CodeTableDto codeTableDto = new CodeTableDto();
        codeTableDto.setCdeTypeId(id);
        model.addAttribute(this.CODETABLE_MODEL_KEY , codeTableDto);

        return this.NEW_CODETABLE_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/codetype/codetable", method = RequestMethod.POST)
    public String CodeTablePost(@ModelAttribute(CODETABLE_MODEL_KEY) @Valid CodeTableDto codeTableDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return this.NEW_CODETABLE_VIEW_NAME;
        }

        try {

            codeTableService.create(codeTableDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.NEW_CODETABLE_VIEW_NAME;
        }

        return "redirect:/admin/codetype/codetable/" + codeTableDto.getCdeTypeId();
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/codetable/mod/{id}", method = RequestMethod.GET)
    public String modCodeTable(Principal principal,
                               Model model,
                              @PathVariable("id") String id) {
        try {

            CodeTableDto codeTableDto = codeTableService.getOne(id);
            model.addAttribute(CODETABLE_MODEL_KEY, codeTableDto);

            String userName = principal.getName();
            model.addAttribute(USERNAME_MODEL_KEY, userName);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_CODETABLE_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/codetable/mod/{id}", method = RequestMethod.POST)
    public String ModCodeTableSave(@ModelAttribute(CODETABLE_MODEL_KEY) @Valid CodeTableDto codeTableDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return this.MOD_CODETABLE_VIEW_NAME;
        }

        try {

            codeTableService.modify(codeTableDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return this.MOD_CODETABLE_VIEW_NAME;
        }

        return "redirect:/admin/codetable/" + id;
    }

    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/codetable/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteCodeTable(Model model,
                                             @PathVariable("id") String id) {

        try {

            codeTableService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }

}
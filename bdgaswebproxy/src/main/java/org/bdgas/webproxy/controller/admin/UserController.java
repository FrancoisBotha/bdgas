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

import org.bdgas.webproxy.domain.dto.UserDto;
import org.bdgas.webproxy.services.UserService;
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
public class UserController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private UserService userService;

    private static final String BASE_PATH = "pages/admin/users/";
    private static final String USER_VIEW_NAME = BASE_PATH + "user";
    private static final String VIEW_USER_VIEW_NAME = BASE_PATH + "user_view";
    private static final String NEW_USER_VIEW_NAME = BASE_PATH + "user_new";
    private static final String MOD_USER_VIEW_NAME = BASE_PATH + "user_mod";

    /* Key which identifies user payload in Model */
    public static final String USER_MODEL_KEY = "user";
    private static final String USERLIST_MODEL_KEY = "users";

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String ShowUserPage(Model model) {

        try {

            List users = userService.getAll();
            model.addAttribute(USERLIST_MODEL_KEY, users);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.USER_VIEW_NAME;

    }

    /***********
     * VIEW    *
     * *********/
    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public String ViewVendor(Model model,
                             @PathVariable("id") String id) {

        try {

            UserDto user = userService.getOne(id);
            model.addAttribute(USER_MODEL_KEY, user);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.VIEW_USER_VIEW_NAME;

    }

    /***************
     * NEW-FORM    *
     * *************/
    @RequestMapping(value = "/admin/user/new", method = RequestMethod.GET)
    public String ShowUserNEwPage(ModelMap model) {
        UserDto helptTextDto = new UserDto();
        model.addAttribute(this.USER_MODEL_KEY , helptTextDto);

        return this.NEW_USER_VIEW_NAME;
    }

    /***************
     * NEW: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/user", method = RequestMethod.POST)
    public String UserPost(@ModelAttribute(USER_MODEL_KEY) @Valid UserDto userDto
            , BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return UserController.NEW_USER_VIEW_NAME;
        }

        try {

            userService.create(userDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return UserController.NEW_USER_VIEW_NAME;
        }

        return "redirect:/admin/user";
    }

    /***************
     * MOD-FORM    *
     * *************/
    @RequestMapping(value = "/admin/user/mod/{id}", method = RequestMethod.GET)
    public String modUser(Model model,
                              @PathVariable("id") String id) {

        try {

            UserDto user = userService.getOne(id);
            model.addAttribute(USER_MODEL_KEY, user);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

        return this.MOD_USER_VIEW_NAME;

    }

    /***************
     * MOD: SAVE   *
     * *************/
    @RequestMapping(value = "/admin/user/mod/{id}", method = RequestMethod.POST)
    public String UserModSave(@ModelAttribute(USER_MODEL_KEY) @Valid UserDto userDto
            , BindingResult bindingResult, ModelMap model,
                                  @PathVariable("id") String id) {

        if (bindingResult.hasErrors()) {
            return UserController.MOD_USER_VIEW_NAME;
        }

        try {

            userService.modify(userDto);

        } catch (RestClientException ex) {

            model.addAttribute("errMsg", RestServiceErrorMsg);
            return UserController.MOD_USER_VIEW_NAME;
        }

        return "redirect:/admin/user/" + id;
    }
    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void DeleteUser(Model model,
                                         @PathVariable("id") String id) {

        try {

            userService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }
}

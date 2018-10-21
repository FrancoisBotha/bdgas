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
package org.bdgas.adminservice.api.v1.controller;

import org.bdgas.adminservice.domain.dto.UserDto;
import org.bdgas.adminservice.domain.model.User;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/user", method = RequestMethod.GET)
    public List getUsers () throws EntityNotFoundException {

        log.info("Get Users");

        return userService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.GET)
    public User getUser (@PathVariable("id") String id) throws EntityNotFoundException {

        return userService.getOne(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/user", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User AddUser(@RequestBody @Valid UserDto userDto )  {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAuthId(userDto.getAuthId());
        user.setOrgId("Default Org");
        return userService.create(user);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public User UpdateTask(@PathVariable("id") String id,
                           @RequestBody @Valid UserDto userDto )
            throws EntityNotFoundException  {
        return userService.update(id, userDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") String id) throws EntityNotFoundException  {
        userService.delete(id);
    }

}

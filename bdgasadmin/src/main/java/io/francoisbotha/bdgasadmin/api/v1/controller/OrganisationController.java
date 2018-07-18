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
package io.francoisbotha.bdgasadmin.api.v1.controller;

import io.francoisbotha.bdgasadmin.domain.dto.OrganisationDto;
import io.francoisbotha.bdgasadmin.domain.model.Organisation;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.OrganisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/organisation", method = RequestMethod.GET)
    public List getOrganisations () throws EntityNotFoundException {

        log.info("Get Organisations");

        return organisationService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/organisation/{id}", method = RequestMethod.GET)
    public List getOrganisations (@PathVariable("id") String id) throws EntityNotFoundException {

        log.info("Get Organisation");

        return organisationService.getAll(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/organisation", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Organisation AddOrganisation(@RequestBody @Valid OrganisationDto organisationDto )  {
        Organisation organisation = new Organisation();
        organisation.setName(organisationDto.getName());
        return organisationService.create(organisation);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/organisation/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrganisation(@PathVariable("id") String id) throws EntityNotFoundException  {
        organisationService.delete(id);
    }

}

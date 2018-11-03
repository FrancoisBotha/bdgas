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

import org.bdgas.adminservice.domain.dto.JobServerDto;
import org.bdgas.adminservice.domain.model.JobServer;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.JobServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class JobServerController {

    @Autowired
    JobServerService jobServerService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/jobserver", method = RequestMethod.GET)
    public List getJobServers () throws EntityNotFoundException {
        
        return jobServerService.getAll();

    }


    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/jobserver/{id}", method = RequestMethod.GET)
    public JobServer  getJobServer (@PathVariable("id") String id) throws EntityNotFoundException {

        return jobServerService.getOne(id);

    }


    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/jobserver", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public JobServer AddJobServer(@RequestBody @Valid JobServerDto jobServerDto )  {
        JobServer jobServer = new JobServer();
        jobServer.setJobServerURL(jobServerDto.getJobServerURL());
        jobServer.setDescription(jobServerDto.getDescription());
        jobServer.setActive(jobServerDto.getActive());

        return jobServerService.create(jobServer);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/jobserver/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public JobServer UpdateJobServer(@PathVariable("id") String id, @RequestBody @Valid JobServerDto jobServerDto )
            throws EntityNotFoundException  {
        return jobServerService.update(id, jobServerDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/jobserver/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteJobServer(@PathVariable("id") String id) throws EntityNotFoundException  {
        jobServerService.delete(id);
    }
}
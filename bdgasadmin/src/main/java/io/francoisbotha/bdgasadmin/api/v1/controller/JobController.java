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

import io.francoisbotha.bdgasadmin.domain.model.Job;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class JobController {

    @Autowired
    JobService jobService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/job", method = RequestMethod.GET)
    public List getJobs () throws EntityNotFoundException {

        return jobService.getAll();

    }

    /************
     * GET One  *
     ************/
    @RequestMapping(value = "/api/v1/job/{id}", method = RequestMethod.GET)
    public Job getJob (@PathVariable("id") String id) throws EntityNotFoundException {

        return jobService.getOne(id);

    }

    /********************
     * GET FOR CODE TYPE *
     ********************/
    @RequestMapping(value = "/api/v1/job/workingpaper/{id}", method = RequestMethod.GET)
    public List getCodesForType (@PathVariable("id") String id) throws EntityNotFoundException {

        return jobService.getJobsForWp(id);
    }
}

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

import io.francoisbotha.bdgasadmin.domain.dto.WorkingPaperDto;
import io.francoisbotha.bdgasadmin.domain.model.WorkingPaper;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.WorkingPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class WorkingPaperController  {

    @Autowired
    WorkingPaperService workingPaperService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/workingpaper", method = RequestMethod.GET)
    public List getWorkingPapers () throws EntityNotFoundException {

        return workingPaperService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/workingpaper/{id}", method = RequestMethod.GET)
    public WorkingPaper getWorkingPapers (@PathVariable("id") String id) throws EntityNotFoundException {

        return workingPaperService.getOne(id);

    }

    /********************
     * GET FOR PROJECT  *
     ********************/
    @RequestMapping(value = "/api/v1/workingpaper/project/{id}", method = RequestMethod.GET)
    public List getWorkingPapersForProject (@PathVariable("id") String id) throws EntityNotFoundException {

        return workingPaperService.getProjectWorkingPapers(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/workingpaper", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WorkingPaper AddWorkingPaper(@RequestBody @Valid WorkingPaperDto workingPaperDto )  {
        WorkingPaper workingPaper = new WorkingPaper();
        workingPaper.setName(workingPaperDto.getName());
        workingPaper.setProjectId(workingPaperDto.getProjectId());
        workingPaper.setLineCount(0);
        return workingPaperService.create(workingPaper);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/workingPaper/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public WorkingPaper UpdateWorkingPaper(@PathVariable("id") String id, @RequestBody @Valid WorkingPaperDto workingPaperDto )
            throws EntityNotFoundException  {
        return workingPaperService.update(id, workingPaperDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/workingPaper/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteWorkingPaper(@PathVariable("id") String id) throws EntityNotFoundException  {
        workingPaperService.delete(id);
    }

}

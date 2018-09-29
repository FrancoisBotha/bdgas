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

import io.francoisbotha.bdgasadmin.domain.dto.WpLineDto;
import io.francoisbotha.bdgasadmin.domain.model.WpLine;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.error.SjsException;
import io.francoisbotha.bdgasadmin.services.WorkingPaperService;
import io.francoisbotha.bdgasadmin.services.WpLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class  WpLineController  {

    @Autowired
    WpLineService wpLineService;

    @Autowired
    WorkingPaperService workingPaperService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/wpline", method = RequestMethod.GET)
    public List getWpLines () throws EntityNotFoundException {

        return wpLineService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/wpline/{id}", method = RequestMethod.GET)
    public WpLine getWpLines (@PathVariable("id") String id) throws EntityNotFoundException {

        return wpLineService.getWpLine(id);

    }

    /*************************
     * GET FOR WORKING PAPER *
     *************************/
    @RequestMapping(value = "/api/v1/wpline/workingpaper/{id}", method = RequestMethod.GET)
    public List getWpLinesForProject (@PathVariable("id") String id) throws EntityNotFoundException {

        return wpLineService.getWorkingPaperLines(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/wpline", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WpLine AddWpLine(@RequestBody @Valid WpLineDto wpLineDto )
            throws HttpStatusCodeException, EntityNotFoundException, SjsException  {

        WpLine wpLine = new WpLine();

        wpLine.setWpId(wpLineDto.getWpId());
        wpLine.setTaskId(wpLineDto.getTaskId());
        wpLine.setTaskCde(wpLineDto.getTaskCde());
        wpLine.setTaskParams(wpLineDto.getTaskParams());
        wpLine.setTaskDesc(wpLineDto.getTaskDesc());
        wpLine.setLnState(wpLineDto.getLnState());

        WpLine returnWpLine = wpLineService.create(wpLine);

        wpLine.setLnNo(workingPaperService.incrLineCount(wpLineDto.getWpId()));

        return returnWpLine;
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/wpline/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public WpLine UpdateWpLine(@PathVariable("id") String id, @RequestBody @Valid WpLineDto workingPaperDto )
            throws EntityNotFoundException  {
        return wpLineService.update(id, workingPaperDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/wpline/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteWpLine(@PathVariable("id") String id) throws EntityNotFoundException  {

        WpLine wpLine = wpLineService.getWpLine(id);
        workingPaperService.decrLineCount(wpLine.getWpId());
        wpLineService.delete(id);
    }

}

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

import io.francoisbotha.bdgasadmin.domain.dto.LocalDataSourceDto;
import io.francoisbotha.bdgasadmin.domain.model.LocalDataSource;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.LocalDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class LocalDataSourceController {

    @Autowired
    LocalDataSourceService localDataSourceService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/localdatasource", method = RequestMethod.GET)
    public List getLocalDataSources () throws EntityNotFoundException {

        return localDataSourceService.getAll();

    }


    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/localdatasource/{id}", method = RequestMethod.GET)
    public LocalDataSource  getLocalDataSource (@PathVariable("id") String id) throws EntityNotFoundException {

        return localDataSourceService.getOne(id);

    }


    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/localdatasource", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LocalDataSource AddLocalDataSource(@RequestBody @Valid LocalDataSourceDto localDataSourceDto )  {
        LocalDataSource localDataSource = new LocalDataSource();
        localDataSource.setFileName(localDataSourceDto.getFileName());
        return localDataSourceService.create(localDataSource);
    }


    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/localdatasource/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocalDataSource(@PathVariable("id") String id) throws EntityNotFoundException  {
        localDataSourceService.delete(id);
    }
}


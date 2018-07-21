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

import io.francoisbotha.bdgasadmin.domain.dto.DataSourceDto;
import io.francoisbotha.bdgasadmin.domain.model.DataSource;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class DataSourceController{

    @Autowired
    DataSourceService dataSourceService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/datasource", method = RequestMethod.GET)
    public List getDataSources () throws EntityNotFoundException {

        return dataSourceService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/datasource/{id}", method = RequestMethod.GET)
    public DataSource getDataSources (@PathVariable("id") String id) throws EntityNotFoundException {

        return dataSourceService.getOne(id);

    }

    /********************
     * GET FOR TEAM  *
     ********************/
    @RequestMapping(value = "/api/v1/datasource/team/{id}", method = RequestMethod.GET)
    public List getDataSourcesForTeam (@PathVariable("id") String id) throws EntityNotFoundException {

        return dataSourceService.getTeamDataSources(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/datasource", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DataSource AddDataSource(@RequestBody @Valid DataSourceDto dataSourceDto )  {
        DataSource dataSource = new DataSource();
        dataSource.setFileName(dataSourceDto.getFileName());
        dataSource.setObjectKey(dataSourceDto.getObjectKey());
        dataSource.setTeamId(dataSourceDto.getTeamId());
        return dataSourceService.create(dataSource);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/datasource/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteDataSource(@PathVariable("id") String id) throws EntityNotFoundException  {
        dataSourceService.delete(id);
    }

}
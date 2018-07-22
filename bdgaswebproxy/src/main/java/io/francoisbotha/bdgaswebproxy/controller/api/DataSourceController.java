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
package io.francoisbotha.bdgaswebproxy.controller.api;

import io.francoisbotha.bdgaswebproxy.domain.dto.DataSourceDto;
import io.francoisbotha.bdgaswebproxy.services.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class DataSourceController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private DataSourceService dataSourceService;

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/api/v1/datasource", method = RequestMethod.GET)
    public List GetDataSources(Model model) {

        try {

            return dataSourceService.getAll();

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;

    }


    /***********
     * ADD    *
     * *********/
    @RequestMapping(value = "/api/v1/datasource", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DataSourceDto getSignedUrl(@RequestParam String teamId,
                                      @RequestParam String fileName,
                                      @RequestParam String objectKey) {

        try {

            DataSourceDto dataSourceDto = new DataSourceDto();
            dataSourceDto.setTeamId(teamId);
            dataSourceDto.setFileName(fileName);
            dataSourceDto.setObjectKey(objectKey);

            DataSourceDto dataSourceDtoReturn = dataSourceService.create(dataSourceDto);
            return dataSourceDtoReturn;

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/api/v1/datasource/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void DeleteDataSource(Model model,
                                             @PathVariable("id") String id) {

        try {

            dataSourceService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }

}

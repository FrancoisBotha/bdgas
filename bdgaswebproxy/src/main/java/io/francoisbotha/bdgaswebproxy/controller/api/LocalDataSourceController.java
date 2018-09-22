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

import io.francoisbotha.bdgaswebproxy.services.LocalDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@RestController
public class LocalDataSourceController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private LocalDataSourceService localDataSourceService;

    /***********
     * LIST    *
     * *********/
    @RequestMapping(value = "/api/v1/localdatasource", method = RequestMethod.GET)
    public List GetDataSources(Model model) {

        try {

            return localDataSourceService.getAll();

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;

    }




}

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
package org.bdgas.webproxy.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bdgas.webproxy.domain.dto.WpLineDto;
import org.bdgas.webproxy.error.EntityNotFoundException;
import org.bdgas.webproxy.error.SjsException;
import org.bdgas.webproxy.services.WpLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class WpLineController {

    @Value("${restservice.error.defaultmsg}")
    private String RestServiceErrorMsg;

    @Autowired
    private WpLineService wpLineService;

    public static final String WPLINE_MODEL_KEY = "wpLine";

    /***********
     * LIST    *
     ***********/
    @RequestMapping(value = "/api/v1/wpline", method = RequestMethod.GET)
    public List GetWpLines(Model model) {

        try {

            return wpLineService.getAll();

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    /*************************
     * GET FOR WORKINGPAPER  *
     *************************/
    @RequestMapping(value = "/api/v1/wpline/workingpaper/{id}", method = RequestMethod.GET)
    public List GetWpLinesForWP(Model model,
                                @PathVariable("id") String id) {

        try {

            return wpLineService.getWorkingPaperLines(id);

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    /***********
     * ADD    *
     **********/
    @RequestMapping(value = "/api/v1/wpline", method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public WpLineDto wpLinePost(@RequestBody String wpLine)
            throws EntityNotFoundException, SjsException, IOException, HttpServerErrorException {

        /*Map input parameter to DTO*/
        WpLineDto wpLineDto = new WpLineDto();
        ObjectMapper mapper = new ObjectMapper();
        wpLineDto = mapper.readValue(wpLine, WpLineDto.class);

        log.info("AAAAAAAAAAAAA" + wpLineDto.getTaskParams());

        WpLineDto wpLineDtoReturn = wpLineService.create(wpLineDto);
        return wpLineDtoReturn;

//        log.info("ADMIN SERVICE RESULT ");
//        log.info(wpLineDtoReturn.toString());

    }

    /***************
     * DELETE      *
     * *************/
    //Used response body because ajax used to delete
    @RequestMapping(value = "/api/v1/wpline/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void DeleteDataSource(Model model,
                                               @PathVariable("id") String id) {

        try {

            wpLineService.delete(id);

        } catch (RestClientException ex) {

            //TODO: some error handling here...
            model.addAttribute("errMsg", RestServiceErrorMsg);
        }

    }

}

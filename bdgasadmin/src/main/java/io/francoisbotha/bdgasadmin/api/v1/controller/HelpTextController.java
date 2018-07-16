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

import io.francoisbotha.bdgasadmin.domain.dto.HelpTextDto;
import io.francoisbotha.bdgasadmin.domain.model.HelpText;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.services.HelpTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class HelpTextController {

    @Autowired
    HelpTextService helpTextService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/helptext", method = RequestMethod.GET)
    public List getHelpTexts () throws EntityNotFoundException {

        log.info("Get HelpTexts");

        return helpTextService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/helptext/{id}", method = RequestMethod.GET)
    public HelpText getHelpTexts (@PathVariable("id") String id) throws EntityNotFoundException {

        log.info("Get HelpText");

        return helpTextService.getOne(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/helptext", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HelpText AddHelpText(@RequestBody @Valid HelpTextDto helpTextDto )  {
        HelpText helpText = new HelpText();
        helpText.setName(helpTextDto.getName());
        helpText.setLang(helpTextDto.getLang());
        helpText.setTxt(helpTextDto.getTxt());
        return helpTextService.create(helpText);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/helptext/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public HelpText UpdateHelpText(@PathVariable("id") String id, @RequestBody @Valid HelpTextDto helpTextDto )
            throws EntityNotFoundException  {
        log.debug(helpTextDto.toString());
        return helpTextService.update(id, helpTextDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/helptext/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteHelpText(@PathVariable("id") String id) throws EntityNotFoundException  {
        helpTextService.delete(id);
    }

}

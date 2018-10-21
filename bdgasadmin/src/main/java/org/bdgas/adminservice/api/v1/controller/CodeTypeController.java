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

import org.bdgas.adminservice.domain.dto.CodeTypeDto;
import org.bdgas.adminservice.domain.model.CodeType;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.CodeTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class CodeTypeController {

    @Autowired
    CodeTypeService codeTypeService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/codetype", method = RequestMethod.GET)
    public List getCodeTypes () throws EntityNotFoundException {

        return codeTypeService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/codetype/{id}", method = RequestMethod.GET)
    public CodeType getCodeType (@PathVariable("id") String id) throws EntityNotFoundException {

        return codeTypeService.getOne(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/codetype", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CodeType AddCodeType(@RequestBody @Valid CodeTypeDto codeTypeDto )  {
        CodeType codeType = new CodeType();
        codeType.setNo(codeTypeDto.getNo());
        codeType.setCdeTypeDesc(codeTypeDto.getCdeTypeDesc());
        return codeTypeService.create(codeType);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/codetype/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public CodeType UpdateCodeType(@PathVariable("id") String id, @RequestBody @Valid CodeTypeDto codeTypeDto )
            throws EntityNotFoundException  {
        return codeTypeService.update(id, codeTypeDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/codetype/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteCodeType(@PathVariable("id") String id) throws EntityNotFoundException  {
        codeTypeService.delete(id);
    }

}

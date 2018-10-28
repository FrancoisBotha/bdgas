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

import org.bdgas.adminservice.domain.dto.CodeTableDto;
import org.bdgas.adminservice.domain.model.CodeTable;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.CodeTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class CodeTableController {

    @Autowired
    CodeTableService codeTableService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/codetable", method = RequestMethod.GET)
    public List getCodeTables () throws EntityNotFoundException {

        log.info("IN CodeTableController");
        return codeTableService.getAll();

    }

    /************
     * GET One  *
     ************/
    @RequestMapping(value = "/api/v1/codetable/{id}", method = RequestMethod.GET)
    public CodeTable getCodeTable (@PathVariable("id") String id) throws EntityNotFoundException {

        return codeTableService.getOne(id);

    }

    /********************
     * GET FOR CODE TYPE *
     ********************/
    @RequestMapping(value = "/api/v1/codetable/codetype/{id}", method = RequestMethod.GET)
    public List getCodesForType (@PathVariable("id") String id) throws EntityNotFoundException {

        return codeTableService.getCodesForType(id);

    }

    /************************
     * GET FOR CODE TYPE Nr *
     ************************/
    @RequestMapping(value = "/api/v1/codetable/codetypenr/{nr}", method = RequestMethod.GET)
    public List getCodesForTypeNr (@PathVariable("nr") String nr) throws EntityNotFoundException {

        return codeTableService.getCodesForNr(nr);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/codetable", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CodeTable AddCodeTable(@RequestBody @Valid CodeTableDto codeTableDto )  {
        CodeTable codeTable = new CodeTable();
        codeTable.setCdeTypeId(codeTableDto.getCdeTypeId());
        codeTable.setCde(codeTableDto.getCde());
        codeTable.setCdeDesc(codeTableDto.getCdeDesc());
        return codeTableService.create(codeTable);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/codetable/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public CodeTable UpdateCodeTable(@PathVariable("id") String id, @RequestBody @Valid CodeTableDto codeTableDto )
            throws EntityNotFoundException  {
        return codeTableService.update(id, codeTableDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/codetable/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteCodeTable(@PathVariable("id") String id) throws EntityNotFoundException  {
        codeTableService.delete(id);
    }

}

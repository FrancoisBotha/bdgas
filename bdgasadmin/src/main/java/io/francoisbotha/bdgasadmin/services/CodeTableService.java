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
package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dao.CodeTableRepository;
import io.francoisbotha.bdgasadmin.domain.dto.CodeTableDto;
import io.francoisbotha.bdgasadmin.domain.model.CodeTable;
import io.francoisbotha.bdgasadmin.domain.model.CodeType;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class CodeTableService {

    @Autowired
    private CodeTableRepository codeTableRepository;

    public List getAll() {

        List CodeTables = new ArrayList();

        Iterable<CodeTable> CodeTablesIt = codeTableRepository.findAll();

        Iterator<CodeTable> iter = CodeTablesIt.iterator();

        while (iter.hasNext()) {
            CodeTables.add(iter.next());
        }

        return CodeTables;
    }

    public CodeTable getOne(String id) throws EntityNotFoundException {

        CodeTable codeTable = codeTableRepository.findOneById(id);

        return codeTable;
    }


    public List  getCodesForType(String codeTypeId) {

        List codeTables = new ArrayList();

        Iterable<CodeTable> codeTablesIt = codeTableRepository.findAllByCdeTypeId(codeTypeId);

        Iterator<CodeTable> iter = codeTablesIt.iterator();

        while (iter.hasNext()) {
            codeTables.add(iter.next());
        }

        return codeTables;
    }


    public CodeTable create(CodeTable codeTable) {
        return codeTableRepository.save(codeTable);
    }

    public CodeTable update(String id, CodeTableDto codeTableDto) throws EntityNotFoundException {

        CodeTable codeTable = codeTableRepository.findOneById(id);

        if(codeTable == null){
            throw new EntityNotFoundException(CodeTable.class, "id", id.toString());
        }

        codeTable.setCdeDesc(codeTableDto.getCdeDesc());

        return codeTableRepository.save(codeTable);
    }

    public void delete(String id)  throws EntityNotFoundException {

        CodeTable codeTable = codeTableRepository.findOneById(id);

        if(codeTable == null){
            throw new EntityNotFoundException(CodeTable.class, "id", id.toString());
        }

        codeTableRepository.delete(codeTable);
    }    
}

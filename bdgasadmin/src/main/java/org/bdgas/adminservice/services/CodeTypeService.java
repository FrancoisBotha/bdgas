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
package org.bdgas.adminservice.services;

import org.bdgas.adminservice.domain.dao.CodeTypeRepository;
import org.bdgas.adminservice.domain.dto.CodeTypeDto;
import org.bdgas.adminservice.domain.model.CodeType;
import org.bdgas.adminservice.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class CodeTypeService {

    @Autowired
    private CodeTypeRepository codeTypeRepository;

    public List getAll() {

        List CodeTypes = new ArrayList();

        Iterable<CodeType> CodeTypesIt = codeTypeRepository.findAll();

        Iterator<CodeType> iter = CodeTypesIt.iterator();

        while (iter.hasNext()) {
            CodeTypes.add(iter.next());
        }

        return CodeTypes;
    }

    public CodeType getOne(String id) throws EntityNotFoundException {

        CodeType codeType = codeTypeRepository.findOneById(id);

        return codeType;
    }

    public CodeType getOneByNr(String nr) throws EntityNotFoundException {

        CodeType codeType = codeTypeRepository.findOneByNo(nr);

        return codeType;
    }

    public CodeType create(CodeType codeType) {
        return codeTypeRepository.save(codeType);
    }

    public CodeType update(String id, CodeTypeDto codeTypeDto) throws EntityNotFoundException {

        CodeType codeType = codeTypeRepository.findOneById(id);

        if(codeType == null){
            throw new EntityNotFoundException(CodeType.class, "id", id.toString());
        }

        codeType.setCdeTypeDesc(codeTypeDto.getCdeTypeDesc());

        return codeTypeRepository.save(codeType);
    }

    public void delete(String id)  throws EntityNotFoundException {

        CodeType codeType = codeTypeRepository.findOneById(id);

        if(codeType == null){
            throw new EntityNotFoundException(CodeType.class, "id", id.toString());
        }

        codeTypeRepository.delete(codeType);
    }    
}

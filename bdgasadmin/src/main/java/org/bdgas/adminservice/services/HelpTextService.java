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

import org.bdgas.adminservice.domain.dao.HelpTextRepository;
import org.bdgas.adminservice.domain.dto.HelpTextDto;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.domain.model.HelpText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class HelpTextService  {

    @Autowired
    private HelpTextRepository helpTextRepository;

    public HelpText getHelpText(String id) throws EntityNotFoundException {
        HelpText helpText = helpTextRepository.findOneById(id);
        if(helpText == null){
            throw new EntityNotFoundException(HelpText.class, "id", id.toString());
        }
        return helpText;
    }

    public HelpText getHelpTextByName(String name) throws EntityNotFoundException {
        HelpText helpText = helpTextRepository.findOneByName(name);
        if(helpText == null){
            throw new EntityNotFoundException(HelpText.class, "id", name.toString());
        }
        return helpText;
    }

    public List getAll() {

        List helpTexts = new ArrayList();

        Iterable<HelpText> helpTextsIt = helpTextRepository.findAll();

        Iterator<HelpText> iter = helpTextsIt.iterator();

        while (iter.hasNext()) {
            helpTexts.add(iter.next());
        }

        return helpTexts;
    }

    public HelpText getOne(String id) throws EntityNotFoundException {


        HelpText helpText = helpTextRepository.findOneById(id);

        return helpText;
    }

    public HelpText create(HelpText helpText) {
        return helpTextRepository.save(helpText);
    }

    public HelpText update(String id, HelpTextDto helpTextDto) throws EntityNotFoundException {

        HelpText helpText = helpTextRepository.findOneById(id);

       //helpText.setLang(helpTextDto.getLang());
        helpText.setName(helpTextDto.getName());
        helpText.setTxt(helpTextDto.getTxt());
        log.debug("before save");

        return helpTextRepository.save(helpText);
    }

    public void delete(String id)  throws EntityNotFoundException {

        HelpText helpText = helpTextRepository.findOneById(id);

        helpTextRepository.delete(helpText);
    }

}

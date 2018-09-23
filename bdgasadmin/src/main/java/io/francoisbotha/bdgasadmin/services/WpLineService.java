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

import io.francoisbotha.bdgasadmin.domain.dao.WpLineRepository;
import io.francoisbotha.bdgasadmin.domain.dto.WpLineDto;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.WpLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WpLineService  {

    @Autowired
    private WpLineRepository wpLineRepository;

    public WpLine getWpLine(String id) throws EntityNotFoundException {
        WpLine wpLine = wpLineRepository.findOneById(id);
        if(wpLine == null){
            throw new EntityNotFoundException(WpLine.class, "id", id.toString());
        }
        return wpLine;
    }

    public List getAll() {

        List wpLines = new ArrayList();

        Iterable<WpLine> wpLinesIt = wpLineRepository.findAll();

        Iterator<WpLine> iter = wpLinesIt.iterator();

        while (iter.hasNext()) {
            wpLines.add(iter.next());
        }

        return wpLines;
    }

    public List getWorkingPaperLines(String wpId) {

        List wpLines = new ArrayList();

        Iterable<WpLine> wpLinesIt = wpLineRepository.findAllByWpId(wpId);

        Iterator<WpLine> iter = wpLinesIt.iterator();

        while (iter.hasNext()) {
            wpLines.add(iter.next());
        }

        return wpLines;
    }

    public WpLine getOne(String id) throws EntityNotFoundException {


        WpLine wpLine = wpLineRepository.findOneById(id);

        return wpLine;
    }


    // TODO: Validation for project referential integrity
    public WpLine create(WpLine wpLine) {

        //Step 1. Create Job from Working Paper Line

        //Step 2. Submit Job to Spark Job Server

        //Step 3. Update Job Details

        //Step 4. Save Working Paper Line
        return wpLineRepository.save(wpLine);
    }

    public WpLine update(String id, WpLineDto wpLineDto) throws EntityNotFoundException {

        WpLine wpLine = wpLineRepository.findOneById(id);

        return wpLineRepository.save(wpLine);
    }

    public void delete(String id)  throws EntityNotFoundException {

        WpLine wpLine = wpLineRepository.findOneById(id);

        wpLineRepository.delete(wpLine);
    }

}

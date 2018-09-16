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

import io.francoisbotha.bdgasadmin.domain.dao.WorkingPaperRepository;
import io.francoisbotha.bdgasadmin.domain.dto.WorkingPaperDto;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.WorkingPaper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WorkingPaperService  {

    @Autowired
    private WorkingPaperRepository workingPaperRepository;

    public WorkingPaper getWorkingPaper(String id) throws EntityNotFoundException {
        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);
        if(workingPaper == null){
            throw new EntityNotFoundException(WorkingPaper.class, "id", id.toString());
        }
        return workingPaper;
    }

    public List getAll() {

        List workingPapers = new ArrayList();

        Iterable<WorkingPaper> workingPapersIt = workingPaperRepository.findAll();

        Iterator<WorkingPaper> iter = workingPapersIt.iterator();

        while (iter.hasNext()) {
            workingPapers.add(iter.next());
        }

        return workingPapers;
    }

    public List getProjectWorkingPapers(String projectId) {

        List workingPapers = new ArrayList();

        Iterable<WorkingPaper> workingPapersIt = workingPaperRepository.findAllByProjectId(projectId);

        Iterator<WorkingPaper> iter = workingPapersIt.iterator();

        while (iter.hasNext()) {
            workingPapers.add(iter.next());
        }

        return workingPapers;
    }

    public WorkingPaper getOne(String id) throws EntityNotFoundException {


        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);

        return workingPaper;
    }


    // TODO: Validation for project referential integrity
    public WorkingPaper create(WorkingPaper workingPaper) {
        return workingPaperRepository.save(workingPaper);
    }

    public WorkingPaper update(String id, WorkingPaperDto workingPaperDto) throws EntityNotFoundException {

        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);

        workingPaper.setName(workingPaperDto.getName());
        workingPaper.setProjectId(workingPaperDto.getProjectId());
        workingPaper.setLineCount(workingPaperDto.getLineCount());

        return workingPaperRepository.save(workingPaper);
    }

    public Integer incrLineCount(String id) throws EntityNotFoundException {

        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);

        Integer lnCount = workingPaper.getLineCount();
        lnCount++;

        workingPaper.setLineCount(lnCount);
        workingPaperRepository.save(workingPaper);

        return lnCount;

    }


    public Integer decrLineCount(String id) throws EntityNotFoundException {

        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);

        Integer lnCount = workingPaper.getLineCount();
        lnCount--;

        workingPaper.setLineCount(lnCount);
        workingPaperRepository.save(workingPaper);

        return lnCount;

    }


    public void delete(String id)  throws EntityNotFoundException {

        WorkingPaper workingPaper = workingPaperRepository.findOneById(id);

        workingPaperRepository.delete(workingPaper);
    }

}


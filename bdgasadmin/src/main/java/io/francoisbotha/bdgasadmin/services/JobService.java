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

import io.francoisbotha.bdgasadmin.domain.dao.JobRepository;
import io.francoisbotha.bdgasadmin.domain.dto.JobDto;
import io.francoisbotha.bdgasadmin.domain.model.Job;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    

    public List getAll() {

        List Jobs = new ArrayList();

        Iterable<Job> JobsIt = jobRepository.findAll();

        Iterator<Job> iter = JobsIt.iterator();

        while (iter.hasNext()) {
            Jobs.add(iter.next());
        }

        return Jobs;
    }

    public Job getOne(String id) throws EntityNotFoundException {

        Job job = jobRepository.findOneById(id);

        return job;
    }


    public List  getJobsForWp(String wpId) {

        List jobs = new ArrayList();

        Iterable<Job> jobsIt = jobRepository.findAllByWpId(wpId);

        Iterator<Job> iter = jobsIt.iterator();

        while (iter.hasNext()) {
            jobs.add(iter.next());
        }

        return jobs;
    }
    

    public Job create(Job job) {
        return jobRepository.save(job);
    }

    public Job update(String id, JobDto jobDto) throws EntityNotFoundException {

        Job job = jobRepository.findOneById(id);

        if(job == null){
            throw new EntityNotFoundException(Job.class, "id", id.toString());
        }

        job.setResult(jobDto.getResult());

        return jobRepository.save(job);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Job job = jobRepository.findOneById(id);

        if(job == null){
            throw new EntityNotFoundException(Job.class, "id", id.toString());
        }

        jobRepository.delete(job);
    }
}
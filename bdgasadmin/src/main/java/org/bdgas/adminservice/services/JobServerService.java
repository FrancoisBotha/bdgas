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

import org.bdgas.adminservice.domain.dao.JobServerRepository;
import org.bdgas.adminservice.domain.dto.JobServerDto;
import org.bdgas.adminservice.domain.model.JobServer;
import org.bdgas.adminservice.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class JobServerService {

    @Autowired
    private JobServerRepository jobServerRepository;


    public List getAll() {

        List JobServers = new ArrayList();

        Iterable<JobServer> JobServersIt = jobServerRepository.findAll();

        Iterator<JobServer> iter = JobServersIt.iterator();

        while (iter.hasNext()) {
            JobServers.add(iter.next());
        }

        return JobServers;
    }

    public JobServer getOne(String id) throws EntityNotFoundException {

        JobServer job = jobServerRepository.findOneById(id);

        return job;
    }


    public JobServer  getJobServerActive() {

        /*Returns first job server marked active=true*/

        String isActive = "true";

        JobServer jobServer = new JobServer();

        Iterable<JobServer> jobsIt = jobServerRepository.findAllByActive(isActive);

        Iterator<JobServer> iter = jobsIt.iterator();

        if (iter.hasNext()) {
            jobServer = iter.next();
        }

        return jobServer;
    }


    public JobServer create(JobServer job) {
        return jobServerRepository.save(job);
    }

    public JobServer update(String id, JobServerDto jobServerDto) throws EntityNotFoundException {

        JobServer jobServer = jobServerRepository.findOneById(id);

        if(jobServer == null){
            throw new EntityNotFoundException(JobServer.class, "id", id.toString());
        }

        jobServer.setJobServerURL(jobServerDto.getJobServerURL());
        jobServer.setDescription(jobServerDto.getDescription());
        jobServer.setActive(jobServerDto.getActive());

        return jobServerRepository.save(jobServer);
    }

    public void delete(String id)  throws EntityNotFoundException {

        JobServer job = jobServerRepository.findOneById(id);

        if(job == null){
            throw new EntityNotFoundException(JobServer.class, "id", id.toString());
        }

        jobServerRepository.delete(job);
    }
}


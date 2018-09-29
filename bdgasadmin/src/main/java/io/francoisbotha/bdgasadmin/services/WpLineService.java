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
import io.francoisbotha.bdgasadmin.domain.dao.WpLineRepository;
import io.francoisbotha.bdgasadmin.domain.dto.JobDto;
import io.francoisbotha.bdgasadmin.domain.dto.TaskDto;
import io.francoisbotha.bdgasadmin.domain.dto.WpLineDto;
import io.francoisbotha.bdgasadmin.domain.model.Job;
import io.francoisbotha.bdgasadmin.domain.model.Task;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.WpLine;
import io.francoisbotha.bdgasadmin.error.SjsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WpLineService  {

    @Autowired
    private WpLineRepository wpLineRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private SjsService sjsService;

    @Autowired
    private TaskService taskService;

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

    public WpLine create(WpLine wpLine)
            throws HttpStatusCodeException, EntityNotFoundException, SjsException {

        try {

            //Step 1. Get Task from Working Paper Line
            Task task = taskService.getOne(wpLine.getTaskId());

            //Step 2. Create Job from Working Paper Line
            JobDto jobDto = new JobDto();

            jobDto.setWpId(wpLine.getWpId());
            jobDto.setWpLineId(wpLine.getId());
            jobDto.setTaskId(wpLine.getTaskId());

            //Default configs
            jobDto.setConfigContext("sql-context-1");
            jobDto.setConfigSync("true");
            jobDto.setConfigTimeout("600");

            jobDto.setConfigAppName(task.getAppName());
            jobDto.setConfigClassPath(task.getClassPath());

            //Step 3. Submit Job to Spark Job Server
            JobDto returnJobDto = new JobDto();
            returnJobDto = sjsService.runJob(jobDto, wpLine.getTaskParams());

            //Step 4. Update Job Details
            Job job = new Job();
            job.setWpId(jobDto.getWpId());
            job.setWpLineId(jobDto.getWpLineId());
            job.setTaskId(jobDto.getTaskId());
            job.setConfigContext(jobDto.getConfigContext());
            job.setConfigSync(jobDto.getConfigSync());
            job.setConfigTimeout(jobDto.getConfigTimeout());
            job.setConfigAppName(jobDto.getConfigAppName());
            job.setConfigClassPath(jobDto.getConfigClassPath());

            job.setDuration(returnJobDto.getDuration());
            job.setJobStart(returnJobDto.getJobStart());
            job.setSparkContext(returnJobDto.getSparkContext());
            job.setSparkJobId(returnJobDto.getSparkJobId());
            job.setResult(returnJobDto.getResult());

            final Job job1 = jobService.create(job);

            //Step 5. Save Working Paper Line
            wpLine.setLnResult(job.getResult());
            return wpLineRepository.save(wpLine);

        }
        catch (HttpStatusCodeException ex) {
            throw ex;
        }
        catch (RestClientException ex) {

            String message = "Failed to get service: " + ex.getMessage();
            log.error(message, ex);
            throw ex;
        }
        catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        }
        catch (SjsException ex) {
            ex.printStackTrace();
            throw ex;
        }
        catch (Exception ex) {
            String message = "General Exception while trying to run SJS Service: " + ex.getMessage();
            log.error(message, ex);
        }

        return null;
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

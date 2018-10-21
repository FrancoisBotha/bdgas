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

import org.bdgas.adminservice.domain.dto.TaskDto;
import org.bdgas.adminservice.domain.model.Task;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class TaskController {
    
    @Autowired
    TaskService taskService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/task", method = RequestMethod.GET)
    public List getTasks () throws EntityNotFoundException {

        return taskService.getAll();

    }


    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/task/{id}", method = RequestMethod.GET)
    public Task  getTask (@PathVariable("id") String id) throws EntityNotFoundException {

        return taskService.getOne(id);

    }


    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/task", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task AddTask(@RequestBody @Valid TaskDto taskDto )  {
        Task task = new Task();
        task.setTaskType(taskDto.getTaskType());
        task.setTaskCde(taskDto.getTaskCde());
        task.setMenuDesc(taskDto.getMenuDesc());
        task.setTaskDesc(taskDto.getTaskDesc());
        task.setSuccessDesc(taskDto.getSuccessDesc());
        task.setAppName(taskDto.getAppName());
        task.setClassPath(taskDto.getClassPath());
        task.setTemplatePath(taskDto.getTemplatePath());
        task.setTaskHelp(taskDto.getTaskHelp());

        return taskService.create(task);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/task/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Task UpdateTask(@PathVariable("id") String id, @RequestBody @Valid TaskDto taskDto )
            throws EntityNotFoundException  {
        return taskService.update(id, taskDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/task/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable("id") String id) throws EntityNotFoundException  {
        taskService.delete(id);
    }
}

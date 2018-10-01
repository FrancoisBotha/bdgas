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

import io.francoisbotha.bdgasadmin.domain.dao.TaskRepository;
import io.francoisbotha.bdgasadmin.domain.dto.TaskDto;
import io.francoisbotha.bdgasadmin.domain.model.Task;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List getAll() {

        List Tasks = new ArrayList();

        Iterable<Task> TasksIt = taskRepository.findAll();

        Iterator<Task> iter = TasksIt.iterator();

        while (iter.hasNext()) {
            Tasks.add(iter.next());
        }

        return Tasks;
    }

    public Task getOne(String id) throws EntityNotFoundException {

        Task codeType = taskRepository.findOneById(id);

        return codeType;
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(String id, TaskDto taskDto) throws EntityNotFoundException {

        Task task = taskRepository.findOneById(id);

        if(task == null){
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }

        task.setTaskType(taskDto.getTaskType());
        task.setTaskCde(taskDto.getTaskCde());
        task.setMenuDesc(taskDto.getMenuDesc());
        task.setTaskDesc(taskDto.getTaskDesc());
        task.setAppName(taskDto.getAppName());
        task.setClassPath(taskDto.getClassPath());
        task.setSuccessDesc(taskDto.getSuccessDesc());
        task.setTemplatePath(taskDto.getTemplatePath());
        task.setTaskHelp(taskDto.getTaskHelp());

        return taskRepository.save(task);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Task task = taskRepository.findOneById(id);

        if(task == null){
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }

        taskRepository.delete(task);
    }
}


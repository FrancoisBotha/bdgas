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

import io.francoisbotha.bdgasadmin.domain.dao.ProjectRepository;
import io.francoisbotha.bdgasadmin.domain.dto.ProjectDto;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class ProjectService  {

    @Autowired
    private ProjectRepository projectRepository;

    public Project getProject(String id) throws EntityNotFoundException {
        Project project = projectRepository.findOneById(id);
        if(project == null){
            throw new EntityNotFoundException(Project.class, "id", id.toString());
        }
        return project;
    }

    public List getAll() {

        List projects = new ArrayList();

        Iterable<Project> projectsIt = projectRepository.findAll();

        Iterator<Project> iter = projectsIt.iterator();

        while (iter.hasNext()) {
            projects.add(iter.next());
        }

        return projects;
    }

    public List getTeamProjects(String teamId) {

        List projects = new ArrayList();

        Iterable<Project> projectsIt = projectRepository.findAllByTeamId(teamId);

        Iterator<Project> iter = projectsIt.iterator();

        while (iter.hasNext()) {
            projects.add(iter.next());
        }

        return projects;
    }

    public Project getOne(String id) throws EntityNotFoundException {


        Project project = projectRepository.findOneById(id);

        return project;
    }

    //TODO: validaton for team referential integrity
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project update(String id, ProjectDto projectDto) throws EntityNotFoundException {

        Project project = projectRepository.findOneById(id);

        project.setName(projectDto.getName());
        project.setTeamId(projectDto.getTeamId());

        return projectRepository.save(project);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Project project = projectRepository.findOneById(id);

        projectRepository.delete(project);
    }

}

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

import org.bdgas.adminservice.domain.dto.ProjectDto;
import org.bdgas.adminservice.domain.model.Project;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class ProjectController  {

    @Autowired
    ProjectService projectService;

    /************
     * GET ALL  *
     ************/
    @RequestMapping(value = "/api/v1/project", method = RequestMethod.GET)
    public List getProjects () throws EntityNotFoundException {

        return projectService.getAll();

    }

    /************
     * GET ONE  *
     ************/
    @RequestMapping(value = "/api/v1/project/{id}", method = RequestMethod.GET)
    public Project getProjects (@PathVariable("id") String id) throws EntityNotFoundException {

        return projectService.getOne(id);

    }

    /********************
     * GET FOR TEAM  *
     ********************/
    @RequestMapping(value = "/api/v1/project/team/{id}", method = RequestMethod.GET)
    public List getProjectsForTeam (@PathVariable("id") String id) throws EntityNotFoundException {

        return projectService.getTeamProjects(id);

    }

    /************
     * ADD      *
     ************/
    @RequestMapping(value = "/api/v1/project", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project AddProject(@RequestBody @Valid ProjectDto projectDto )  {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setTeamId(projectDto.getTeamId());
        return projectService.create(project);
    }

    /************
     * UPDATE   *
     ************/
    @RequestMapping(value = "/api/v1/project/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Project UpdateProject(@PathVariable("id") String id, @RequestBody @Valid ProjectDto projectDto )
            throws EntityNotFoundException  {
        return projectService.update(id, projectDto);
    }

    /************
     * DELETE   *
     ************/
    @RequestMapping(value = "/api/v1/project/{id}", method = RequestMethod.DELETE )
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable("id") String id) throws EntityNotFoundException  {
        projectService.delete(id);
    }

}
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

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.francoisbotha.bdgasadmin.domain.dao.DataSourceRepository;
import io.francoisbotha.bdgasadmin.domain.dto.DataSourceDto;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class DataSourceService  {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private S3Service s3Service;

    public DataSource getDataSource(String id) throws EntityNotFoundException {
        DataSource dataSource = dataSourceRepository.findOneById(id);
        if(dataSource == null){
            throw new EntityNotFoundException(DataSource.class, "id", id.toString());
        }
        return dataSource;
    }

    public List getAll() {

        List dataSources = new ArrayList();

        Iterable<DataSource> dataSourcesIt = dataSourceRepository.findAll();

        Iterator<DataSource> iter = dataSourcesIt.iterator();

        while (iter.hasNext()) {
            dataSources.add(iter.next());
        }

        return dataSources;
    }

    public List getTeamDataSources(String teamId) {

        List dataSources = new ArrayList();

        Iterable<DataSource> dataSourcesIt = dataSourceRepository.findAllByTeamId(teamId);

        Iterator<DataSource> iter = dataSourcesIt.iterator();

        while (iter.hasNext()) {
            dataSources.add(iter.next());
        }

        return dataSources;
    }

    public DataSource getOne(String id) throws EntityNotFoundException {


        DataSource dataSource = dataSourceRepository.findOneById(id);

        return dataSource;
    }

    //TODO: validaton for team referential integrity
    public DataSource create(DataSource dataSource) {

        try {

            ObjectMetadata objectMetadata = s3Service.getObjectMetaData(dataSource.getObjectKey());

            dataSource.setContentType(objectMetadata.getContentType());
            dataSource.setContentLength(objectMetadata.getContentLength());
            dataSource.setCreateDt(objectMetadata.getLastModified().toString());
            return  dataSourceRepository.save(dataSource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**************
     *  DELETE    *
     **************/
    public void delete(String id)  throws EntityNotFoundException {

        try {

            DataSource dataSource = dataSourceRepository.findOneById(id);

            s3Service.deleteObject(dataSource.getObjectKey());

            dataSourceRepository.delete(dataSource);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
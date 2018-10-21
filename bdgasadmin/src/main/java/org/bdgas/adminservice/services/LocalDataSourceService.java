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

import org.bdgas.adminservice.domain.dao.LocalDataSourceRepository;
import org.bdgas.adminservice.domain.model.LocalDataSource;
import org.bdgas.adminservice.error.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class LocalDataSourceService {

    @Autowired
    private LocalDataSourceRepository localDataSourceRepository;

    public List getAll() {

        List localDataSources = new ArrayList();

        Iterable<LocalDataSource> localDataSourcesIt = localDataSourceRepository.findAll();

        Iterator<LocalDataSource> iter = localDataSourcesIt.iterator();

        while (iter.hasNext()) {
            localDataSources.add(iter.next());
        }

        return localDataSources;
    }

    public LocalDataSource getOne(String id) throws EntityNotFoundException {

        LocalDataSource localDataSource = localDataSourceRepository.findOneById(id);

        return localDataSource;
    }

    public LocalDataSource create(LocalDataSource localDataRepository) {
        return localDataSourceRepository.save(localDataRepository);
    }

    public void delete(String id)  throws EntityNotFoundException {

        LocalDataSource ocalDataSource = localDataSourceRepository.findOneById(id);

        if(ocalDataSource == null){
            throw new EntityNotFoundException(LocalDataSource.class, "id", id.toString());
        }

        localDataSourceRepository.delete(ocalDataSource);
    }
}



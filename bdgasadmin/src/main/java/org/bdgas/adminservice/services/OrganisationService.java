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

import org.bdgas.adminservice.domain.dao.OrganisationRepository;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.domain.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

    public Organisation getOrganisation(String id) throws EntityNotFoundException {
        Organisation organisation = organisationRepository.findOneById(id);
        if(organisation == null){
            throw new EntityNotFoundException(Organisation.class, "id", id.toString());
        }
        return organisation;
    }

    public List getAll() {

        List organisations = new ArrayList();

        Iterable<Organisation> organisationsIt = organisationRepository.findAll();

        Iterator<Organisation> iter = organisationsIt.iterator();

        while (iter.hasNext()) {
            organisations.add(iter.next());
        }

        return organisations;
    }

    public List getAll(String id) throws EntityNotFoundException  {

        List organisations = new ArrayList();

        Iterable<Organisation> organisationsIt = organisationRepository.findAllById(id);

        Iterator<Organisation> iter = organisationsIt.iterator();

        while (iter.hasNext()) {
            organisations.add(iter.next());
        }

        if(organisations.isEmpty()
                || organisations.get(0) == null){
            throw new EntityNotFoundException(Organisation.class, "id", id.toString());
        }

        return organisations;
    }

    public Organisation create(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public void delete(String id)  throws EntityNotFoundException {

        Organisation organisation = organisationRepository.findOneById(id);

        organisationRepository.delete(organisation);
    }

}


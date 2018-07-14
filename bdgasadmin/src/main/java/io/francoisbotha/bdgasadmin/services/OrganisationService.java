package io.francoisbotha.bdgasadmin.services;

import io.francoisbotha.bdgasadmin.domain.dao.OrganisationRepository;
import io.francoisbotha.bdgasadmin.error.EntityNotFoundException;
import io.francoisbotha.bdgasadmin.domain.model.Organisation;
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


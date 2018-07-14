package io.francoisbotha.bdgasadmin.domain.dao;

import io.francoisbotha.bdgasadmin.domain.model.Organisation;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface OrganisationRepository extends CrudRepository<Organisation, String> {
    List<Organisation> findAll();
    List<Organisation> findAllById(String Id);
    Organisation findOneById(String id);
}
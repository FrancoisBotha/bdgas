package io.francoisbotha.bdgasadmin.domain.dao;

import io.francoisbotha.bdgasadmin.domain.model.Team;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TeamRepository extends CrudRepository<Team, String> {
    List<Team> findAll();
}

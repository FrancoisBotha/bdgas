package io.francoisbotha.bdgasadmin.domain.dao;

import io.francoisbotha.bdgasadmin.domain.model.HelpText;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface HelpTextRepository extends CrudRepository<HelpText, String> {
    List<HelpText> findAll();
    List<HelpText> findAllById(String Id);
    HelpText findOneById(String id);
}
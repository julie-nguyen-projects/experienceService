package com.epitech.pgt2019.repository;

import com.epitech.pgt2019.domain.Company;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

}

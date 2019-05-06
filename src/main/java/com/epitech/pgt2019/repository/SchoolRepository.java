package com.epitech.pgt2019.repository;

import com.epitech.pgt2019.domain.School;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the School entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolRepository extends MongoRepository<School, String> {

}

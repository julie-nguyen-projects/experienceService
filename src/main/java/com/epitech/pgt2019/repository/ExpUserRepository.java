package com.epitech.pgt2019.repository;

import com.epitech.pgt2019.domain.ExpUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ExpUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpUserRepository extends MongoRepository<ExpUser, String> {

}

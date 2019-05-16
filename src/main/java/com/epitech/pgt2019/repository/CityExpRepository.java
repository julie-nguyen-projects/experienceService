package com.epitech.pgt2019.repository;

import com.epitech.pgt2019.domain.CityExp;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CityExp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityExpRepository extends MongoRepository<CityExp, String> {

}

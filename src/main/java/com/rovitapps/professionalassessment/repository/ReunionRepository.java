package com.rovitapps.professionalassessment.repository;

import com.rovitapps.professionalassessment.model.Reunion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends MongoRepository<Reunion, String> {
}

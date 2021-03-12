package com.rovitapps.professionalassessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.rovitapps.professionalassessment.model.OneAOne;

import java.util.Date;
import java.util.List;

@Repository
public interface OneAOneRepository extends MongoRepository<OneAOne, String> {

    @Query("{'creatorAssessments.owner.username':'?0', " +
            "'evaluatedAssessments.owner.username':'?1', " +
            "date: '?2'}")
    List<OneAOne> findDuplicate(String creatorUsername, String evaluatedUserName, Date date);

    @Query("{'creatorAssessments.owner.username':'?0', " +
            "'evaluatedAssessments.owner.username':'?1'")
    List<OneAOne> findAllByUsers(String creatorUsername, String evaluatedUserName);
}

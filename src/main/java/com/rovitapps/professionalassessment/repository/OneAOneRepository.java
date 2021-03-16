package com.rovitapps.professionalassessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.rovitapps.professionalassessment.model.OneAOne;

import java.util.Date;
import java.util.List;

@Repository
public interface OneAOneRepository extends MongoRepository<OneAOne, String> {

    @Query("{'applicantUser':'?0', 'guestUser':'?1', date: {'$gte' : ?2}}")
    List<OneAOne> findDuplicate(String creatorUsername, String evaluatedUserName, Date date);

    List<OneAOne> findAllByApplicantUserOrGuestUser(String applicationUser, String guestUser);

    List<OneAOne> findAllByApplicantUserAndGuestUserOrderByApplicantUser(String applicationUser, String guestUser);
}

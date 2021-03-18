package com.rovitapps.professionalassessment.repository;

import com.rovitapps.professionalassessment.model.OneAOne;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneAOneRepository extends MongoRepository<OneAOne, String> {

    @Query("{'applicantUser.username':'?0', 'guestUser.username':'?1'}")
    OneAOne findByApplicantUserAndGuestUser(String applicationUser, String guestUser);

    @Query("{$or:[{'applicantUser.username':'?0'},{'guestUser.username':'?0'}]}")
    List<OneAOne> findAllByUser(String username);

    @Query("{'reunions.$id': '?0'}")
    OneAOne findByReunionId(String id);
}

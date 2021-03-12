package com.rovitapps.professionalassessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.rovitapps.professionalassessment.model.GradeType;

@Repository
public interface GradeTypeRepository extends MongoRepository<GradeType, String> {

    GradeType findByType(String type);

    /*@Query("{$or: [{'title': { $regex: '?0', $options: 'i'}}, " +
            "{'libraryCode': { $regex: '?0', $options: 'i'}}, " +
            "{'_id': '?0'}, " +
            "{'authors': { $regex: '?0', $options: 'i'}}]}")
    Page<Book> findAllByCriteria(Pageable pageable, String q);

    Page<Book> findByUpdateDateBefore(Pageable pageable, Date date);

    Book findByTitleOrLibraryCode(String Title, String libraryCode);

    Book findById(String id, Class<Book> bookClass);*/

}

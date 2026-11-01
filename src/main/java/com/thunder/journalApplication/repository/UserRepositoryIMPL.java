
package com.thunder.journalApplication.repository;

import com.thunder.journalApplication.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryIMPL {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<user> getUserForSA() {
        Query query = new Query();

        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("email")
                                .regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
                        Criteria.where("email").ne(null).ne(""),
                        Criteria.where("sentimentAnalysis").exists(true)
                )
        );

        return mongoTemplate.find(query, user.class);
    }
}

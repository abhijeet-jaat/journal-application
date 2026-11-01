package com.thunder.journalApplication.repository;
import com.thunder.journalApplication.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<user, ObjectId>
{
    user findByUserName(String username);
    void deleteByUserName(String username);
}

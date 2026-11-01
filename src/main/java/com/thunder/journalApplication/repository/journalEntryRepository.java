package com.thunder.journalApplication.repository;
import com.thunder.journalApplication.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepository extends MongoRepository<journalEntry, ObjectId>
{
}

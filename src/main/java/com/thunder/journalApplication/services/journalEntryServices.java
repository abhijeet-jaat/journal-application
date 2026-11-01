package com.thunder.journalApplication.services;
import com.thunder.journalApplication.entity.journalEntry;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.journalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class journalEntryServices {

    @Autowired
    private journalEntryRepository JournalEntryRepository;
    @Autowired
    private userServices UserServices;

    @Transactional
    public void saveEntry(journalEntry JournalEntry, String userName) {
        try {
            user User = UserServices.findByUserName(userName);
            JournalEntry.setDate(LocalDateTime.now());
            journalEntry saved = JournalEntryRepository.save(JournalEntry);
            User.getJournalEntries().add(saved);
            UserServices.saveUser(User);
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException(" An error occueed while saving journalEntry");
        }
    }

    public void saveEntry(journalEntry JournalEntry){
        JournalEntryRepository.save(JournalEntry);
    }

    public List<journalEntry> getAll(){
        return JournalEntryRepository.findAll();
    }

    public Optional<journalEntry> findById(ObjectId id){
        return JournalEntryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        user User = UserServices.findByUserName(userName);
        boolean removed = User.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (removed) {
            UserServices.saveUser(User);
            JournalEntryRepository.deleteById(id);
        }
        return removed;
    }
}

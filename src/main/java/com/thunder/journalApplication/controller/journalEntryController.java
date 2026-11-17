package com.thunder.journalApplication.controller;

import com.thunder.journalApplication.Dto.JournalDto;
import com.thunder.journalApplication.entity.journalEntry;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.services.journalEntryServices;
import com.thunder.journalApplication.services.userServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name="Journal APIs")
public class journalEntryController {

    @Autowired
    private journalEntryServices JournalEntryServices;
    @Autowired
    private userServices UserServices;

    @GetMapping
    @Operation(summary= "Get All Journal Entires Of A User")
    public ResponseEntity <?> getAllJournalEntriesOfUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user User = UserServices.findByUserName(userName);
        List<journalEntry> all = User.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(summary= "Post Journal Entries Of A User")
    public ResponseEntity <JournalDto> createEntry(@RequestBody JournalDto myEntry) {
        journalEntry newEntry = new journalEntry();
        newEntry.setTitle(myEntry.getTitle());
        newEntry.setContent(myEntry.getContent());
        newEntry.setSentiment(myEntry.getSentiment());
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntryServices.saveEntry(newEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    @Operation(summary= "Get Journal Entry by ID")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user User = UserServices.findByUserName(userName);
        List<journalEntry> collect = User.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<journalEntry> JournalEntry = JournalEntryServices.findById(objectId);
            if (JournalEntry.isPresent()) {
                return new ResponseEntity<>(JournalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    @Operation(summary= "Update Journal Entry by ID")
   public ResponseEntity<?>updateJournalEntry(@PathVariable String myId, @RequestBody JournalDto newEntry) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user User = UserServices.findByUserName(userName);
        List <journalEntry> collect = User.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<journalEntry> JournalEntry = JournalEntryServices.findById(objectId);
            if (JournalEntry.isPresent()) {
                journalEntry old = JournalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                old.setSentiment(newEntry.getSentiment());
                JournalEntryServices.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{myId}")
    @Operation(summary= "Delete Journal Entry by ID")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String myId) {
        ObjectId id = new ObjectId(myId);
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        JournalEntryServices.deleteById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

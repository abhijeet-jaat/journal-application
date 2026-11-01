package com.thunder.journalApplication.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {

    @Id
    private ObjectId id;

    @Indexed(unique = true)

    private String userName;
    private String email;
    private String password;
    private Boolean sentimentAnalysis;

    @DBRef
    private List<journalEntry> JournalEntries = new ArrayList<>();
    private List<String> roles;
}
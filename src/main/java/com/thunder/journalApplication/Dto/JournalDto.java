package com.thunder.journalApplication.Dto;

import com.thunder.journalApplication.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {
    @NonNull
    private String title;
    private String content;
    private Sentiment sentiment;

}

package com.thunder.journalApplication.scheduler;

import com.thunder.journalApplication.cache.AppCache;
import com.thunder.journalApplication.entity.journalEntry;
import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.enums.Sentiment;
import com.thunder.journalApplication.repository.UserRepositoryIMPL;
import com.thunder.journalApplication.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    UserRepositoryIMPL userRepository;
    @Autowired
    private AppCache  appCache;

    // tesing SendMail SMTP
//    @PostConstruct
//    public void testFetchUserAndSendMail() {
//        fetchUserAndSendMail();
//    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendMail(){
        List<user> users = userRepository.getUserForSA();
        for (user user : users) {
            List<journalEntry> JournalEntries = user.getJournalEntries();
            List<Sentiment> filteredEntries = JournalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : filteredEntries) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        maxCount = entry.getValue();
                        mostFrequentSentiment = entry.getKey();
                    }
                }
                if (mostFrequentSentiment != null) {
                    emailService.sendEmail(user.getEmail(), "Sentiment for 7 days ", mostFrequentSentiment.toString());
                }
            }

        }
    }
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearCache(){
        appCache.init();
    }
}

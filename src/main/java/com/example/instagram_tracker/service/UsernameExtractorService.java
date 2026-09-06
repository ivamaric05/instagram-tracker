package com.example.instagram_tracker.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsernameExtractorService {

    public List<String> extractUsernames(String text) {

        List<String> usernames = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return usernames;
        }

        String[] lines = text.split("\\R");

        for (String line : lines) {

            String username = line.trim();

            // Preskačemo prazne linije
            if (username.isEmpty()) {
                continue;
            }

            // Preskačemo Instagram navigacione stavke
            if (username.equalsIgnoreCase("explore")
                    || username.equalsIgnoreCase("reels")) {
                continue;
            }

            // Instagram username:
            // slova, brojevi, . i _
            if (username.matches("[a-zA-Z0-9._]+")) {
                usernames.add(username);
            }
        }

        return usernames;
    }
}

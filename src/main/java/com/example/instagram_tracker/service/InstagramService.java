package com.example.instagram_tracker.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InstagramService {

    public Set<String> getFollowers(String username) {

        Set<String> followers = new HashSet<>();

        followers.add("ana123");
        followers.add("milica789");
        followers.add("novi");

        return followers;
    }
}

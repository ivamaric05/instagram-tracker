package com.example.instagram_tracker.controller;

import com.example.instagram_tracker.model.Profile;
import com.example.instagram_tracker.service.ProfileService;
import com.example.instagram_tracker.service.TrackerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final TrackerService trackerService;

    public ProfileController(
            ProfileService profileService,
            TrackerService trackerService
    ) {
        this.profileService = profileService;
        this.trackerService = trackerService;
    }


    // =========================================================
    // HOME
    // =========================================================

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "profiles",
                profileService.getAllProfiles()
        );

        return "index";
    }


    // =========================================================
    // ADD PROFILE
    // =========================================================

    @PostMapping("/profiles")
    public String addProfile(
            @RequestParam String username
    ) {

        profileService.addProfile(username);

        return "redirect:/";
    }


    // =========================================================
    // PROFILE
    // =========================================================

    @GetMapping("/profiles/{username}")
    public String profile(
            @PathVariable String username,
            Model model
    ) {

        Profile profile =
                profileService.getProfile(username);


        // =====================================================
        // FOLLOWER COUNTS
        // =====================================================

        long maleCount =
                profileService.countFollowersByCategory(
                        profile,
                        "Male"
                );

        long femaleCount =
                profileService.countFollowersByCategory(
                        profile,
                        "Female"
                );

        long businessCount =
                profileService.countFollowersByCategory(
                        profile,
                        "Business"
                );

        long unknownCount =
                profileService.countFollowersByCategory(
                        profile,
                        "Unknown"
                );


        // =====================================================
        // FOLLOWING COUNTS
        // =====================================================

        long followingMaleCount =
                profileService.countFollowingByCategory(
                        profile,
                        "Male"
                );

        long followingFemaleCount =
                profileService.countFollowingByCategory(
                        profile,
                        "Female"
                );

        long followingBusinessCount =
                profileService.countFollowingByCategory(
                        profile,
                        "Business"
                );

        long followingUnknownCount =
                profileService.countFollowingByCategory(
                        profile,
                        "Unknown"
                );


        // =====================================================
        // SEND DATA TO HTML
        // =====================================================

        model.addAttribute(
                "profile",
                profile
        );


        // FOLLOWERS

        model.addAttribute(
                "maleCount",
                maleCount
        );

        model.addAttribute(
                "femaleCount",
                femaleCount
        );

        model.addAttribute(
                "businessCount",
                businessCount
        );

        model.addAttribute(
                "unknownCount",
                unknownCount
        );


        // FOLLOWING

        model.addAttribute(
                "followingMaleCount",
                followingMaleCount
        );

        model.addAttribute(
                "followingFemaleCount",
                followingFemaleCount
        );

        model.addAttribute(
                "followingBusinessCount",
                followingBusinessCount
        );

        model.addAttribute(
                "followingUnknownCount",
                followingUnknownCount
        );


        return "profile";
    }


    // =========================================================
    // CHECK FOLLOWERS
    // =========================================================

    @PostMapping(
            "/profiles/{username}/check-followers"
    )
    public String checkFollowers(
            @PathVariable String username,
            @RequestParam String followersText
    ) {

        trackerService.checkFollowers(
                username,
                followersText
        );

        return "redirect:/profiles/" + username;
    }


    // =========================================================
    // CHECK FOLLOWING
    // =========================================================

    @PostMapping(
            "/profiles/{username}/check-following"
    )
    public String checkFollowing(
            @PathVariable String username,
            @RequestParam String followingText
    ) {

        trackerService.checkFollowing(
                username,
                followingText
        );

        return "redirect:/profiles/" + username;
    }


    // =========================================================
    // SAVE ALL FOLLOWER CATEGORIES
    // =========================================================

    @PostMapping(
            "/profiles/{username}/followers/categories"
    )
    public String updateFollowerCategories(
            @PathVariable String username,
            @RequestParam Map<String, String> params
    ) {

        for (Map.Entry<String, String> entry :
                params.entrySet()) {

            String key = entry.getKey();

            if (key.startsWith("category_")) {

                Long followerId =
                        Long.parseLong(
                                key.substring(
                                        "category_".length()
                                )
                        );

                String category =
                        entry.getValue();

                profileService.updateFollowerCategory(
                        followerId,
                        category
                );
            }
        }

        return "redirect:/profiles/" + username;
    }


    // =========================================================
    // SAVE ALL FOLLOWING CATEGORIES
    // =========================================================

    @PostMapping(
            "/profiles/{username}/following/categories"
    )
    public String updateFollowingCategories(
            @PathVariable String username,
            @RequestParam Map<String, String> params
    ) {

        for (Map.Entry<String, String> entry :
                params.entrySet()) {

            String key = entry.getKey();

            if (key.startsWith("following_category_")) {

                Long followingId =
                        Long.parseLong(
                                key.substring(
                                        "following_category_".length()
                                )
                        );

                String category =
                        entry.getValue();

                profileService.updateFollowingCategory(
                        followingId,
                        category
                );
            }
        }

        return "redirect:/profiles/" + username;
    }
}
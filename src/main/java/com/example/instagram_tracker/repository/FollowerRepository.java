package com.example.instagram_tracker.repository;
import com.example.instagram_tracker.model.Follower;
import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; import java.util.Optional;
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByProfile(Profile profile);
    Optional<Follower> findByProfileAndUsername( Profile profile, String username );
}

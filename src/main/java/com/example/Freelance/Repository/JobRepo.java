package com.example.Freelance.Repository;

import com.example.Freelance.Entity.Job;
import com.example.Freelance.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepo extends JpaRepository<Job,Long> {
    List<Job> findByPostedBy(User user);
}

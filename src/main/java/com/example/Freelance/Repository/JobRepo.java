package com.example.Freelance.Repository;

import com.example.Freelance.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job,Long> {

}

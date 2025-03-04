package com.example.Freelance.Controller;

import com.example.Freelance.Entity.Job;
import com.example.Freelance.Entity.User;
import com.example.Freelance.Repository.UserRepo;
import com.example.Freelance.Service.JobService;
import com.example.Freelance.Utils.ApiResponse;
import com.example.Freelance.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepo userRepo;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job, @RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String email = jwtUtil.extractUsername(token);
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        job.setPostedBy(user);
        jobService.createJob(job);
        return new ResponseEntity<>(new ApiResponse("Job created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/get-all-jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        jobService.updateJob(id, jobDetails);
        return ResponseEntity.ok(new ApiResponse("Job updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(new ApiResponse("Job deleted successfully"));
    }
}

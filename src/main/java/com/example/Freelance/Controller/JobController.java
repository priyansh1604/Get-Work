package com.example.Freelance.Controller;

import com.example.Freelance.Entity.Job;
import com.example.Freelance.Service.JobService;
import com.example.Freelance.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>(new ApiResponse("Job created successfully"), HttpStatus.CREATED);
    }

    @GetMapping
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

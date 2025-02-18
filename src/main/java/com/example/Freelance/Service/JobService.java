package com.example.Freelance.Service;


import com.example.Freelance.Entity.Job;
import com.example.Freelance.Repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepo jobRepository;

    public JobService(JobRepo jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job updateJob(Long id, Job updatedJob) {
        Job existingJob = getJobById(id);
        if (updatedJob.getTitle() != null) {
            existingJob.setTitle(updatedJob.getTitle());
        }
        if (updatedJob.getDescription() != null) {
            existingJob.setDescription(updatedJob.getDescription());
        }
        if (updatedJob.getBudget() != null) {
            existingJob.setBudget(updatedJob.getBudget());
        }
        if (updatedJob.getCategory() != null) {
            existingJob.setCategory(updatedJob.getCategory());
        }
        if (updatedJob.getSkillsRequired() != null && !updatedJob.getSkillsRequired().isEmpty()) {
            existingJob.setSkillsRequired(updatedJob.getSkillsRequired());
        }
        if (updatedJob.getStatus() != null) {
            existingJob.setStatus(updatedJob.getStatus());
        }
        return jobRepository.save(existingJob);
    }

    public void deleteJob(Long id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
    }
}

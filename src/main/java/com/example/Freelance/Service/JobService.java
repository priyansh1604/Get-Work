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
        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setBudget(updatedJob.getBudget());
        existingJob.setCategory(updatedJob.getCategory());
        existingJob.setSkillsRequired(updatedJob.getSkillsRequired());
        existingJob.setStatus(updatedJob.getStatus());

        return jobRepository.save(existingJob);
    }

    public void deleteJob(Long id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
    }
}

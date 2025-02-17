package com.example.Freelance.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Column(length = 2000)
    private String description;

    @Min(value = 0, message = "Budget must be greater than or equal to 0")
    private Double budget;

    private String category;

    @ElementCollection
    private List<String> skillsRequired;

    private String status = "open";

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is required") String description) {
        this.description = description;
    }

    public @Min(value = 0, message = "Budget must be greater than or equal to 0") Double getBudget() {
        return budget;
    }

    public void setBudget(@Min(value = 0, message = "Budget must be greater than or equal to 0") Double budget) {
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(List<String> skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

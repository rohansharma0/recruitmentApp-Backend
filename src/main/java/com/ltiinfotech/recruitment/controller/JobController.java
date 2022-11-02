package com.ltiinfotech.recruitment.controller;

import com.ltiinfotech.recruitment.model.User;
import com.ltiinfotech.recruitment.payload.ApiResponse;
import com.ltiinfotech.recruitment.payload.JobDto;
import com.ltiinfotech.recruitment.repository.JobRepository;
import com.ltiinfotech.recruitment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @CrossOrigin
    @PostMapping("/job")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto jobDto){
        JobDto createdJobDto = this.jobService.createJob(jobDto);
        return new ResponseEntity<>(createdJobDto, HttpStatus.CREATED);
    }

    //put - update user
    @PutMapping("/job/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobDto> updateJob(@Valid @RequestBody JobDto jobDto , @PathVariable Long jobId){
        JobDto updatedJobDto = this.jobService.updateJob(jobDto , jobId);
        return new ResponseEntity<>(updatedJobDto , HttpStatus.OK);
    }

    //delete - delete job
    @DeleteMapping("/job/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long jobId){
        this.jobService.deleteJob(jobId);
        return new ResponseEntity<>( new ApiResponse("Job Deleted Successfully" , true) , HttpStatus.OK);
    }

    //get - job
    @GetMapping("/jobs")
    public ResponseEntity<List<JobDto>> getAllJobs(){
        return ResponseEntity.ok(this.jobService.getAllJobs());
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<JobDto> getJob(@PathVariable Long jobId){
        return ResponseEntity.ok(this.jobService.getJobById(jobId));
    }

    @CrossOrigin
    @GetMapping("/job/search/skill/{skill}")
    public ResponseEntity<List<JobDto>> getJobBySkill(@PathVariable String skill){
        return ResponseEntity.ok(this.jobService.getAllJobsBySkill(skill));
    }

    @CrossOrigin
    @GetMapping("/job/search/title/{title}")
    public ResponseEntity<List<JobDto>> getJobByTitle(@PathVariable String title){
        return ResponseEntity.ok(this.jobService.getAllJobsByTitle(title));
    }
}

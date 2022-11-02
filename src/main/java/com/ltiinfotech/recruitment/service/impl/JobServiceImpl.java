package com.ltiinfotech.recruitment.service.impl;

import com.ltiinfotech.recruitment.exception.ResourceNotFoundException;
import com.ltiinfotech.recruitment.model.Job;
import com.ltiinfotech.recruitment.payload.JobDto;
import com.ltiinfotech.recruitment.repository.JobRepository;
import com.ltiinfotech.recruitment.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JobDto createJob(JobDto jobDto) {
        Job job = this.dtoToJob(jobDto);
        Job savedJob = this.jobRepository.save(job);
        return this.jobToDto(savedJob);
    }

    @Override
    public JobDto updateJob(JobDto jobDto, long jobId) {
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job" , "id" , jobId));

        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setSkill(jobDto.getSkill());
        job.setExpiryDate(jobDto.getExpiryDate());
        job.setIsActive(jobDto.getIsActive());
        job.setMaxExperience(jobDto.getMaxExperience());
        job.setMinExperience(jobDto.getMinExperience());

        Job updatedJob = this.jobRepository.save(job);

        return this.jobToDto(updatedJob);
    }

    @Override
    public JobDto getJobById(long jobId) {
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job" , "id" , jobId));

        return this.jobToDto(job);
    }

    @Override
    public List<JobDto> getAllJobs() {

        List<Job> jobs = this.jobRepository.findAll();

        return jobs.stream().map(job -> this.jobToDto(job)).collect(Collectors.toList());
    }

    @Override
    public List<JobDto>  getAllJobsByTitle(String title){

        List<Job> jobs = this.jobRepository.findByTitleContaining(title);

        return jobs.stream().map(job -> this.jobToDto(job)).collect(Collectors.toList());
    }

    @Override
    public List<JobDto>  getAllJobsBySkill(String skill){

        List<Job> jobs = this.jobRepository.findBySkillContaining(skill);

        return jobs.stream().map(job -> this.jobToDto(job)).collect(Collectors.toList());
    }


    @Override
    public void deleteJob(long jobId) {
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job" , "id" , jobId));
        this.jobRepository.delete(job);
    }

    private Job dtoToJob(JobDto jobDto){
        return this.modelMapper.map(jobDto, Job.class);
    }

    private JobDto jobToDto(Job job){
        return this.modelMapper.map(job, JobDto.class);
    }

}

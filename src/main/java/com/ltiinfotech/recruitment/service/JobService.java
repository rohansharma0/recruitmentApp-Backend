package com.ltiinfotech.recruitment.service;


import com.ltiinfotech.recruitment.payload.JobDto;

import java.util.List;

public interface JobService {
    JobDto createJob(JobDto jobDto);

    JobDto updateJob(JobDto jobDto , long jobId);

    JobDto getJobById(long jobId);

    List<JobDto> getAllJobs();

    List<JobDto> getAllJobsByTitle(String title);

    List<JobDto> getAllJobsBySkill(String skill);

    void deleteJob(long jobId);

}

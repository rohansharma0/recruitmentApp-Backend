package com.ltiinfotech.recruitment.repository;

import com.ltiinfotech.recruitment.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContaining(String title);

    List<Job> findBySkillContaining(String skill);
}

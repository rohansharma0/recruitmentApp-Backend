package com.ltiinfotech.recruitment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "jobs")
@NoArgsConstructor
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String skill;

    private Integer minExperience;

    private Integer maxExperience;

    private Boolean isActive;

    private Date expiryDate;

    @ManyToMany(mappedBy = "savedJobs" , fetch = FetchType.EAGER)
    private Set<User> savedUser = new HashSet<>();

    @ManyToMany(mappedBy = "appliedJobs" , fetch = FetchType.EAGER)
    private Set<User> appliedUser = new HashSet<>();

}

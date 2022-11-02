package com.ltiinfotech.recruitment.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class JobDto {

    private long id;

    @NotEmpty(message = "Title must not be empty.")
    private String title;

    @NotEmpty(message = "Description must not be empty.")
    private String description;

    @NotEmpty(message = "Skill must not be empty")
    private String skill;

    @NotEmpty(message = "Minimum experience must not be empty")
    private Integer minExperience;

    @NotEmpty(message = "Maximum experience must not be empty")
    private Integer maxExperience;

    private Boolean isActive;

    private Date expiryDate;
}

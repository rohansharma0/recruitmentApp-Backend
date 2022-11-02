package com.ltiinfotech.recruitment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    private long id;

    public String name;
}

package com.ltiinfotech.recruitment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String resumeUrl;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(name = "user_role" ,
        joinColumns = @JoinColumn(name = "user" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role" , referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(name = "user_savedJobs" ,
            joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id" , referencedColumnName = "id")
    )
    private Set<Job> savedJobs = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(name = "user_appliedJobs" ,
            joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id" , referencedColumnName = "id")
    )
    private Set<Job> appliedJobs = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword(){
        return this.password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

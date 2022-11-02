package com.ltiinfotech.recruitment.security;


import com.ltiinfotech.recruitment.exception.ResourceNotFoundException;
import com.ltiinfotech.recruitment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User" , "email : " + username , 0));
    }
}

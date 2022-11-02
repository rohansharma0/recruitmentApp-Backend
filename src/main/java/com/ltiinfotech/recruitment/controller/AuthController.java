package com.ltiinfotech.recruitment.controller;

import com.ltiinfotech.recruitment.exception.ApiException;
import com.ltiinfotech.recruitment.model.User;
import com.ltiinfotech.recruitment.payload.JwtAuthRequest;
import com.ltiinfotech.recruitment.payload.JwtAuthResponse;
import com.ltiinfotech.recruitment.payload.UserDto;
import com.ltiinfotech.recruitment.security.JwtTokenHelper;
import com.ltiinfotech.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
            ) throws Exception {
        this.authenticate(request.getEmail() , request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser(userDetails);

        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    //post - register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto){
        User createUser = this.userService.registerUser(userDto);
        return new ResponseEntity<>(createUser , HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username , password);

        try{
            this.authenticationManager.authenticate(authenticationToken);

        }catch (BadCredentialsException e){
            throw new ApiException("Invalid email or password");
        }


    }

}

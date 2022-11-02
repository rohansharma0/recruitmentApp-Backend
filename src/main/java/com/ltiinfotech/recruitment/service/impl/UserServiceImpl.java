package com.ltiinfotech.recruitment.service.impl;

import com.ltiinfotech.recruitment.config.AppConstants;
import com.ltiinfotech.recruitment.exception.ResourceNotFoundException;
import com.ltiinfotech.recruitment.model.Role;
import com.ltiinfotech.recruitment.model.User;
import com.ltiinfotech.recruitment.payload.JobDto;
import com.ltiinfotech.recruitment.payload.UserDto;
import com.ltiinfotech.recruitment.repository.RoleRepository;
import com.ltiinfotech.recruitment.repository.UserRepository;
import com.ltiinfotech.recruitment.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        //encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepository.findById(AppConstants.USER_ROLE_ID).get();
        user.getRoles().add(role);

        User savedUser = this.userRepository.save(user);

        return savedUser;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        //encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepository.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setResumeUrl(userDto.getResumeUrl());
        user.setEmail(userDto.getEmail());

        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto updateUserPassword(UserDto userDto, long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));

        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepository.findAll();

        return users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    }

//    @Override
//    public List<JobDto> getAllSavedJobsByUserId() {
//
//        List<User> users = this.userRepository.findAll();
//
//        return users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
//    }



    @Override
    public void deleteUser(long userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));
        user.setRoles(null);
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }


}

package com.ltiinfotech.recruitment.service;

import com.ltiinfotech.recruitment.model.Job;
import com.ltiinfotech.recruitment.model.User;
import com.ltiinfotech.recruitment.payload.JobDto;
import com.ltiinfotech.recruitment.payload.UserDto;

import java.util.List;

public interface UserService {

    User registerUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user , long userId);

    UserDto updateUserPassword(UserDto user , long userId);

    UserDto getUserById(long userId);

    List<UserDto> getAllUsers();

//    List<JobDto> getAllSavedJobsByUserId();

    void deleteUser(long userId);
}

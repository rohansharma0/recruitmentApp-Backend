package com.ltiinfotech.recruitment.controller;

import com.ltiinfotech.recruitment.model.Job;
import com.ltiinfotech.recruitment.model.User;
import com.ltiinfotech.recruitment.payload.ApiResponse;
import com.ltiinfotech.recruitment.payload.UserDto;
import com.ltiinfotech.recruitment.service.FileService;
import com.ltiinfotech.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {


    @Autowired
    private FileService fileService;

    @Value("${project.resumes}")
    private String resumesPath;


    @Autowired
    private UserService userService;


    //put - update user
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable Long userId){
        UserDto updateUser = this.userService.updateUser(userDto , userId);
        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    //put - update user
    @PutMapping("/user/reset-password/{userId}")
    public ResponseEntity<UserDto> updateUserPassword(@Valid @RequestBody UserDto userDto , @PathVariable Long userId){
        UserDto updateUser = this.userService.updateUserPassword(userDto , userId);
        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    //delete - delete user
    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>( new ApiResponse("User Deleted Successfully" , true) , HttpStatus.OK);
    }

    //get - user get
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    //post - Product image upload
    @PostMapping("/user/upload-resume/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> uploadUserResume(
            @PathVariable Long userId,
            @RequestParam(value = "resume") MultipartFile resume
    ) throws IOException {

        UserDto userDto = this.userService.getUserById(userId);

        String fileName = this.fileService.uploadResume(resumesPath , resume);

        userDto.setResumeUrl(fileName);

        UserDto updateUser =  this.userService.updateUser(userDto , userId);

        return new ResponseEntity<>(updateUser , HttpStatus.OK);
    }

    //get - get product image
    @GetMapping(value = "/user/resume/{resumeUrl}" , produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadResume(
            @PathVariable("resumeUrl") String resumeUrl,
            HttpServletResponse response
    ) throws IOException {
        InputStream inputStream = this.fileService.getResource(resumesPath , resumeUrl);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        StreamUtils.copy(inputStream , response.getOutputStream());
    }

//    @GetMapping("/saved-jobs/{userId}/")
//    public ResponseEntity<List<Job>> getUser(@PathVariable Long userId){
//        return ResponseEntity.ok(this.userService.getAllSavedJobsByUserId(userId));
//    }
//
//    @GetMapping("/saved-jobs/{userId}/")
//    public ResponseEntity<List<Job>> getUser(@PathVariable Long userId){
//        return ResponseEntity.ok(this.userService.getAllSavedJobsByUserId(userId));
//    }
}

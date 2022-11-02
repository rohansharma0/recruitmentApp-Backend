package com.ltiinfotech.recruitment.service.impl;

import com.ltiinfotech.recruitment.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadResume(String path, MultipartFile file) throws IOException {

        //FileName
        String name = file.getOriginalFilename();

        //Random name generate file
        String randomID = UUID.randomUUID().toString();
        String randomFileName = randomID.concat(name.substring(name.lastIndexOf('.')));

        //FullPath
        String filePath = path + File.separator + randomFileName;

        //Create folder if not created
        File f = new File(path);
        if(!f.exists())f.mkdir();

        //FileCopy

        Files.copy(file.getInputStream() , Paths.get(filePath));

        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path+ File.separator+fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }


}
